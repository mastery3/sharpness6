package mastery3.sharpness6.mixin;

import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.DataResult;

import mastery3.sharpness6.Entry;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Definition;
import net.minecraft.registry.MutableRegistry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryInfo;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.resource.Resource;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;


@Mixin(net.minecraft.registry.RegistryLoader.class)
public class RegistryLoaderMixin {
	private static Definition modPropertiesMaxLevel(Definition oldDefinition, int newLevel) {
		return new Definition(
			oldDefinition.supportedItems(),
			oldDefinition.primaryItems(),
			oldDefinition.weight(),
			newLevel,
			oldDefinition.minCost(),
			oldDefinition.maxCost(),
			oldDefinition.anvilCost(),
			oldDefinition.slots()
		);
	}

	// Hacky workaround because @Inject is broken
	@Inject(method="parseAndAdd",
		at = @At(value = "HEAD"), cancellable=true)
	private static <E> void parseAndAdd(MutableRegistry<E> registry, com.mojang.serialization.Decoder<E> decoder, RegistryOps<com.google.gson.JsonElement> ops, RegistryKey<E> key, Resource resource, RegistryEntryInfo entryInfo, CallbackInfo ci) throws IOException {
		java.io.Reader reader = resource.getReader();

		try {
			JsonElement jsonElement = JsonParser.parseReader(reader);
			// Start of injection
			Identifier wholeRegistryIdentifier = registry.getKey().getValue();
			String wholeRegistryIdentifierString = wholeRegistryIdentifier.getNamespace() + ":" + wholeRegistryIdentifier.getPath();

			if (wholeRegistryIdentifierString.equals("minecraft:enchantment")) {
				Identifier individualEnchantmentIdentifier = key.getValue();
				String individualEnchantmentIdentifierString = individualEnchantmentIdentifier.getNamespace() + ":" + individualEnchantmentIdentifier.getPath();
				Entry.LOGGER.fatal(jsonElement.toString());
				switch (individualEnchantmentIdentifierString) {
					case "minecraft:protection": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.protection); break;
					case "minecraft:feather_falling": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.feather_falling); break;
					case "minecraft:blast_protection": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.blast_protection); break;
					case "minecraft:projectile_protection": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.projectile_protection); break;
					case "minecraft:fire_protection": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.fire_protection); break;
					case "minecraft:sharpness": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.sharpness); break;
					case "minecraft:smite": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.smite); break;
					case "minecraft:bane_of_arthropods": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.bane_of_arthropods); break;
					case "minecraft:channeling": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.channeling); break;
					case "minecraft:power": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.power); break;
					case "minecraft:efficiency": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.efficiency); break;
					case "minecraft:unbreaking":((JsonObject)jsonElement).addProperty("max_level", Entry.config.unbreaking); break;
					case "minecraft:riptide": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.riptide); break;
					case "minecraft:fortune": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.fortune); break;
					case "minecraft:piercing": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.piercing); break;
					case "minecraft:mending": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.mending); break;
					case "minecraft:fire_aspect": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.fire_aspect); break;
					case "minecraft:sweeping_edge": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.sweeping_edge); break;
					case "minecraft:loyalty": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.loyalty); break;
					case "minecraft:knockback": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.knockback ); break;
					case "minecraft:aqua_affinity": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.aqua_affinity); break;
					case "minecraft:lure": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.lure); break;
					case "minecraft:luck_of_the_sea": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.luck_of_the_sea); break;
					case "minecraft:quick_charge": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.quick_charge); break;
					case "minecraft:punch": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.punch); break;
					case "minecraft:multishot": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.multishot); break;
					case "minecraft:impaling": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.impaling); break;
					case "minecraft:flame": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.flame); break;
					case "minecraft:looting": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.looting); break;
					case "minecraft:depth_strider": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.depth_strider); break;
					case "minecraft:frost_walker": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.frost_walker); break;
					case "minecraft:swift_sneak": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.swift_sneak); break;
					case "minecraft:thorns": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.thorns); break;
					case "minecraft:soul_speed": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.soul_speed); break;
					case "minecraft:respiration": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.respiration); break;
					case "minecraft:binding_curse": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.binding_curse); break;
					case "minecraft:vanishing_curse": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.vanishing_curse); break;
					case "minecraft:infinity": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.impaling); break;
					case "minecraft:silk_touch": ((JsonObject)jsonElement).addProperty("max_level", Entry.config.silk_touch); break;
				}
			}
			// End of injection
			
			DataResult<E> dataResult = decoder.parse(ops, jsonElement);
			
			E object = dataResult.getOrThrow();
			registry.add(key, object, entryInfo);
		} catch (Throwable var11) {
			if (reader != null) {
				try {
				reader.close();
				} catch (Throwable var10) {
				var11.addSuppressed(var10);
				}
			}

			throw var11;
		}

		if (reader != null) {
			reader.close();
		}
		ci.cancel();
	}
}