package mastery3.sharpness6.mixin;

import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Decoder;
import mastery3.sharpness6.Entry;
import mastery3.sharpness6.core.EnchantmentRegistryPatcher;
import net.minecraft.registry.MutableRegistry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryLoader;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.entry.RegistryEntryInfo;
import net.minecraft.resource.Resource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/**
 * Patches parsed {@code minecraft:enchantment} JSON resources before codec decode and clears KnownPack
 * identity on modified entries so unmodified vanilla clients receive updated caps during login sync.
 */
@Mixin(RegistryLoader.class)
public final class RegistryLoaderMixin {

    @Inject(
            method = "parseAndAdd",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/serialization/Decoder;parse(Lcom/mojang/serialization/DynamicOps;Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;"
            )
    )
    private static <E> void sharpness6$patchEnchantmentJson(
            MutableRegistry<E> registry,
            Decoder<E> decoder,
            RegistryOps<JsonElement> ops,
            RegistryKey<E> key,
            Resource resource,
            RegistryEntryInfo entryInfo,
            CallbackInfo ci,
            @Local JsonElement jsonElement) {
        if (!RegistryKeys.ENCHANTMENT.getValue().equals(key.getRegistry())) {
            return;
        }
        String id = key.getValue().toString();
        EnchantmentRegistryPatcher.PatchResult result = EnchantmentRegistryPatcher.patchEnchantmentJson(
                id, jsonElement, Entry.getPolicy());
        if (result.modified()) {
            Entry.markModified(id);
        } else {
            Entry.clearModified(id);
        }
    }

    @ModifyArgs(
            method = "parseAndAdd",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/registry/MutableRegistry;add(Lnet/minecraft/registry/RegistryKey;Ljava/lang/Object;Lnet/minecraft/registry/entry/RegistryEntryInfo;)Lnet/minecraft/registry/entry/RegistryEntry$Reference;"
            )
    )
    private static void sharpness6$forceSyncModifiedEnchantment(Args args) {
        RegistryKey<?> key = args.get(0);
        if (RegistryKeys.ENCHANTMENT.getValue().equals(key.getRegistry())
                && Entry.wasModified(key.getValue().toString())) {
            args.set(2, RegistryEntryInfo.DEFAULT);
        }
    }
}
