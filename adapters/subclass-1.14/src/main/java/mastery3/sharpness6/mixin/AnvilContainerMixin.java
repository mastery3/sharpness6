package mastery3.sharpness6.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mastery3.sharpness6.Entry;
import mastery3.sharpness6.core.AnvilPolicy;
import mastery3.sharpness6.core.SharpnessConfig;
import net.minecraft.container.AnvilContainer;
import net.minecraft.container.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Optionally lifts or clamps the vanilla anvil "Too Expensive!" cap on Minecraft 1.14.4 - 1.15.2.
 */
@Mixin(AnvilContainer.class)
public abstract class AnvilContainerMixin {

    @ModifyArg(
            method = "updateResult",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/container/Property;set(I)V"),
            index = 0
    )
    private int sharpness6$adjustAnvilCost(int cost) {
        SharpnessConfig config = Entry.getPolicy().config();
        int inputCount = ((Container) (Object) this).getSlot(0).getStack().getCount();
        return AnvilPolicy.adjustAnvilCost(cost, inputCount, config);
    }

    @ModifyExpressionValue(
            method = "updateResult",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/player/PlayerAbilities;creativeMode:Z",
                    ordinal = 1
            ),
            require = 0
    )
    private boolean sharpness6$bypassUncappedTooExpensive(boolean creativeMode) {
        if (creativeMode) {
            return true;
        }
        SharpnessConfig config = Entry.getPolicy().config();
        if (config.anvilMaxCost > 0) {
            return false;
        }
        int inputCount = ((Container) (Object) this).getSlot(0).getStack().getCount();
        return AnvilPolicy.shouldBypassExpensiveCheck(inputCount, config);
    }
}
