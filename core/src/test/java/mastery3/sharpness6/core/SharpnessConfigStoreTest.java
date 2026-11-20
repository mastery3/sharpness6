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
    void assertAllLegacyNull(SharpnessConfig config) {
        assertNull(config.aqua_affinity);
        assertNull(config.bane_of_arthropods);
        assertNull(config.binding_curse);
        assertNull(config.blast_protection);
        assertNull(config.breach);
        assertNull(config.channeling);
        assertNull(config.density);
        assertNull(config.depth_strider);
        assertNull(config.efficiency);
        assertNull(config.feather_falling);
        assertNull(config.fire_aspect);
        assertNull(config.fire_protection);
        assertNull(config.flame);
        assertNull(config.fortune);
        assertNull(config.frost_walker);
        assertNull(config.impaling);
        assertNull(config.infinity);
        assertNull(config.knockback);
        assertNull(config.looting);
        assertNull(config.loyalty);
        assertNull(config.luck_of_the_sea);
        assertNull(config.lunge);
        assertNull(config.lure);
        assertNull(config.mending);
        assertNull(config.multishot);
        assertNull(config.piercing);
        assertNull(config.power);
        assertNull(config.projectile_protection);
        assertNull(config.protection);
        assertNull(config.punch);
        assertNull(config.quick_charge);
        assertNull(config.respiration);
        assertNull(config.riptide);
        assertNull(config.sharpness);
        assertNull(config.silk_touch);
        assertNull(config.smite);
        assertNull(config.soul_speed);
        assertNull(config.sweeping_edge);
        assertNull(config.swift_sneak);
        assertNull(config.thorns);
        assertNull(config.unbreaking);
        assertNull(config.unbreakingArmorFix);
        assertNull(config.vanishing_curse);
        assertNull(config.wind_burst);
    }

    void assertNew(SharpnessConfig config) {
        assertAllLegacyNull(config);

        assertNotNull(config.enchantments);
        assertTrue(config.enchantments.isEmpty());

        assertNotNull(config.enhancements);
        assertNotNull(config.enhancements.unbreaking);
        assertFalse(config.enhancements.unbreaking.modifyArmorFormula);
    }

    @Test
    void createNew(@TempDir Path dir) {
        Path configPath = dir.resolve("sharpness6.json");

        List<String> warnings = new ArrayList<String>();
        SharpnessConfig config = SharpnessConfigStore.loadOrCreate(configPath, warnings::add);

        assertTrue(warnings.isEmpty());
        assertTrue(Files.exists(configPath));
        assertNew(config);
    }

    @Test
    void loadExisting(@TempDir Path dir) throws Exception {
        Path configPath = dir.resolve("sharpness6.json");
        Files.write(configPath, "{ \"enchantments\": { \"minecraft:sharpness\": 10, \"minecraft:protection\": 5 }, \"enhancements\": { \"unbreaking\": { \"modifyArmorFormula\": true } } }".getBytes(StandardCharsets.UTF_8));

        List<String> warnings = new ArrayList<String>();
        SharpnessConfig config = SharpnessConfigStore.loadOrCreate(configPath, warnings::add);
        assertTrue(warnings.isEmpty());

        assertNull(config.sharpness);
        assertNull(config.protection);

        assertEquals(config.enchantments.get("minecraft:sharpness"), 10);
        assertEquals(config.enchantments.get("minecraft:protection"), 5);
        assertEquals(config.enchantments.size(), 2);
        assertTrue(config.enhancements.unbreaking.modifyArmorFormula);

        assertAllLegacyNull(config);
    }

    @Test
    void loadBlank(@TempDir Path dir) throws Exception {
        Path configPath = dir.resolve("sharpness6.json");
        Files.write(configPath, "{}".getBytes(StandardCharsets.UTF_8));

        List<String> warnings = new ArrayList<String>();
        SharpnessConfig config = SharpnessConfigStore.loadOrCreate(configPath, warnings::add);

        assertTrue(warnings.isEmpty());
        assertNew(config);
    }

    @Test
    void loadAllNull(@TempDir Path dir) throws Exception {
        Path configPath = dir.resolve("sharpness6.json");
        Files.write(configPath, "{ \"enchantments\": { \"minecraft:sharpness\": null, \"minecraft:protection\": null }, \"enhancements\": { \"unbreaking\": { \"modifyArmorFormula\": null } } }".getBytes(StandardCharsets.UTF_8));

        List<String> warnings = new ArrayList<String>();
        SharpnessConfig config = SharpnessConfigStore.loadOrCreate(configPath, warnings::add);

        assertTrue(warnings.isEmpty());
        assertNew(config);
    }

    @Test
    void loadWrongType(@TempDir Path dir) throws Exception {
        Path configPath = dir.resolve("sharpness6.json");
        Files.write(configPath, "{ \"enchantments\": true, \"enhancements\": 1 }".getBytes(StandardCharsets.UTF_8));

        List<String> warnings = new ArrayList<String>();
        SharpnessConfig config = SharpnessConfigStore.loadOrCreate(configPath, warnings::add);

        assertFalse(warnings.isEmpty());
        assertNew(config);
    }

    @Test
    void migratesLegacyConfigurationValues(@TempDir Path dir) throws IOException {
        Path configPath = dir.resolve("sharpness6.json");
        Files.write(configPath, "{ \"aqua_affinity\": 1, \"bane_of_arthropods\": 2, \"binding_curse\": 3, \"blast_protection\": 4, \"breach\": 5, \"channeling\": 6, \"density\": 7, \"depth_strider\": 8, \"efficiency\": 9, \"feather_falling\": 10, \"fire_aspect\": 11, \"fire_protection\": 12, \"flame\": 13, \"fortune\": 14, \"frost_walker\": 15, \"impaling\": 16, \"infinity\": 17, \"knockback\": 18, \"looting\": 19, \"loyalty\": 20, \"luck_of_the_sea\": 21, \"lunge\": 22, \"lure\": 23, \"mending\": 24, \"multishot\": 25, \"piercing\": 26, \"power\": 27, \"projectile_protection\": 28, \"protection\": 29, \"punch\": 30, \"quick_charge\": 31, \"respiration\": 32, \"riptide\": 33, \"sharpness\": 34, \"silk_touch\": 35, \"soul_speed\": 37, \"sweeping_edge\": 38, \"swift_sneak\": 39, \"thorns\": 40, \"unbreaking\": 41, \"unbreakingArmorFix\": true, \"vanishing_curse\": 43, \"wind_burst\": 44 }".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig config = SharpnessConfigStore.loadOrCreate(configPath, null);
        assertNull(config.sharpness);
        assertNull(config.protection);

        assertEquals(config.enchantments.get("minecraft:sharpness"), 34);
        assertEquals(config.enchantments.get("minecraft:smite"), 34);

        assertEquals(config.enchantments.get("minecraft:aqua_affinity"), 1);
        assertEquals(config.enchantments.get("minecraft:bane_of_arthropods"), 2);
        assertEquals(config.enchantments.get("minecraft:binding_curse"), 3);
        assertEquals(config.enchantments.get("minecraft:blast_protection"), 4);
        assertEquals(config.enchantments.get("minecraft:breach"), 5);
        assertEquals(config.enchantments.get("minecraft:channeling"), 6);
        assertEquals(config.enchantments.get("minecraft:density"), 7);
        assertEquals(config.enchantments.get("minecraft:depth_strider"), 8);
        assertEquals(config.enchantments.get("minecraft:efficiency"), 9);
        assertEquals(config.enchantments.get("minecraft:feather_falling"), 10);
        assertEquals(config.enchantments.get("minecraft:fire_aspect"), 11);
        assertEquals(config.enchantments.get("minecraft:fire_protection"), 12);
        assertEquals(config.enchantments.get("minecraft:flame"), 13);
        assertEquals(config.enchantments.get("minecraft:fortune"), 14);
        assertEquals(config.enchantments.get("minecraft:frost_walker"), 15);
        assertEquals(config.enchantments.get("minecraft:impaling"), 16);
        assertEquals(config.enchantments.get("minecraft:infinity"), 17);
        assertEquals(config.enchantments.get("minecraft:knockback"), 18);
        assertEquals(config.enchantments.get("minecraft:looting"), 19);
        assertEquals(config.enchantments.get("minecraft:loyalty"), 20);
        assertEquals(config.enchantments.get("minecraft:luck_of_the_sea"), 21);
        assertEquals(config.enchantments.get("minecraft:lunge"), 22);
        assertEquals(config.enchantments.get("minecraft:lure"), 23);
        assertEquals(config.enchantments.get("minecraft:mending"), 24);
        assertEquals(config.enchantments.get("minecraft:multishot"), 25);
        assertEquals(config.enchantments.get("minecraft:piercing"), 26);
        assertEquals(config.enchantments.get("minecraft:power"), 27);
        assertEquals(config.enchantments.get("minecraft:projectile_protection"), 28);
        assertEquals(config.enchantments.get("minecraft:protection"), 29);
        assertEquals(config.enchantments.get("minecraft:punch"), 30);
        assertEquals(config.enchantments.get("minecraft:quick_charge"), 31);
        assertEquals(config.enchantments.get("minecraft:respiration"), 32);
        assertEquals(config.enchantments.get("minecraft:riptide"), 33);
        assertEquals(config.enchantments.get("minecraft:silk_touch"), 35);
        assertEquals(config.enchantments.get("minecraft:soul_speed"), 37);
        assertEquals(config.enchantments.get("minecraft:sweeping_edge"), 38);
        assertEquals(config.enchantments.get("minecraft:swift_sneak"), 39);
        assertEquals(config.enchantments.get("minecraft:thorns"), 40);
        assertEquals(config.enchantments.get("minecraft:unbreaking"), 41);
        assertEquals(config.enchantments.get("minecraft:vanishing_curse"), 43);
        assertEquals(config.enchantments.get("minecraft:wind_burst"), 44);

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
        assertNew(loaded);

        assertEquals(malformed, new String(Files.readAllBytes(configPath), StandardCharsets.UTF_8));
    }

    @Test
    void handlesEmptyConfigFileGracefully(@TempDir Path dir) throws Exception {
        Path configPath = dir.resolve("sharpness6.json");
        Files.write(configPath, new byte[0]);
        List<String> warnings = new ArrayList<String>();

        SharpnessConfig loaded = SharpnessConfigStore.loadOrCreate(configPath, warnings::add);

        assertEquals(1, warnings.size());
        assertNew(loaded);
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
