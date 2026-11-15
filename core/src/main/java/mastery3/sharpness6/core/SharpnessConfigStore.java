package mastery3.sharpness6.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.function.Consumer;

/**
 * UTF-8 safe persistence layer for {@link SharpnessConfig}.
 * Preserves user-authored malformed files instead of overwriting them on syntax error.
 */
public final class SharpnessConfigStore {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private SharpnessConfigStore() {
    }

    /**
     * Loads config from {@code configPath}, writing defaults if the file does not exist or migrating null fields cleanly.
     */
    public static SharpnessConfig loadOrCreate(Path configPath, Consumer<String> warningSink) {
        if (!Files.exists(configPath)) {
            SharpnessConfig created = new SharpnessConfig();
            created.normalizeFallbacks();
            saveQuietly(configPath, created, warningSink);
            return created;
        }

        SharpnessConfig loaded = null;
        try (BufferedReader reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {
            loaded = GSON.fromJson(reader, SharpnessConfig.class);
        } catch (JsonParseException e) {
            warn(warningSink, "Malformed JSON in " + configPath + " (" + e.getMessage()
                    + "); using default values in memory without overwriting your file.");
            SharpnessConfig fallback = new SharpnessConfig();
            fallback.normalizeFallbacks();
            return fallback;
        } catch (IOException e) {
            warn(warningSink, "Failed to read config " + configPath + ": " + e.getMessage());
            SharpnessConfig fallback = new SharpnessConfig();
            fallback.normalizeFallbacks();
            return fallback;
        }

        if (loaded == null) {
            warn(warningSink, "Config file " + configPath + " was empty; restoring default configuration.");
            loaded = new SharpnessConfig();
        }
        loaded.normalizeFallbacks();
        saveQuietly(configPath, loaded, warningSink);
        return loaded;
    }

    /**
     * Atomically writes {@code config} as UTF-8 JSON to {@code configPath}.
     */
    public static void save(Path configPath, SharpnessConfig config) throws IOException {
        Path parent = configPath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path tempFile = Files.createTempFile(parent != null ? parent : configPath.toAbsolutePath().getParent(),
                "sharpness6-", ".json.tmp");
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8)) {
                GSON.toJson(config, writer);
            }
            try {
                Files.move(tempFile, configPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException atomicUnsupported) {
                Files.move(tempFile, configPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    private static void saveQuietly(Path configPath, SharpnessConfig config, Consumer<String> warningSink) {
        try {
            save(configPath, config);
        } catch (IOException e) {
            warn(warningSink, "Failed to save config " + configPath + ": " + e.getMessage());
        }
    }

    private static void warn(Consumer<String> warningSink, String message) {
        if (warningSink != null) {
            warningSink.accept(message);
        }
    }
}
