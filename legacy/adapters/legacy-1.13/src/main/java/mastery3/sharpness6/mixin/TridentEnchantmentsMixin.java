package mastery3.sharpness6.mixin;

import mastery3.sharpness6.LegacyEnchantmentHooks;
import net.minecraft.enchantment.ChannelingEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ImpalingEnchantment;
import net.minecraft.enchantment.LoyaltyEnchantment;
import net.minecraft.enchantment.RiptideEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides {@link Enchantment#getMaximumLevel()} for Trident enchantments added in Minecraft 1.13.
 */
@Mixin({
        ChannelingEnchantment.class,
        ImpalingEnchantment.class,
        LoyaltyEnchantment.class,
        RiptideEnchantment.class
})
public abstract class TridentEnchantmentsMixin {

    @Inject(method = "getMaximumLevel", at = @At("RETURN"), cancellable = true)
    private void sharpness6$modifyTridentMaxLevel(CallbackInfoReturnable<Integer> cir) {
        LegacyEnchantmentHooks.applyMaxLevelOverride((Enchantment) (Object) this, cir);
    }
}
