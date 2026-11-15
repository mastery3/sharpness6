package mastery3.sharpness6.mixin;

import mastery3.sharpness6.LegacyEnchantmentHooks;
import net.minecraft.enchantment.AquaAffinityEnchantment;
import net.minecraft.enchantment.BindingCurseEnchantment;
import net.minecraft.enchantment.ChannelingEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.FlameEnchantment;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.enchantment.MultishotEnchantment;
import net.minecraft.enchantment.SilkTouchEnchantment;
import net.minecraft.enchantment.VanishingCurseEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides explicit level-1 {@link Enchantment#getMaxLevel()} implementations retained on Minecraft 1.16.5 - 1.19.2.
 */
@Mixin({
        AquaAffinityEnchantment.class,
        BindingCurseEnchantment.class,
        ChannelingEnchantment.class,
        FlameEnchantment.class,
        InfinityEnchantment.class,
        MendingEnchantment.class,
        MultishotEnchantment.class,
        SilkTouchEnchantment.class,
        VanishingCurseEnchantment.class
})
public abstract class SingleLevelSubclassesMixin {

    @Inject(method = "getMaxLevel", at = @At("RETURN"), cancellable = true)
    private void sharpness6$modifySingleLevelMax(CallbackInfoReturnable<Integer> cir) {
        LegacyEnchantmentHooks.applyMaxLevelOverride((Enchantment) (Object) this, cir);
    }
}
