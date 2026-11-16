package mastery3.sharpness6.core;

import java.util.List;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.junit.jupiter.api.io.TempDir;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class SharpnessConfigStoreTest {
    @Test
    void createsDefaultConfigWithModernProperties(@TempDir Path dir) {
        Path configPath = dir.resolve("sharpness6.json");
        List<String> warnings = new ArrayList<String>();

        SharpnessConfig config = SharpnessConfigStore.loadOrCreate(configPath, warnings::add);

        assertTrue(Files.exists(configPath));
        assertTrue(warnings.isEmpty());

        assertNotNull(config.enchantments);
        assertTrue(config.enchantments.isEmpty());

        assertNotNull(config.enhancements);
        assertNotNull(config.enhancements.unbreaking);
        assertNotNull(config.enhancements.unbreaking.modifyArmorFormula);

        // Legacy Values that shouldn't be here
        assertNull(config.sharpness);
        assertNull(config.smite);
        assertNull(config.bane_of_arthropods);
        assertNull(config.protection);
        assertNull(config.fire_protection);
        assertNull(config.blast_protection);
        assertNull(config.projectile_protection);
        assertNull(config.feather_falling);
        assertNull(config.lunge);
        assertNull(config.unbreakingArmorFix);
    }

    @Test
    void usesModernConfigurationValues(@TempDir Path dir) throws Exception {
        Path configPath = dir.resolve("sharpness6.json");
        Files.write(configPath, "{ \"enchantments\": { \"minecraft:sharpness\": 10, \"minecraft:protection\": 5 } }".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig config = SharpnessConfigStore.loadOrCreate(configPath, null);
        List<String> warnings = new ArrayList<String>();

        assertNull(config.sharpness);
        assertNull(config.protection);

        assertEquals(config.enchantments.get("minecraft:sharpness"), 10);
        assertEquals(config.enchantments.get("minecraft:protection"), 5);

        assertTrue(warnings.isEmpty());
    }

    @Test
    void migratesLegacyConfigurationValues(@TempDir Path dir) throws IOException {
        Path configPath = dir.resolve("sharpness6.json");
        Files.write(configPath, "{\"sharpness\": 10, \"smite\": 11, \"protection\": 8, \"unbreakingArmorFix\": true}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig config = SharpnessConfigStore.loadOrCreate(configPath, null);
        assertNull(config.sharpness);
        assertNull(config.protection);

        // Sharpness
        assertEquals(config.enchantments.get("minecraft:sharpness"), 10);
        assertEquals(config.enchantments.get("minecraft:smite"), 11);
        assertEquals(config.enchantments.get("minecraft:bane_of_arthropods"), 10);

        // Protection
        assertEquals(config.enchantments.get("minecraft:protection"), 8);
        assertEquals(config.enchantments.get("minecraft:fire_protection"), 8);
        assertEquals(config.enchantments.get("minecraft:projectile_protection"), 8);
        assertEquals(config.enchantments.get("minecraft:blast_protection"), 8);
        assertEquals(config.enchantments.get("minecraft:feather_falling"), 8);

        // Unbreaking Armor Fix
        assertTrue(config.enhancements.unbreaking.modifyArmorFormula);
    }

    @Test
    void doesNotOverwriteMalformedJsonConfig(@TempDir Path dir) throws Exception {
        Path configPath = dir.resolve("sharpness6.json");
        String malformed = "{ \"sharpness\": 10, UNCLOSED ";
        Files.write(configPath, malformed.getBytes(StandardCharsets.UTF_8));
        List<String> warnings = new ArrayList<String>();

        SharpnessConfig loaded = SharpnessConfigStore.loadOrCreate(configPath, warnings::add);

        assertFalse(warnings.isEmpty());

        assertNotNull(loaded.enchantments);
        assertTrue(loaded.enchantments.isEmpty());
        assertNotNull(loaded.enhancements);

        assertEquals(malformed, new String(Files.readAllBytes(configPath), StandardCharsets.UTF_8));
    }

    @Test
    void handlesEmptyConfigFileGracefully(@TempDir Path dir) throws Exception {
        Path configPath = dir.resolve("sharpness6.json");
        Files.write(configPath, new byte[0]);
        List<String> warnings = new ArrayList<String>();

        SharpnessConfig loaded = SharpnessConfigStore.loadOrCreate(configPath, warnings::add);

        assertEquals(1, warnings.size());
        assertNotNull(loaded.enchantments);
        assertTrue(loaded.enchantments.isEmpty());
        assertNotNull(loaded.enhancements);
    }

    @Test
    void idsAreCanonicalized(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, ("{\"enchantments\": {\"minecraft:sharpness\": 12, \"othermod:sharpness\": 30}}").getBytes(StandardCharsets.UTF_8));
        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(12, c.enchantments.get("minecraft:sharpness"));
        assertEquals(30, c.enchantments.get("othermod:sharpness"));
        assertFalse(c.enchantments.containsKey("sharpness6"));
    }

    @Test
    void doesNotReplaceExistingCanonicalized(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, ("{\"enchantments\": {\"minecraft:sharpness\": 12, \"sharpness\": 30}}").getBytes(StandardCharsets.UTF_8));
        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(12, c.enchantments.get("minecraft:sharpness"));
        assertEquals(30, c.enchantments.get("sharpness"));
    }

    @Test
    void doesntClampInStore(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, ("{\"enchantments\": {\"minecraft:sharpness\": -5, \"minecraft:protection\": 1000000000}}").getBytes(StandardCharsets.UTF_8));
        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(-5, c.enchantments.get("minecraft:sharpness"));
        assertEquals(1000000000, c.enchantments.get("minecraft:protection"));
    }

    @Test
    void neverOverrideModernWithLegacy(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, ("{\"enchantments\": {\"minecraft:sharpness\": 10}, \"sharpness\": 5}").getBytes(StandardCharsets.UTF_8));
        SharpnessConfig c = SharpnessConfigStore.loadOrCreate(p, null);

        assertEquals(10, c.enchantments.get("minecraft:sharpness"));

        // We don't know why the user put duplicate keys in, so don't delete it.
        assertEquals(5, c.sharpness);
    }

    @Test
    void buildsCorrectBoundedLevelMap(@TempDir Path dir) throws Exception {
        Path configPath = dir.resolve("sharpness6.json");
        Files.write(configPath, "{ \"enchantments\": { \"minecraft:sharpness\": 10, \"minecraft:protection\": 5 } }".getBytes(StandardCharsets.UTF_8));

        List<String> warnings = new ArrayList<String>();
        SharpnessConfig config = SharpnessConfigStore.loadOrCreate(configPath, warnings::add);

        Map<String, Integer> boundedLevelMap = config.toBoundedLevelMap(8, warnings::add);

        assertEquals(10, config.enchantments.get("minecraft:sharpness"));
        assertEquals(boundedLevelMap.get("minecraft:sharpness"), 8);
        assertEquals(boundedLevelMap.get("minecraft:protection"), 5);

        assertEquals(warnings.size(), 1);
    }

    @Test
    void savesAndReloadsMigratedConfig(@TempDir Path dir) throws Exception {
        Path p = dir.resolve("sharpness6.json");
        Files.write(p, "{\"enchantments\": {\"minecraft:sharpness\": 11, \"othermod:foo\": 4}}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig first = SharpnessConfigStore.loadOrCreate(p, null);
        assertEquals(11, first.enchantments.get("minecraft:sharpness"));
        assertEquals(Integer.valueOf(4), first.enchantments.get("othermod:foo"));

        SharpnessConfigStore.save(p, first);
        SharpnessConfig second = SharpnessConfigStore.loadOrCreate(p, null);
        assertEquals(11, second.enchantments.get("minecraft:sharpness"));
        assertEquals(Integer.valueOf(4), second.enchantments.get("othermod:foo"));
    }
}
