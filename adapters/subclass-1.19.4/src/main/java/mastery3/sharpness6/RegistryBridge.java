package mastery3.sharpness6;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

/**
 * Resolves enchantment registry identifiers on Minecraft 1.19.4 - 1.20.4.
 */
final class RegistryBridge {

    private RegistryBridge() {
    }

    static String getEnchantmentId(Enchantment enchantment) {
        Identifier id = Registries.ENCHANTMENT.getId(enchantment);
        return id == null ? null : id.toString();
    }
}
