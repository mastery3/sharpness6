package mastery3.sharpness6.core;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class EnchantmentRegistryPatcherTest {

    private static final Gson GSON = new Gson();

    private static final String SHARPNESS_JSON = "{"
            + "\"max_level\": 5,"
            + "\"weight\": 10"
            + "}";

    private static final String UNBREAKING_26_JSON = "{"
            + "\"max_level\": 3,"
            + "\"effects\": {"
            + "  \"minecraft:item_damage\": ["
            + "    {"
            + "      \"effect\": {"
            + "        \"type\": \"minecraft:remove_binomial\","
            + "        \"chance\": {"
            + "          \"type\": \"minecraft:fraction\","
            + "          \"denominator\": {\"type\": \"minecraft:linear\", \"base\": 10.0, \"per_level_above_first\": 5.0},"
            + "          \"numerator\": {\"type\": \"minecraft:linear\", \"base\": 2.0, \"per_level_above_first\": 2.0}"
            + "        }"
            + "      },"
            + "      \"requirements\": {\"type\": \"minecraft:match_tool\", \"predicate\": {\"items\": \"#minecraft:enchantable/armor\"}}"
            + "    },"
            + "    {"
            + "      \"effect\": {"
            + "        \"type\": \"minecraft:remove_binomial\","
            + "        \"chance\": {"
            + "          \"type\": \"minecraft:fraction\","
            + "          \"denominator\": {\"type\": \"minecraft:linear\", \"base\": 2.0, \"per_level_above_first\": 1.0},"
            + "          \"numerator\": {\"type\": \"minecraft:linear\", \"base\": 1.0, \"per_level_above_first\": 1.0}"
            + "        }"
            + "      },"
            + "      \"requirements\": {\"type\": \"minecraft:inverted\", \"term\": {\"type\": \"minecraft:match_tool\", \"predicate\": {\"items\": \"#minecraft:enchantable/armor\"}}}"
            + "    }"
            + "  ]"
            + "}"
            + "}";

    private static final String UNBREAKING_121_JSON = UNBREAKING_26_JSON.replace("\"type\": \"minecraft:match_tool\"", "\"condition\": \"minecraft:match_tool\"");

    @Test
    void leavesUnmodifiedEnchantmentsUntouchedForKnownPackPreservation() {
        SharpnessConfig config = new SharpnessConfig();
        EnchantmentLimitPolicy policy = new EnchantmentLimitPolicy(
                config, EnchantmentIds.DATA_DRIVEN_MAX_LEVEL_CAP, null);
        JsonElement json = GSON.fromJson(SHARPNESS_JSON, JsonElement.class);

        EnchantmentRegistryPatcher.PatchResult result =
                EnchantmentRegistryPatcher.patchEnchantmentJson("minecraft:sharpness", json, policy);

        assertFalse(result.modified());
        assertEquals(5, json.getAsJsonObject().get("max_level").getAsInt());
    }

    @Test
    void patchesMaxLevelOnConfiguredEnchantment() {
        SharpnessConfig config = new SharpnessConfig();
        config.sharpness = 10;
        EnchantmentLimitPolicy policy = new EnchantmentLimitPolicy(
                config, EnchantmentIds.DATA_DRIVEN_MAX_LEVEL_CAP, null);
        JsonElement json = GSON.fromJson(SHARPNESS_JSON, JsonElement.class);

        EnchantmentRegistryPatcher.PatchResult result =
                EnchantmentRegistryPatcher.patchEnchantmentJson("minecraft:sharpness", json, policy);

        assertTrue(result.modified());
        assertEquals(10, result.resultingMaxLevel());
        assertEquals(10, json.getAsJsonObject().get("max_level").getAsInt());
    }

    @Test
    void transformsBoth121And26SchemasForUnbreakingArmorFixWithoutBreakingToolBranch() {
        SharpnessConfig config = new SharpnessConfig();
        config.unbreaking = 10;
        config.unbreakingArmorFix = true;
        EnchantmentLimitPolicy policy = new EnchantmentLimitPolicy(
                config, EnchantmentIds.DATA_DRIVEN_MAX_LEVEL_CAP, null);

        for (String sample : new String[]{UNBREAKING_121_JSON, UNBREAKING_26_JSON}) {
            JsonElement root = GSON.fromJson(sample, JsonElement.class);
            EnchantmentRegistryPatcher.PatchResult result =
                    EnchantmentRegistryPatcher.patchEnchantmentJson("minecraft:unbreaking", root, policy);

            assertTrue(result.modified());
            assertTrue(result.unbreakingTransformed());
            assertEquals(10, result.resultingMaxLevel());

            JsonObject obj = root.getAsJsonObject();
            JsonObject armorChance = obj.getAsJsonObject("effects")
                    .getAsJsonArray("minecraft:item_damage").get(0).getAsJsonObject()
                    .getAsJsonObject("effect").getAsJsonObject("chance");
            assertEquals(0.5, armorChance.getAsJsonObject("numerator").get("base").getAsDouble());
            assertEquals(1.5, armorChance.getAsJsonObject("denominator").get("base").getAsDouble());

            // Ensure non-armor branch remains intact.
            JsonObject toolChance = obj.getAsJsonObject("effects")
                    .getAsJsonArray("minecraft:item_damage").get(1).getAsJsonObject()
                    .getAsJsonObject("effect").getAsJsonObject("chance");
            assertEquals(1.0, toolChance.getAsJsonObject("numerator").get("base").getAsDouble());
            assertEquals(2.0, toolChance.getAsJsonObject("denominator").get("base").getAsDouble());
        }
    }
}
