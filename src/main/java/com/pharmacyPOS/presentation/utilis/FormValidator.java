package com.pharmacyPOS.presentation.utilis;

public class FormValidator {

    /**
     * Checks if a string is not null and not empty.
     *
     * @param input The string to check.
     * @return true if the string is not null and not empty.
     */
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Checks if a string is a valid number.
     *
     * @param input The string to check.
     * @return true if the string is a valid number.
     */
    public static boolean isNumber(String input) {
        if (input == null) {
            return false;
        }
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a string is within a specified length range.
     *
     * @param input The string to check.
     * @param min   The minimum length.
     * @param max   The maximum length.
     * @return true if the string's length is within the specified range.
     */
    public static boolean isLengthValid(String input, int min, int max) {
        if (input == null) {
            return false;
        }
        int length = input.length();
        return length >= min && length <= max;
    }

    /**
     * Checks if a string matches a given regular expression.
     *
     * @param input The string to check.
     * @param regex The regular expression to match against.
     * @return true if the string matches the regex.
     */
    public static boolean matchesRegex(String input, String regex) {
        if (input == null) {
            return false;
        }
        return input.matches(regex);
    }

    // Additional validation methods can be added here...
}
