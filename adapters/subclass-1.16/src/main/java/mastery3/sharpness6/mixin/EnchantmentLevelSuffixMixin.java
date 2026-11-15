package mastery3.sharpness6.mixin;

import mastery3.sharpness6.core.RomanNumerals;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Enchantment.class)
public abstract class EnchantmentLevelSuffixMixin {
    @ModifyArg(
            method = "getName(I)Lnet/minecraft/text/Text;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/text/MutableText;append(Lnet/minecraft/text/Text;)Lnet/minecraft/text/MutableText;", ordinal = 0),
            index = 0
    )
    private Text sharpness6$renderRaisedLevelSuffix(Text vanillaSuffix, int level) {
        if (level <= 10) {
            return vanillaSuffix;
        }
        return new LiteralText(RomanNumerals.toRoman(level));
    }
}
