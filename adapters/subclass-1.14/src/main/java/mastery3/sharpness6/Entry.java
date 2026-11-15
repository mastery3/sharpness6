package mastery3.sharpness6;

import mastery3.sharpness6.core.EnchantmentIds;
import mastery3.sharpness6.core.EnchantmentLimitPolicy;
import mastery3.sharpness6.core.SharpnessConfig;
import mastery3.sharpness6.core.SharpnessConfigStore;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.OptionalInt;

/**
 * Fabric entrypoint and shared runtime policy holder for Minecraft 1.14.4 - 1.15.2.
 */
public final class Entry implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("sharpness6");

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

    /**
     * Resolves configured maximum level override for an enchantment instance, returning {@code original} when unchanged.
     */
    public static int resolveMaxLevel(Enchantment enchantment, int original) {
        Identifier id = Registry.ENCHANTMENT.getId(enchantment);
        if (id == null) {
            return original;
        }
        OptionalInt override = getPolicy().resolveOverrideForVanillaBaseline(id.toString(), original);
        return override.isPresent() ? override.getAsInt() : original;
    }

    /**
     * Applies any configured maximum level override to a mixin {@code getMaximumLevel()} callback.
     */
    public static void applyMaxLevelOverride(Enchantment enchantment, CallbackInfoReturnable<Integer> cir) {
        int original = cir.getReturnValueI();
        int updated = resolveMaxLevel(enchantment, original);
        if (updated != original) {
            cir.setReturnValue(updated);
        }
    }
}
