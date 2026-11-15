package mastery3.sharpness6;

import mastery3.sharpness6.core.EnchantmentIds;
import mastery3.sharpness6.core.EnchantmentLimitPolicy;
import mastery3.sharpness6.core.SharpnessConfig;
import mastery3.sharpness6.core.SharpnessConfigStore;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * Fabric entrypoint and shared runtime policy holder for Minecraft 1.20.6 property-based enchantments.
 */
public final class Entry implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("sharpness6");

    private static volatile EnchantmentLimitPolicy policy;

    @Override
    public void onInitialize() {
        reloadConfig();
        LOGGER.info("Loaded sharpness6 policy (sharpness max={}, bypassTooExpensive={}).",
                policy.config().sharpness, policy.config().bypassTooExpensive);
    }

    /**
     * Returns the initialized enchantment limit policy, loading {@code config/sharpness6.json} lazily if needed.
     */
    public static EnchantmentLimitPolicy getPolicy() {
        EnchantmentLimitPolicy current = policy;
        if (current == null) {
            synchronized (Entry.class) {
                current = policy;
                if (current == null) {
                    reloadConfig();
                    current = policy;
                }
            }
        }
        return current;
    }

    /**
     * Reloads {@code config/sharpness6.json} from disk cleanly.
     */
    public static synchronized void reloadConfig() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("sharpness6.json");
        SharpnessConfig loaded = SharpnessConfigStore.loadOrCreate(configPath, LOGGER::warn);
        policy = new EnchantmentLimitPolicy(loaded, EnchantmentIds.LEGACY_MAX_LEVEL_CAP, LOGGER::warn);
    }
}
