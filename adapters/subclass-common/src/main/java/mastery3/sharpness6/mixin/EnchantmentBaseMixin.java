package mastery3.sharpness6.mixin;

import mastery3.sharpness6.LegacyEnchantmentHooks;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides base {@link Enchantment#getMaxLevel()} for subclasses inheriting the default max level of 1.
 */
@Mixin(Enchantment.class)
public abstract class EnchantmentBaseMixin {

    @Inject(method = "getMaxLevel", at = @At("RETURN"), cancellable = true)
    private void sharpness6$modifyBaseMaxLevel(CallbackInfoReturnable<Integer> cir) {
        LegacyEnchantmentHooks.applyMaxLevelOverride((Enchantment) (Object) this, cir);
    }
}
