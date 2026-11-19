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
        // Boundary and subtractive cases for the decimal-place design.
        assertEquals("I", RomanNumerals.toRoman(1));
        assertEquals("IV", RomanNumerals.toRoman(4));
        assertEquals("IX", RomanNumerals.toRoman(9));
        assertEquals("XIV", RomanNumerals.toRoman(14));
        assertEquals("XL", RomanNumerals.toRoman(40));
        assertEquals("XCIX", RomanNumerals.toRoman(99));
        assertEquals("CDXLIV", RomanNumerals.toRoman(444));
        assertEquals("CMXCIX", RomanNumerals.toRoman(999));
        assertEquals("MCMXCIX", RomanNumerals.toRoman(1999));
        assertEquals("MMXXIV", RomanNumerals.toRoman(2024));
        assertEquals("MMMCMXCIX", RomanNumerals.toRoman(3999));
        // Out-of-range fallback to decimal.
        assertEquals("0", RomanNumerals.toRoman(0));
        assertEquals("-5", RomanNumerals.toRoman(-5));
        assertEquals("4000", RomanNumerals.toRoman(4000));
        assertEquals("100000", RomanNumerals.toRoman(100000));
    }
}
