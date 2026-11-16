package mastery3.sharpness6.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class AnvilPolicyAndRomanTest {

    @Test
    void formatsRomanNumeralsThrough255Accurately() {
        assertEquals("VI", RomanNumerals.toRoman(6));
        assertEquals("X", RomanNumerals.toRoman(10));
        assertEquals("XI", RomanNumerals.toRoman(11));
        assertEquals("XL", RomanNumerals.toRoman(40));
        assertEquals("XCIV", RomanNumerals.toRoman(94));
        assertEquals("C", RomanNumerals.toRoman(100));
        assertEquals("CCLV", RomanNumerals.toRoman(255));
    }
}
