package mastery3.sharpness6.mixin;

import mastery3.sharpness6.LegacyEnchantmentHooks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.SwiftSneakEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides {@link SwiftSneakEnchantment#getMaxLevel()} on Minecraft 1.19.2 - 1.20.4.
 */
@Mixin(SwiftSneakEnchantment.class)
public abstract class SwiftSneakEnchantmentMixin {

    @Inject(method = "getMaxLevel", at = @At("RETURN"), cancellable = true)
    private void sharpness6$modifySwiftSneakMaxLevel(CallbackInfoReturnable<Integer> cir) {
        LegacyEnchantmentHooks.applyMaxLevelOverride((Enchantment) (Object) this, cir);
    }
}
