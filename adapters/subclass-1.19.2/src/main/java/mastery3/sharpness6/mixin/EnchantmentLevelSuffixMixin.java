package mastery3.sharpness6.mixin;

import mastery3.sharpness6.core.RomanNumerals;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentLevelSuffixMixin {
    @Inject(method = "getName(I)Lnet/minecraft/text/Text;", at = @At("HEAD"), cancellable = true)
    private void sharpness6$renderRaisedLevelSuffix(int level, CallbackInfoReturnable<Text> cir) {
        if (level <= 10) {
            return;
        }
        Enchantment self = (Enchantment) (Object) this;
        MutableText name = Text.translatable(self.getTranslationKey());
        name = self.isCursed() ? name.formatted(Formatting.RED) : name.formatted(Formatting.GRAY);
        if (level != 1 || self.getMaxLevel() != 1) {
            name.append("");
            name.append(Text.literal(RomanNumerals.toRoman(level)));
        }
        cir.setReturnValue(name);
        cir.cancel();
    }
}
