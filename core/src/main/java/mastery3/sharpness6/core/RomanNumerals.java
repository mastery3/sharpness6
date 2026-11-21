package mastery3.sharpness6.core;

/** Converts enchantment levels to conventional Roman numerals. */
public final class RomanNumerals {
    private RomanNumerals() {
    }

    /**
     * Formats levels 1 through 3999 using standard subtractive notation.
     * Other values are shown in decimal, as before.
     */
    public static String toRoman(int level) {
        if (level < 1 || level > 3999) {
            return Integer.toString(level);
        }

        StringBuilder result = new StringBuilder(15);

        // Thousands have no five- or ten-thousand symbol in this notation.
        for (int thousands = level / 1000; thousands > 0; thousands--) {
            result.append('M');
        }

        appendDigit(result, (level / 100) % 10, 'C', 'D', 'M');
        appendDigit(result, (level / 10) % 10, 'X', 'L', 'C');
        appendDigit(result, level % 10, 'I', 'V', 'X');

        return result.toString();
    }

    /** Writes one decimal digit in the corresponding Roman place value. */
    private static void appendDigit(StringBuilder out, int digit,
                                    char one, char five, char ten) {
        if (digit == 9) {
            out.append(one).append(ten);
            return;
        }

        if (digit >= 5) {
            out.append(five);
            digit -= 5;
        }

        if (digit == 4) {
            out.append(one).append(five);
        } else {
            for (int i = 0; i < digit; i++) {
                out.append(one);
            }
        }
    }
}
