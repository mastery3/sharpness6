package mastery3.sharpness6.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import mastery3.sharpness6.Entry;

@Mixin(net.minecraft.enchantment.PowerEnchantment.class)
public class PowerMixin {
	@Overwrite
	public int getMaxLevel() {
		return Entry.getInstance().config.power;
	}
}