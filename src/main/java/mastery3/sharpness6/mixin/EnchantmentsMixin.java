package mastery3.sharpness6.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mastery3.sharpness6.Entry;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Properties;

@Mixin(net.minecraft.enchantment.Enchantments.class)
public class EnchantmentsMixin {

	private static Properties modPropertiesMaxLevel(Properties oldProperties, int newLevel) {
		return new Properties(
			oldProperties.supportedItems(),
			oldProperties.primaryItems(),
			oldProperties.weight(),
			newLevel,
			oldProperties.minCost(),
			oldProperties.minCost(),
			oldProperties.anvilCost(),
			oldProperties.requiredFeatures(),
			oldProperties.slots()
		);
	}

	@Inject(method="register", at=@At("HEAD"), cancellable=true)
	private static void register(String name, Enchantment enchantment, CallbackInfoReturnable<Boolean> cir) {
		if (Entry.config == null) {
			Entry.loadConfig();
		}
		if (Entry.config == null) {
			throw new Error();
		}
		if (name == "protection" || name == "feather_falling" || name == "blast_protection" || name == "projectile_protection" || name == "fire_protection") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.protection);
		} else if (name == "sharpness" || name == "smite" || name == "bane_of_arthropods") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.sharpness);
		} else if (name == "power") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.power);
		} else if (name == "efficiency") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.efficiency);
		} else if (name == "unbreaking") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.unbreaking);
		}
    }
}