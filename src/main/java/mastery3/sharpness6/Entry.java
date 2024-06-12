// Credit to the Actually Unbreaking developers for most of the config code since I'm too lazy to write a proper config

package mastery3.sharpness6;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Entry implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("sharpness6");

	public static AUConfig config;
    private static Entry instance;

    public static Entry getInstance() {
        return instance;
    }

    public void onInitialize() {
        loadConfig();
        instance = this;
		LOGGER.info("Go make that OP weapon :)");
    }
    // config code based bedrockify config code
    // https://github.com/juancarloscp52/BedrockIfy/blob/1.17.x/src/main/java/me/juancarloscp52/bedrockify/Bedrockify.java
    public static void loadConfig() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "sharpness6.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (configFile.exists()) {
            try {
                FileReader fileReader = new FileReader(configFile);
                config = gson.fromJson(fileReader, AUConfig.class);
                fileReader.close();
                
            } catch (IOException e) {
                LOGGER.warn("could not load sharpness6 config options: " + e.getLocalizedMessage());
            }
        } else {
            config = new AUConfig();
        }
        if (config.smite == null) { config.smite = config.sharpness; }
        if (config.bane_of_arthropods == null) { config.bane_of_arthropods = config.sharpness; }
        if (config.fire_protection == null) { config.fire_protection = config.protection; }
        if (config.blast_protection == null) { config.blast_protection = config.protection; }
        if (config.projectile_protection == null) { config.projectile_protection = config.protection; }
        if (config.feather_falling == null) { config.feather_falling = config.protection; }
        saveConfig();
    }

    public static void saveConfig() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "sharpness6.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdir();
        }
        try {
            FileWriter fileWriter = new FileWriter(configFile);
            fileWriter.write(gson.toJson(config));
            fileWriter.close();
        } catch (IOException e) {
            LOGGER.warn("could not save sharpness6 config options: " + e.getLocalizedMessage());
        }
    }
}
