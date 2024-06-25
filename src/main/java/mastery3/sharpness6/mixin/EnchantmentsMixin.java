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
			oldProperties.maxCost(),
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

		switch (name) {
			case "protection": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.protection); break;
			case "feather_falling": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.feather_falling); break;
			case "blast_protection": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.blast_protection); break;
			case "projectile_protection": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.projectile_protection); break;
			case "fire_protection": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.fire_protection); break;
			case "sharpness": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.sharpness); break;
			case "smite": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.smite); break;
			case "bane_of_arthropods": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.bane_of_arthropods); break;
			case "channeling": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.channeling); break;
			case "power": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.power); break;
			case "efficiency": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.efficiency); break;
			case "unbreaking": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.unbreaking); break;
			case "riptide": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.riptide); break;
			case "fortune": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.fortune); break;
			case "piercing": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.piercing); break;
			case "mending": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.mending); break;
			case "fire_aspect": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.fire_aspect); break;
			case "sweeping_edge": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.sweeping_edge); break;
			case "loyalty": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.loyalty); break;
			case "knockback": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.knockback ); break;
			case "aqua_affinity": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.aqua_affinity); break;
			case "lure": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.lure); break;
			case "luck_of_the_sea": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.luck_of_the_sea); break;
			case "quick_charge": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.quick_charge); break;
			case "punch": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.punch); break;
			case "multishot": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.multishot); break;
			case "impaling": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.impaling); break;
			case "flame": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.flame); break;
			case "looting": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.looting); break;
			case "depth_strider": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.depth_strider); break;
			case "frost_walker": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.frost_walker); break;
			case "swift_sneak": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.swift_sneak); break;
			case "thorns": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.thorns); break;
			case "soul_speed": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.soul_speed); break;
			case "respiration": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.respiration); break;
			case "binding_curse": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.binding_curse); break;
			case "vanishing_curse": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.vanishing_curse); break;
			case "infinity": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.infinity); break;
			case "silk_touch": enchantment.properties = EnchantmentsMixin.modPropertiesMaxLevel(enchantment.properties, Entry.config.silk_touch); break;
		}
    }
}
