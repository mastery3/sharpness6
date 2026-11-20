package mastery3.sharpness6.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

final class ConfigMigrationEdgeCasesTest {

    @Test
    void canonicalAndShorthandKeysDoNotOverwriteEachOther(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"enchantments\": {\"minecraft:sharpness\": 12, \"sharpness\": 30}}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        // Both keys retained in the map (migration does not delete shorthand).
        assertEquals(Integer.valueOf(12), c.enchantments.get("minecraft:sharpness"));
        assertEquals(Integer.valueOf(30), c.enchantments.get("sharpness"));

        // Effective value prefers the namespaced entry.
        assertEquals(Integer.valueOf(12), c.toBoundedLevelMap(255, null).get("minecraft:sharpness"));
    }

    @Test
    void shorthandOnlyKeyIsCanonicalized(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"enchantments\": {\"sharpness\": 7}}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(Integer.valueOf(7), c.enchantments.get("minecraft:sharpness"));
        assertFalse(c.enchantments.containsKey("sharpness"));
    }

    @Test
    void unbreakingArmorFixFalseStaysFalse(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"unbreakingArmorFix\": false}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertFalse(c.enhancements.unbreaking.modifyArmorFormula);
    }

    @Test
    void unbreakingArmorFixTrueMigratesToTrue(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"unbreakingArmorFix\": true}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertTrue(c.enhancements.unbreaking.modifyArmorFormula);
    }

    @Test
    void migrationDoesNotThrowOnMixedKeys(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"enchantments\": {\"sharpness\": 5, \"protection\": 4, \"minecraft:efficiency\": 9}}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(Integer.valueOf(5), c.enchantments.get("minecraft:sharpness"));
        assertEquals(Integer.valueOf(4), c.enchantments.get("minecraft:protection"));
        assertEquals(Integer.valueOf(9), c.enchantments.get("minecraft:efficiency"));
    }

    @Test
    void roundTripAfterMigrationKeepsValues(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"sharpness\": 11, \"unbreakingArmorFix\": true}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig first = SharpnessConfigStore.loadOrCreate(p, null);
        assertEquals(Integer.valueOf(11), first.enchantments.get("minecraft:sharpness"));
        assertTrue(first.enhancements.unbreaking.modifyArmorFormula);

        SharpnessConfigStore.save(p, first);
        SharpnessConfig second = SharpnessConfigStore.loadOrCreate(p, null);
        assertEquals(Integer.valueOf(11), second.enchantments.get("minecraft:sharpness"));
        assertTrue(second.enhancements.unbreaking.modifyArmorFormula);
    }
}
