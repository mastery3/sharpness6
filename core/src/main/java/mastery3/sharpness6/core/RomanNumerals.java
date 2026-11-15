package mastery3.sharpness6.core;

/**
 * Canonical Roman numeral conversion for enchantment levels above 10.
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
}