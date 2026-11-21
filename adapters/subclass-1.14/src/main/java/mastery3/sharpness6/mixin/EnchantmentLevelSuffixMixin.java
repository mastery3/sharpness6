package mastery3.sharpness6.mixin;

import mastery3.sharpness6.core.RomanNumerals;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
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
        Text name = new TranslatableText(self.getTranslationKey());
        name = self.isCursed() ? name.formatted(Formatting.RED) : name.formatted(Formatting.GRAY);
        if (level != 1 || self.getMaximumLevel() != 1) {
            name.append(" ");
            name.append(new LiteralText(RomanNumerals.toRoman(level)));
        }
        cir.setReturnValue(name);
        cir.cancel();
    }
}
