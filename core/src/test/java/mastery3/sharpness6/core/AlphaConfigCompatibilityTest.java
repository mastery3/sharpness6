package mastery3.sharpness6.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

final class AlphaConfigCompatibilityTest {

    @Test
    void loadsAlphaEnchantmentsMap(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, ("{\"enchantments\": {\"minecraft:sharpness\": 12, \"minecraft:protection\": 8}, " +
                "\"enhancements\": {\"unbreaking\": {\"modifyArmorFormula\": true}}}").getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(12, c.sharpness);
        assertEquals(8, c.protection);
        assertTrue(c.unbreakingArmorFix);
    }

    @Test
    void preservesLegacyIndividualFields(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"sharpness\": 10, \"protection\": 7, \"unbreakingArmorFix\": true}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(10, c.sharpness);
        assertEquals(7, c.protection);
        assertTrue(c.unbreakingArmorFix);
    }

    @Test
    void migratesAlphaCustomEnchantmentToCustomMaxLevels(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"enchantments\": {\"mod:custom_enchant\": 6}}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(Integer.valueOf(6), c.customMaxLevels.get("mod:custom_enchant"));
    }

    @Test
    void malformedJsonRestoresDefaults(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{not json".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(5, c.sharpness);
        assertFalse(c.unbreakingArmorFix);
    }

    @Test
    void alphaNamespaceCollisionKeepsCustomIdFull(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, ("{\"enchantments\": {\"minecraft:sharpness\": 12, \"othermod:sharpness\": 30}}").getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(12, c.sharpness);
        assertEquals(Integer.valueOf(30), c.customMaxLevels.get("othermod:sharpness"));
        // Full identifier preserved, not collapsed to "sharpness".
        assertNull(c.customMaxLevels.get("sharpness"));
    }

    @Test
    void alphaUnknownMinecraftEnchantmentRoutesToCustom(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"enchantments\": {\"minecraft:nonexistent_enchant\": 7}}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(Integer.valueOf(7), c.customMaxLevels.get("minecraft:nonexistent_enchant"));
    }

    @Test
    void alphaNullAndOutOfRangeValuesAreIgnored(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"enchantments\": {\"minecraft:sharpness\": null, \"minecraft:protection\": 0}}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        // null leaves default; 0 is stored raw (clamped to 1 later by policy).
        assertEquals(5, c.sharpness);
        assertEquals(0, c.protection);
    }

    @Test
    void alphaConflictPrefersAlphaMapOverLegacyField(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"sharpness\": 4, \"enchantments\": {\"minecraft:sharpness\": 11}}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(11, c.sharpness);
    }

    @Test
    void partiallyPopulatedAlphaConfigKeepsDefaults(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"enchantments\": {\"minecraft:sharpness\": 9}}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(9, c.sharpness);
        assertEquals(5, c.efficiency);
        assertEquals(4, c.protection);
    }

    @Test
    void savesAndReloadsMigratedConfig(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"enchantments\": {\"minecraft:sharpness\": 11, \"othermod:foo\": 4}}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig first = SharpnessConfigStore.loadOrCreate(p, null);
        assertEquals(11, first.sharpness);
        assertEquals(Integer.valueOf(4), first.customMaxLevels.get("othermod:foo"));

        SharpnessConfigStore.save(p, first);
        SharpnessConfig second = SharpnessConfigStore.loadOrCreate(p, null);
        assertEquals(11, second.sharpness);
        assertEquals(Integer.valueOf(4), second.customMaxLevels.get("othermod:foo"));
    }
}
