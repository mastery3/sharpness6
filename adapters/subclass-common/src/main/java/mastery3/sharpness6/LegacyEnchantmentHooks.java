package mastery3.sharpness6;

import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

/**
 * Shared runtime helper delegating registry ID resolution to the active version-era bridge.
 */
public final class LegacyEnchantmentHooks {

    private LegacyEnchantmentHooks() {
    }

    /**
     * Resolves configured maximum level override for an enchantment instance, returning {@code original} when unchanged.
     */
    public static int resolveMaxLevel(Enchantment enchantment, int original) {
        String id = RegistryBridge.getEnchantmentId(enchantment);
        if (id == null) {
            return original;
        }
        OptionalInt override = Entry.getPolicy().resolveOverrideForVanillaBaseline(id, original);
        return override.isPresent() ? override.getAsInt() : original;
    }

    /**
     * Applies any configured maximum level override to a mixin {@code getMaxLevel()} callback.
     */
    public static void applyMaxLevelOverride(Enchantment enchantment, CallbackInfoReturnable<Integer> cir) {
        int original = cir.getReturnValueI();
        int updated = resolveMaxLevel(enchantment, original);
        if (updated != original) {
            cir.setReturnValue(updated);
        }
    }
}
