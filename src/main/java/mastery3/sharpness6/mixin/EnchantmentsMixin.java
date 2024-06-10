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
		} else if (name == "riptide") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.riptide);
		} else if (name == "fortune") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.fortune);
		} else if (name == "piercing") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.piercing);
		} else if (name == "mending") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.mending);
		} else if (name == "fire_aspect") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.fire_aspect);
		} else if (name == "sweeping_edge") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.sweeping_edge);
		} else if (name == "looting") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.looting);
		} else if (name == "loyalty") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.loyalty);
		} else if (name == "knockback") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.knockback );
		} else if (name == "aqua_affinity") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.aqua_affinity);
		} else if (name == "impaling") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.impaling);
		} else if (name == "lure") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.lure);
		} else if (name == "luck") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.luck);
		} else if (name == "quick_charge") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.quick_charge);
		} else if (name == "punch") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.punch);
		} else if (name == "multishot") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.multishot);
		} else if (name == "impaling") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.impaling);
		} else if (name == "flame") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.flame);
		} else if (name == "looting") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.looting);
		} else if (name == "depth_strider") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.depth_strider);
		} else if (name == "frost_walker") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.frost_walker);
		} else if (name == "swift_sneak") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.swift_sneak);
		} else if (name == "thorns") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.thorns);
		} else if (name == "soul_speed") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.soul_speed);
		} else if (name == "respiration") {
			enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.respiration);

		}
    }
}