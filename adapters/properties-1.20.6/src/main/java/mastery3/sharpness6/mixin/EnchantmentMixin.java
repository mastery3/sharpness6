package mastery3.sharpness6.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import mastery3.sharpness6.Entry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.OptionalInt;

/**
 * Overrides {@link Enchantment#getMaxLevel()} based on registry identifier lookup.
 */
@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @ModifyReturnValue(method = "getMaxLevel", at = @At("RETURN"))
    private int sharpness6$modifyMaxLevel(int original) {
        Identifier id = Registries.ENCHANTMENT.getId((Enchantment) (Object) this);
        if (id == null) {
            return original;
        }
        OptionalInt override = Entry.getPolicy().resolveOverrideForVanillaBaseline(id.toString(), original);
        return override.isPresent() ? override.getAsInt() : original;
    }
}
