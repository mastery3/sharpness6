package mastery3.sharpness6.mixin;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ForgingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Exposes {@code ForgingScreenHandler.input} cleanly across 1.20.6.
 */
@Mixin(ForgingScreenHandler.class)
public interface ForgingScreenHandlerAccessor {

    @Accessor("input")
    Inventory sharpness6$getInput();
}
