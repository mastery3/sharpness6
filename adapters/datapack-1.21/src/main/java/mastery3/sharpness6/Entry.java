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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Fabric entrypoint and shared runtime policy holder for Minecraft 1.21.x data-driven registries.
 */
public final class Entry implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("sharpness6");
    private static final Set<String> MODIFIED_ENCHANTMENTS = ConcurrentHashMap.newKeySet();

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
        policy = new EnchantmentLimitPolicy(loaded, EnchantmentIds.DATA_DRIVEN_MAX_LEVEL_CAP, LOGGER::warn);
        MODIFIED_ENCHANTMENTS.clear();
    }

    public static void markModified(String rawId) {
        MODIFIED_ENCHANTMENTS.add(EnchantmentIds.canonicalize(rawId));
    }

    public static void clearModified(String rawId) {
        MODIFIED_ENCHANTMENTS.remove(EnchantmentIds.canonicalize(rawId));
    }

    public static boolean wasModified(String rawId) {
        return MODIFIED_ENCHANTMENTS.contains(EnchantmentIds.canonicalize(rawId));
    }
}
