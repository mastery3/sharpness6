package mastery3.sharpness6.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class RomanNumeralsTest {

    @Test
    void toRoman() {
        // 1-10
        assertEquals("I", RomanNumerals.toRoman(1));
        assertEquals("II", RomanNumerals.toRoman(2));
        assertEquals("III", RomanNumerals.toRoman(3));
        assertEquals("IV", RomanNumerals.toRoman(4));
        assertEquals("V", RomanNumerals.toRoman(5));
        assertEquals("VI", RomanNumerals.toRoman(6));
        assertEquals("VII", RomanNumerals.toRoman(7));
        assertEquals("VIII", RomanNumerals.toRoman(8));
        assertEquals("IX", RomanNumerals.toRoman(9));
        assertEquals("X", RomanNumerals.toRoman(10));

        // 11-50
        assertEquals("XI", RomanNumerals.toRoman(11));
        assertEquals("XIII", RomanNumerals.toRoman(13));
        assertEquals("XIV", RomanNumerals.toRoman(14));
        assertEquals("XIX", RomanNumerals.toRoman(19));
        assertEquals("XX", RomanNumerals.toRoman(20));
        assertEquals("XXIX", RomanNumerals.toRoman(29));
        assertEquals("XXXIX", RomanNumerals.toRoman(39));
        assertEquals("XL", RomanNumerals.toRoman(40));
        assertEquals("XLV", RomanNumerals.toRoman(45));
        assertEquals("XLIX", RomanNumerals.toRoman(49));
        assertEquals("L", RomanNumerals.toRoman(50));

        // 51-100
        assertEquals("LX", RomanNumerals.toRoman(60));
        assertEquals("XC", RomanNumerals.toRoman(90));
        assertEquals("XCIV", RomanNumerals.toRoman(94));
        assertEquals("XCV", RomanNumerals.toRoman(95));
        assertEquals("XCIX", RomanNumerals.toRoman(99));
        assertEquals("C", RomanNumerals.toRoman(100));

        // 101-500
        assertEquals("CX", RomanNumerals.toRoman(110));
        assertEquals("CL", RomanNumerals.toRoman(150));
        assertEquals("CXC", RomanNumerals.toRoman(190));
        assertEquals("CXCV", RomanNumerals.toRoman(195));
        assertEquals("CXCIX", RomanNumerals.toRoman(199));
        assertEquals("CC", RomanNumerals.toRoman(200));
        assertEquals("CCLV", RomanNumerals.toRoman(255));
        assertEquals("CCC", RomanNumerals.toRoman(300));
        assertEquals("CD", RomanNumerals.toRoman(400));
        assertEquals("CDXLIV", RomanNumerals.toRoman(444));
        assertEquals("CDL", RomanNumerals.toRoman(450));
        assertEquals("CDXC", RomanNumerals.toRoman(490));
        assertEquals("CDXCV", RomanNumerals.toRoman(495));
        assertEquals("CDXCIX", RomanNumerals.toRoman(499));
        assertEquals("D", RomanNumerals.toRoman(500));

        // 501-1000
        assertEquals("DC", RomanNumerals.toRoman(600));
        assertEquals("DCCC", RomanNumerals.toRoman(800));
        assertEquals("CM", RomanNumerals.toRoman(900));
        assertEquals("CML", RomanNumerals.toRoman(950));
        assertEquals("CMXC", RomanNumerals.toRoman(990));
        assertEquals("CMXCV", RomanNumerals.toRoman(995));
        assertEquals("CMXCIX", RomanNumerals.toRoman(999));
        assertEquals("M", RomanNumerals.toRoman(1000));

        // 1001-3999
        assertEquals("MD", RomanNumerals.toRoman(1500));
        assertEquals("MCM", RomanNumerals.toRoman(1900));
        assertEquals("MCMXCIX", RomanNumerals.toRoman(1999));
        assertEquals("MM", RomanNumerals.toRoman(2000));
        assertEquals("MMXXIV", RomanNumerals.toRoman(2024));
        assertEquals("MMM", RomanNumerals.toRoman(3000));
        assertEquals("MMMCMXCIX", RomanNumerals.toRoman(3999));

        // Numbers not representable as Roman Numerals
        assertEquals("0", RomanNumerals.toRoman(0));
        assertEquals("-1", RomanNumerals.toRoman(-1));
        assertEquals("4000", RomanNumerals.toRoman(4000));
        assertEquals("2147483647", RomanNumerals.toRoman(2147483647));
        assertEquals("-2147483648", RomanNumerals.toRoman(-2147483648));
    }
}
