package mastery3.sharpness6.mixin;

import mastery3.sharpness6.Entry;
import mastery3.sharpness6.core.EnchantmentLimitPolicy;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Applies smooth asymptotic armor durability scaling when {@code unbreakingArmorFix} is enabled.
 */
@Mixin(UnbreakingEnchantment.class)
public abstract class UnbreakingEnchantmentMixin {

    @Inject(method = "shouldPreventDamage", at = @At("HEAD"), cancellable = true)
    private static void sharpness6$scaleArmorUnbreaking(
            ItemStack item,
            int level,
            Random random,
            CallbackInfoReturnable<Boolean> cir) {
        EnchantmentLimitPolicy policy = Entry.getPolicy();
        if (policy.config().unbreakingArmorFix && item.getItem() instanceof ArmorItem) {
            cir.setReturnValue(EnchantmentLimitPolicy.shouldPreventArmorDamage(level, random.nextDouble()));
        }
    }
}
