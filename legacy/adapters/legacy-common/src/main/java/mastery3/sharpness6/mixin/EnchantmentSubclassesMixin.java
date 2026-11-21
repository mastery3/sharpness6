package mastery3.sharpness6.mixin;

import mastery3.sharpness6.LegacyEnchantmentHooks;
import net.minecraft.enchantment.AquaAffinityEnchantment;
import net.minecraft.enchantment.BetterLootEnchantment;
import net.minecraft.enchantment.BindingCurseEnchantment;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.DepthStriderEnchantment;
import net.minecraft.enchantment.EfficiencyEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.enchantment.FlameEnchantment;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.enchantment.KnockbackEnchantment;
import net.minecraft.enchantment.LureEnchantment;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.enchantment.PowerEnchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.enchantment.PunchEnchantment;
import net.minecraft.enchantment.RespirationEnchantment;
import net.minecraft.enchantment.SilkTouchEnchantment;
import net.minecraft.enchantment.SweepingEnchantment;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.enchantment.VanishingCurseEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides {@link Enchantment#getMaximumLevel()} across all shared vanilla subclasses on MC 1.12.2 - 1.13.2.
 */
@Mixin({
        AquaAffinityEnchantment.class,
        BetterLootEnchantment.class,
        BindingCurseEnchantment.class,
        DamageEnchantment.class,
        DepthStriderEnchantment.class,
        EfficiencyEnchantment.class,
        FireAspectEnchantment.class,
        FlameEnchantment.class,
        FrostWalkerEnchantment.class,
        InfinityEnchantment.class,
        KnockbackEnchantment.class,
        LureEnchantment.class,
        MendingEnchantment.class,
        PowerEnchantment.class,
        ProtectionEnchantment.class,
        PunchEnchantment.class,
        RespirationEnchantment.class,
        SilkTouchEnchantment.class,
        SweepingEnchantment.class,
        ThornsEnchantment.class,
        UnbreakingEnchantment.class,
        VanishingCurseEnchantment.class
})
public abstract class EnchantmentSubclassesMixin {

    @Inject(method = "getMaximumLevel", at = @At("RETURN"), cancellable = true)
    private void sharpness6$modifySubclassMaxLevel(CallbackInfoReturnable<Integer> cir) {
        LegacyEnchantmentHooks.applyMaxLevelOverride((Enchantment) (Object) this, cir);
    }
}
