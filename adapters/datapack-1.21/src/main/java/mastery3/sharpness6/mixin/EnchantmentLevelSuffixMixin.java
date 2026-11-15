package mastery3.sharpness6.mixin;

import mastery3.sharpness6.core.RomanNumerals;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentLevelSuffixMixin {
    @Inject(method = "getName(Lnet/minecraft/registry/entry/RegistryEntry;I)Lnet/minecraft/text/Text;",
            at = @At("HEAD"), cancellable = true)
    private static void sharpness6$renderRaisedLevelSuffix(RegistryEntry<Enchantment> entry, int level,
                                                          CallbackInfoReturnable<Text> cir) {
        if (level <= 10) {
            return;
        }
        Enchantment ench = entry.value();
        MutableText name = ench.description().copy();
        if (entry.isIn(EnchantmentTags.CURSE)) {
            name = Texts.setStyleIfAbsent(name, Style.EMPTY.withColor(Formatting.RED));
        } else {
            name = Texts.setStyleIfAbsent(name, Style.EMPTY.withColor(Formatting.GRAY));
        }
        if (level != 1 || ench.getMaxLevel() != 1) {
            name.append(ScreenTexts.SPACE);
            name.append(Text.literal(RomanNumerals.toRoman(level)));
        }
        cir.setReturnValue(name);
        cir.cancel();
    }
}
