package mastery3.sharpness6.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class AnvilPolicyAndRomanTest {

    @Test
    void adjustsAnvilCostOnlyForSingleInputWhenEnabledAndAboveCap() {
        SharpnessConfig config = new SharpnessConfig();
        config.bypassTooExpensive = true;
        config.anvilMaxCost = 39;

        assertEquals(15, AnvilPolicy.adjustAnvilCost(15, 1, config));
        assertEquals(39, AnvilPolicy.adjustAnvilCost(52, 1, config));
        // Stacked item safety check must keep cost >= 40 rejected.
        assertEquals(40, AnvilPolicy.adjustAnvilCost(40, 2, config));

        config.anvilMaxCost = -1;
        assertEquals(75, AnvilPolicy.adjustAnvilCost(75, 1, config));
        assertTrue(AnvilPolicy.shouldBypassExpensiveCheck(1, config));
        assertFalse(AnvilPolicy.shouldBypassExpensiveCheck(2, config));
    }

    @Test
    void formatsRomanNumeralsThrough255Accurately() {
        assertEquals("VI", RomanNumerals.toRoman(6));
        assertEquals("X", RomanNumerals.toRoman(10));
        assertEquals("XI", RomanNumerals.toRoman(11));
        assertEquals("XL", RomanNumerals.toRoman(40));
        assertEquals("XCIV", RomanNumerals.toRoman(94));
        assertEquals("C", RomanNumerals.toRoman(100));
        assertEquals("CCLV", RomanNumerals.toRoman(255));
        assertTrue(RomanNumerals.enchantmentLevelEntries(11, 255).containsKey("enchantment.level.255"));
        assertEquals(245, RomanNumerals.enchantmentLevelEntries(11, 255).size());
    }
}
