package mastery3.sharpness6.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mastery3.sharpness6.Entry;
import mastery3.sharpness6.core.AnvilPolicy;
import mastery3.sharpness6.core.SharpnessConfig;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Optionally lifts or clamps the vanilla anvil "Too Expensive!" cap while preserving stacked-item checks.
 */
@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin {

    @ModifyArg(
            method = "updateResult",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/Property;set(I)V"),
            index = 0
    )
    private int sharpness6$adjustAnvilCost(int cost) {
        SharpnessConfig config = Entry.getPolicy().config();
        int inputCount = ((ForgingScreenHandlerAccessor) this).sharpness6$getInput().getStack(0).getCount();
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
    private boolean sharpness6$bypassUncappedTooExpensiveLegacyCreative(boolean creativeMode) {
        return sharpness6$resolveCreativeBypass(creativeMode);
    }

    @ModifyExpressionValue(
            method = "updateResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isInCreativeMode()Z",
                    ordinal = 1
            ),
            require = 0
    )
    private boolean sharpness6$bypassUncappedTooExpensiveMethodCreative(boolean creativeMode) {
        return sharpness6$resolveCreativeBypass(creativeMode);
    }

    private boolean sharpness6$resolveCreativeBypass(boolean hasCreative) {
        if (hasCreative) {
            return true;
        }
        SharpnessConfig config = Entry.getPolicy().config();
        if (config.anvilMaxCost > 0) {
            return false;
        }
        int inputCount = ((ForgingScreenHandlerAccessor) this).sharpness6$getInput().getStack(0).getCount();
        return AnvilPolicy.shouldBypassExpensiveCheck(inputCount, config);
    }
}
