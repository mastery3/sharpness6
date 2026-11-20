package mastery3.sharpness6.mixin;

import mastery3.sharpness6.core.RomanNumerals;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.tags.EnchantmentTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentLevelSuffixMixin {
    @Inject(method = "getFullname(Lnet/minecraft/core/Holder;I)Lnet/minecraft/network/chat/Component;",
            at = @At("HEAD"), cancellable = true)
    private static void sharpness6$renderRaisedLevelSuffix(Holder<Enchantment> holder, int level,
                                                           CallbackInfoReturnable<Component> cir) {
        if (level <= 10) {
            return;
        }
        Enchantment ench = holder.value();
        MutableComponent name = ench.description().copy();
        if (holder.is(EnchantmentTags.CURSE)) {
            name = ComponentUtils.mergeStyles(name, Style.EMPTY.withColor(ChatFormatting.RED));
        } else {
            name = ComponentUtils.mergeStyles(name, Style.EMPTY.withColor(ChatFormatting.GRAY));
        }
        if (level != 1 || ench.getMaxLevel() != 1) {
            name.append(CommonComponents.SPACE);
            name.append(Component.literal(RomanNumerals.toRoman(level)));
        }
        cir.setReturnValue(name);
        cir.cancel();
    }
}
