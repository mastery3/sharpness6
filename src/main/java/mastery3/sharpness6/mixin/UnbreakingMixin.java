package mastery3.sharpness6.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(net.minecraft.enchantment.UnbreakingEnchantment.class)
public class UnbreakingMixin {
	@Overwrite
	public int getMaxLevel() {
		return 10;
	}
}