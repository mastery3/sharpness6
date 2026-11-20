package mastery3.sharpness6.mixin;

import mastery3.sharpness6.LegacyEnchantmentHooks;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.DepthStriderEnchantment;
import net.minecraft.enchantment.EfficiencyEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.enchantment.ImpalingEnchantment;
import net.minecraft.enchantment.KnockbackEnchantment;
import net.minecraft.enchantment.LoyaltyEnchantment;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.enchantment.LureEnchantment;
import net.minecraft.enchantment.PiercingEnchantment;
import net.minecraft.enchantment.PowerEnchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.enchantment.PunchEnchantment;
import net.minecraft.enchantment.QuickChargeEnchantment;
import net.minecraft.enchantment.RespirationEnchantment;
import net.minecraft.enchantment.RiptideEnchantment;
import net.minecraft.enchantment.SoulSpeedEnchantment;
import net.minecraft.enchantment.SweepingEnchantment;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.enchantment.UnbreakingEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides {@link Enchantment#getMaxLevel()} across all multi-level vanilla subclasses on Minecraft 1.16.5 - 1.20.4.
 */
@Mixin({
        DamageEnchantment.class,
        DepthStriderEnchantment.class,
        EfficiencyEnchantment.class,
        FireAspectEnchantment.class,
        FrostWalkerEnchantment.class,
        ImpalingEnchantment.class,
        KnockbackEnchantment.class,
        LoyaltyEnchantment.class,
        LuckEnchantment.class,
        LureEnchantment.class,
        PiercingEnchantment.class,
        PowerEnchantment.class,
        ProtectionEnchantment.class,
        PunchEnchantment.class,
        QuickChargeEnchantment.class,
        RespirationEnchantment.class,
        RiptideEnchantment.class,
        SoulSpeedEnchantment.class,
        SweepingEnchantment.class,
        ThornsEnchantment.class,
        UnbreakingEnchantment.class
})
public abstract class EnchantmentSubclassesMixin {

    @Inject(method = "getMaxLevel", at = @At("RETURN"), cancellable = true)
    private void sharpness6$modifySubclassMaxLevel(CallbackInfoReturnable<Integer> cir) {
        LegacyEnchantmentHooks.applyMaxLevelOverride((Enchantment) (Object) this, cir);
    }
}
