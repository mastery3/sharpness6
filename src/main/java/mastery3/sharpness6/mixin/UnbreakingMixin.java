package mastery3.sharpness6.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import mastery3.sharpness6.Entry;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

@Mixin(net.minecraft.enchantment.UnbreakingEnchantment.class)
public class UnbreakingMixin {
	@Overwrite
	public int getMaxLevel() {
		return Entry.getInstance().config.unbreaking;
	}

	@Inject(method="shouldPreventDamage(Lnet/minecraft/item/ItemStack;ILjava/util/Random;)Z", at=@At("HEAD"), cancellable=true)
	private static void shouldPreventDamage(ItemStack item, int level, Random random, CallbackInfoReturnable<Boolean> cir) {
		if (item.getItem() instanceof ArmorItem) {
			cir.setReturnValue(Boolean.valueOf(random.nextDouble()*((level + 1)/2) >= 1));
		 } else {
			cir.setReturnValue(Boolean.valueOf(random.nextInt(level + 1) > 0));
		 }
	}
}