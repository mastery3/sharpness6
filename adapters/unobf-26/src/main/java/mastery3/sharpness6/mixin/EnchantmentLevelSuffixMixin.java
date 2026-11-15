package mastery3.sharpness6.mixin;

import mastery3.sharpness6.core.RomanNumerals;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Enchantment.class)
public abstract class EnchantmentLevelSuffixMixin {
    @ModifyArg(
            method = "getFullname(Lnet/minecraft/core/Holder;I)Lnet/minecraft/network/chat/Component;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;append(Lnet/minecraft/network/chat/Component;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 1),
            index = 0
    )
    private static Component sharpness6$renderRaisedLevelSuffix(Component vanillaSuffix, Holder<Enchantment> holder, int level) {
        if (level <= 10) {
            return vanillaSuffix;
        }
        return Component.literal(RomanNumerals.toRoman(level));
    }
}
