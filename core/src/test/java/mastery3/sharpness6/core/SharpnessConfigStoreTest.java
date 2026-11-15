package mastery3.sharpness6.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class SharpnessConfigStoreTest {

    @Test
    void createsDefaultConfigWithNormalizedFallbacks(@TempDir Path dir) {
        Path configPath = dir.resolve("sharpness6.json");
        List<String> warnings = new ArrayList<String>();

        SharpnessConfig config = SharpnessConfigStore.loadOrCreate(configPath, warnings::add);

        assertTrue(Files.exists(configPath));
        assertTrue(warnings.isEmpty());
        assertEquals(5, config.sharpness);
        assertEquals(5, config.smite);
        assertEquals(5, config.bane_of_arthropods);
        assertEquals(4, config.protection);
        assertEquals(4, config.fire_protection);
        assertEquals(4, config.blast_protection);
        assertEquals(4, config.projectile_protection);
        assertEquals(4, config.feather_falling);
        assertEquals(3, config.lunge);
        assertFalse(config.bypassTooExpensive);
        assertEquals(39, config.anvilMaxCost);
    }

    @Test
    void preservesLegacyFallbackBehaviorWhenSecondaryFieldsOmitted(@TempDir Path dir) throws Exception {
        Path configPath = dir.resolve("sharpness6.json");
        Files.write(configPath, "{\"sharpness\": 10, \"protection\": 8}".getBytes(StandardCharsets.UTF_8));

        SharpnessConfig config = SharpnessConfigStore.loadOrCreate(configPath, null);

        assertEquals(10, config.sharpness);
        assertEquals(10, config.smite);
        assertEquals(10, config.bane_of_arthropods);
        assertEquals(8, config.protection);
        assertEquals(8, config.fire_protection);
        assertEquals(8, config.feather_falling);
        assertNotNull(config.customMaxLevels);
    }

    @Test
    void doesNotOverwriteMalformedJsonConfig(@TempDir Path dir) throws Exception {
        Path configPath = dir.resolve("sharpness6.json");
        String malformed = "{ \"sharpness\": 10, UNCLOSED ";
        Files.write(configPath, malformed.getBytes(StandardCharsets.UTF_8));
        List<String> warnings = new ArrayList<String>();

        SharpnessConfig loaded = SharpnessConfigStore.loadOrCreate(configPath, warnings::add);

        assertFalse(warnings.isEmpty());
        assertEquals(5, loaded.sharpness);
        assertEquals(malformed, new String(Files.readAllBytes(configPath), StandardCharsets.UTF_8));
    }

    @Test
    void handlesEmptyConfigFileGracefully(@TempDir Path dir) throws Exception {
        Path configPath = dir.resolve("sharpness6.json");
        Files.write(configPath, new byte[0]);
        List<String> warnings = new ArrayList<String>();

        SharpnessConfig loaded = SharpnessConfigStore.loadOrCreate(configPath, warnings::add);

        assertEquals(1, warnings.size());
        assertEquals(5, loaded.sharpness);
    }
}
