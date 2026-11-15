package mastery3.sharpness6.mixin;

import mastery3.sharpness6.core.RomanNumerals;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Renders raised enchantment levels (11+) as Roman numerals itself instead of relying on the
 * shared "enchantment.level.N" lang keys, so the mod no longer pollutes the minecraft namespace
 * that other mods may read as a generic numeral formatter (issue #7).
 */
@Mixin(Enchantment.class)
public abstract class EnchantmentLevelSuffixMixin {

    @ModifyArg(
            method = "getName(Lnet/minecraft/registry/entry/RegistryEntry;I)Lnet/minecraft/text/Text;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/text/MutableText;append(Lnet/minecraft/text/Text;)Lnet/minecraft/text/MutableText;", ordinal = 1),
            index = 0
    )
    private static Text sharpness6$renderRaisedLevelSuffix(Text vanillaSuffix, RegistryEntry<Enchantment> entry, int level) {
        if (level <= 10) {
            return vanillaSuffix;
        }
        return Text.literal(RomanNumerals.toRoman(level));
    }
}
