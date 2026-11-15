package mastery3.sharpness6.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Canonical Roman numeral conversion and language-entry generation for enchantment levels above 10.
 */
public final class RomanNumerals {

    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    private RomanNumerals() {
    }

    /**
     * Converts a positive integer in {@code [1, 3999]} to standard Roman numerals.
     * Values outside that range fall back to base-10 string formatting.
     */
    public static String toRoman(int number) {
        if (number < 1 || number > 3999) {
            return Integer.toString(number);
        }
        int remaining = number;
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < VALUES.length; i++) {
            while (remaining >= VALUES[i]) {
                remaining -= VALUES[i];
                out.append(SYMBOLS[i]);
            }
        }
        return out.toString();
    }

    /**
     * Generates {@code enchantment.level.<n>} -> Roman numeral entries for levels {@code minLevel..maxLevel}.
     */
    public static Map<String, String> enchantmentLevelEntries(int minLevel, int maxLevel) {
        if (maxLevel < minLevel) {
            return Collections.emptyMap();
        }
        Map<String, String> entries = new LinkedHashMap<String, String>();
        for (int level = minLevel; level <= maxLevel; level++) {
            entries.put("enchantment.level." + level, toRoman(level));
        }
        return Collections.unmodifiableMap(entries);
    }
}
