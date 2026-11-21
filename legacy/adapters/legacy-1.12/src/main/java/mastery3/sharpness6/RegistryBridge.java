package mastery3.sharpness6;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;

/**
 * Resolves enchantment registry identifiers on Minecraft 1.12.2.
 */
final class RegistryBridge {

    private RegistryBridge() {
    }

    static String getEnchantmentId(Enchantment enchantment) {
        Identifier id = (Identifier) Enchantment.REGISTRY.getIdentifier(enchantment);
        return id == null ? null : id.toString();
    }
}
