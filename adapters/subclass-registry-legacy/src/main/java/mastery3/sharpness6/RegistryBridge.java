package mastery3.sharpness6;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Resolves enchantment registry identifiers on Minecraft 1.16.5 - 1.19.2.
 */
final class RegistryBridge {

    private RegistryBridge() {
    }

    static String getEnchantmentId(Enchantment enchantment) {
        Identifier id = Registry.ENCHANTMENT.getId(enchantment);
        return id == null ? null : id.toString();
    }
}
