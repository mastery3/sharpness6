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
    void partiallyPopulatedAlphaConfigKeepsDefaults(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"enchantments\": {\"minecraft:sharpness\": 9}}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(9, c.sharpness);
        // Other fields retain vanilla defaults.
        assertEquals(5, c.efficiency);
        assertEquals(4, c.protection);
    }
}
