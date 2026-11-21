package mastery3.sharpness6.mixin;

import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Decoder;
import mastery3.sharpness6.Entry;
import mastery3.sharpness6.core.EnchantmentRegistryPatcher;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryLoadTask;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.Resource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Patches parsed {@code minecraft:enchantment} JSON resources before codec decode and clears KnownPack
 * identity on modified entries so unmodified vanilla clients receive updated caps during login sync.
 */
@Mixin(targets = "net.minecraft.resources.RegistryLoadTask$PendingRegistration")
public final class PendingRegistrationMixin {

    @Inject(
            method = "loadFromResource",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/serialization/Decoder;parse(Lcom/mojang/serialization/DynamicOps;Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;"
            )
    )
    private static <T> void sharpness6$patchEnchantmentJson(
            Decoder<T> decoder,
            RegistryOps<JsonElement> ops,
            ResourceKey<T> key,
            Resource resource,
            CallbackInfoReturnable<?> cir,
            @Local JsonElement jsonElement) {
        if (!Registries.ENCHANTMENT.identifier().equals(key.registry())) {
            return;
        }
        String id = key.identifier().toString();
        EnchantmentRegistryPatcher.PatchResult result = EnchantmentRegistryPatcher.patchEnchantmentJson(
                id, jsonElement, Entry.getPolicy());
        if (result.modified()) {
            Entry.markModified(id);
        } else {
            Entry.clearModified(id);
        }
    }

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private static RegistrationInfo sharpness6$forceSyncModifiedEnchantment(
            RegistrationInfo originalInfo,
            ResourceKey<?> key) {
        if (Registries.ENCHANTMENT.identifier().equals(key.registry())
                && Entry.wasModified(key.identifier().toString())) {
            return RegistrationInfo.BUILT_IN;
        }
        return originalInfo;
    }
}
