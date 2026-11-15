package mastery3.sharpness6.mixin;

import mastery3.sharpness6.Entry;
import mastery3.sharpness6.core.AnvilPolicy;
import mastery3.sharpness6.core.SharpnessConfig;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.AnvilScreenHandler;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Optionally lifts or clamps the vanilla anvil "Too Expensive!" cap on Legacy Fabric (1.12.2 - 1.13.2).
 */
@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin {

    @Shadow
    @Final
    private Inventory inventory;

    @Redirect(
            method = "updateResult",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/screen/AnvilScreenHandler;repairCost:I",
                    opcode = Opcodes.PUTFIELD
            )
    )
    private void sharpness6$adjustRepairCost(AnvilScreenHandler instance, int cost) {
        SharpnessConfig config = Entry.getPolicy().config();
        int inputCount = this.inventory.getInvStack(0).getCount();
        instance.repairCost = AnvilPolicy.adjustAnvilCost(cost, inputCount, config);
    }

    @Redirect(
            method = "updateResult",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/player/PlayerAbilities;creativeMode:Z",
                    opcode = Opcodes.GETFIELD,
                    ordinal = 1
            ),
            require = 0
    )
    private boolean sharpness6$bypassUncappedTooExpensive(PlayerAbilities abilities) {
        if (abilities.creativeMode) {
            return true;
        }
        SharpnessConfig config = Entry.getPolicy().config();
        if (config.anvilMaxCost > 0) {
            return false;
        }
        int inputCount = this.inventory.getInvStack(0).getCount();
        return AnvilPolicy.shouldBypassExpensiveCheck(inputCount, config);
    }
}
