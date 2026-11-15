package mastery3.sharpness6.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mastery3.sharpness6.Entry;
import mastery3.sharpness6.core.AnvilPolicy;
import mastery3.sharpness6.core.SharpnessConfig;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ItemCombinerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Optionally lifts or clamps the vanilla anvil "Too Expensive!" cap while preserving stacked-item checks.
 */
@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {

    private AnvilMenuMixin() {
        super(null, 0, null, null, null);
    }

    @ModifyArg(
            method = "createResult",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/DataSlot;set(I)V"),
            index = 0
    )
    private int sharpness6$adjustAnvilCost(int cost) {
        SharpnessConfig config = Entry.getPolicy().config();
        int inputCount = this.inputSlots.getItem(0).getCount();
        return AnvilPolicy.adjustAnvilCost(cost, inputCount, config);
    }

    @ModifyExpressionValue(
            method = "createResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;hasInfiniteMaterials()Z",
                    ordinal = 1
            ),
            require = 0
    )
    private boolean sharpness6$bypassUncappedTooExpensive(boolean hasInfiniteMaterials) {
        if (hasInfiniteMaterials) {
            return true;
        }
        SharpnessConfig config = Entry.getPolicy().config();
        if (config.anvilMaxCost > 0) {
            return false;
        }
        Container slots = this.inputSlots;
        return AnvilPolicy.shouldBypassExpensiveCheck(slots.getItem(0).getCount(), config);
    }
}
