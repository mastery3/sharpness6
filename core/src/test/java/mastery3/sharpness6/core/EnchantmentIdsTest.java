package mastery3.sharpness6.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class EnchantmentIdsTest {
    @Test
    void canonicalize() {
        assertNull(EnchantmentIds.canonicalize(null));
        assertTrue(EnchantmentIds.canonicalize("").isEmpty());
        assertTrue(EnchantmentIds.canonicalize(" \t\n").isEmpty());

        assertEquals(EnchantmentIds.canonicalize("minecraft:sharpness"), "minecraft:sharpness");
        assertEquals(EnchantmentIds.canonicalize("sharpness"), "minecraft:sharpness");
        assertEquals(EnchantmentIds.canonicalize(" sharpness "), "minecraft:sharpness");
        assertEquals(EnchantmentIds.canonicalize("SHARPNESS"), "minecraft:sharpness");

        assertEquals(EnchantmentIds.canonicalize("sharpnes"), "minecraft:sharpnes");
        assertEquals(EnchantmentIds.canonicalize(":sharpness"), ":sharpness");
        assertEquals(EnchantmentIds.canonicalize("othermod:sharpness"), "othermod:sharpness");

        assertEquals(EnchantmentIds.canonicalize(null, null), null);
        assertEquals(EnchantmentIds.canonicalize("minecraft", null), null);
        assertEquals(EnchantmentIds.canonicalize(null, "sharpness"), "minecraft:sharpness");

        assertEquals(EnchantmentIds.canonicalize("", "sharpness"), ":sharpness");
        assertEquals(EnchantmentIds.canonicalize("minecraft", ""), "minecraft:");

        assertEquals(EnchantmentIds.canonicalize("minecraft", "sharpness"), "minecraft:sharpness");
        assertEquals(EnchantmentIds.canonicalize(" minecraft ", " sharpness "), "minecraft:sharpness");
        assertEquals(EnchantmentIds.canonicalize("othermod", "sharpness"), "othermod:sharpness");

        assertEquals(EnchantmentIds.canonicalize("sweeping"), "minecraft:sweeping_edge");
        assertEquals(EnchantmentIds.canonicalize("minecraft", "sweeping"), "minecraft:sweeping_edge");
        assertEquals(EnchantmentIds.canonicalize("othermod", "sweeping"), "othermod:sweeping");
    }
}
