package mastery3.sharpness6.mixin;

import mastery3.sharpness6.Entry;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides base {@link Enchantment#getMaximumLevel()} on Minecraft 1.14.4 - 1.15.2.
 */
@Mixin(Enchantment.class)
public abstract class EnchantmentBaseMixin {

    @Inject(method = "getMaximumLevel", at = @At("RETURN"), cancellable = true)
    private void sharpness6$modifyBaseMaxLevel(CallbackInfoReturnable<Integer> cir) {
        Entry.applyMaxLevelOverride((Enchantment) (Object) this, cir);
    }
}
