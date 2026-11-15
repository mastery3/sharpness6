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

    // Ordinal-free on purpose: every Property.set call inside updateResult is a cost write, so all of them
    // get the clamp. If vanilla adds a set() call that must NOT be clamped, revisit this (require=1 only
    // fails if the target disappears entirely).
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

    // Creative-mode check shape varies by version (field read vs isInCreativeMode()); exactly one of these
    // two hedges binds per version. Both require=0: the non-matching hedge silently no-ops instead of
    // failing the mixin apply, so one hook covers several minors around 1.21.x.
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
