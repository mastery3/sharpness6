package mastery3.sharpness6.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import mastery3.sharpness6.Entry;

@Mixin(net.minecraft.enchantment.ThornsEnchantment.class)
public class ThornsMixin {
	@Overwrite
	public int getMaxLevel() {
		return Entry.getInstance().config.thorns;
	}
}