package mastery3.sharpness6.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(net.minecraft.enchantment.PowerEnchantment.class)
public class PowerMixin {
	@Overwrite
	public int getMaxLevel() {
		return 10;
	}
}