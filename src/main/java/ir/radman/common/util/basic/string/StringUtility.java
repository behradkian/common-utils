package ir.radman.common.util.basic.string;

import ir.radman.common.util.convertor.JsonConvertor;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Base64;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class that provides common and reusable string manipulation functions.
 * This class is final and cannot be instantiated.
 * All methods are static and null-safe.
 *
 * @author : Pedram Behradkian
 * @date : 2025/10/31
 */
public final class StringUtility {

    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String NULL = "null";
    public static final char DASH = '-';
    public static final char UNDERLINE = '_';
    public static final char SLASH = '/';
    public static final char BACK_SLASH = '\\';

    private StringUtility() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Counts how many times a specific character appears in the given text.
     *
     * @param text      input string (nullable)
     * @param character the character to count
     * @return number of occurrences, 0 if text is null
     */
    public static long countCharacter(String text, char character) {
        if (text == null) {
            return 0;
        } else {
            return text.chars().filter(ch -> ch == character).count();
        }
    }

    /**
     * Checks if a given string represents a valid number (integer or decimal).
     *
     * @param text input string
     * @return true if number, false otherwise
     */
    public static boolean isNumber(String text) {
        if (isBlank(text)) {
            return false;
        } else {
            return text.matches("^-?\\d+(\\.\\d+)?$");
        }
    }

    /**
     * Checks whether a string is null or blank.
     *
     * @param text string to check
     * @return true if null or blank
     */
    public static boolean isBlank(String text) {
        return text == null || text.isBlank();
    }

    /**
     * Adds a space between digits and letters in a text.
     * Example: "abc123def" → "abc 123 def"
     *
     * @param text input string
     * @return modified string with spaces
     */
    public static String addSpaceBetweenDigitAndWord(String text) {
        if (isBlank(text)) {
            return EMPTY;
        } else {
            return Pattern.compile("(?<=[^0-9])(?=[0-9])|(?<=[0-9])(?=[^0-9])").matcher(text).replaceAll(SPACE);
        }
    }

    /**
     * Converts an object to its JSON string representation.
     *
     * @param object any object
     * @return JSON string
     */
    public static String toJsonString(Object object) {
        return JsonConvertor.object2JsonString(object);
    }

    /**
     * Reverses the given string.
     *
     * @param text input string
     * @return reversed string
     */
    public static String reverse(String text) {
        if (text == null) {
            return EMPTY;
        } else {
            return new StringBuilder(text).reverse().toString();
        }
    }

    /**
     * Checks if a word is a palindrome.
     *
     * @param word input word
     * @return true if palindrome
     */
    public static boolean isPalindrome(String word) {
        if (isBlank(word)) {
            return false;
        } else {
            int left = 0;
            int right = word.length() - 1;
            while (left < right) {
                if (word.charAt(left) != word.charAt(right)) {
                    return false;
                }
                left++;
                right--;
            }
            return true;
        }
    }

    /**
     * Returns true if text contains only alphabetic letters.
     */
    public static boolean containsOnlyLetters(String text) {
        return text != null && text.matches("^\\p{L}+$");
    }

    /**
     * Returns true if text contains only digits.
     */
    public static boolean containsOnlyDigits(String text) {
        return text != null && text.matches("^\\d+$");
    }

    /**
     * Returns true if text contains both letters and digits.
     */
    public static boolean containsLettersAndDigits(String text) {
        return text != null && text.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$");
    }

    /**
     * Converts text to camelCase.
     */
    public static String toCamelCase(String input) {
        if (isBlank(input)) {
            return EMPTY;
        } else {
            StringBuilder result = new StringBuilder();
            boolean capitalizeNext = false;
            input = input.trim().toLowerCase();
            for (char ch : input.toCharArray()) {
                if (Character.isWhitespace(ch) || ch == DASH || ch == UNDERLINE) {
                    capitalizeNext = true;
                } else {
                    if (capitalizeNext) {
                        result.append(Character.toUpperCase(ch));
                        capitalizeNext = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
            if (!result.isEmpty()) {
                result.setCharAt(0, Character.toLowerCase(result.charAt(0)));
            }
            return result.toString();
        }
    }

    /**
     * Converts text to PascalCase.
     */
    public static String toPascalCase(String input) {
        if (isBlank(input)) {
            return EMPTY;
        } else {
            StringBuilder result = new StringBuilder();
            boolean capitalizeNext = true;
            input = input.trim().toLowerCase();
            for (char ch : input.toCharArray()) {
                if (Character.isWhitespace(ch) || ch == DASH || ch == UNDERLINE || !Character.isLetterOrDigit(ch)) {
                    capitalizeNext = true;
                } else {
                    if (capitalizeNext) {
                        result.append(Character.toUpperCase(ch));
                        capitalizeNext = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
            return result.toString();
        }
    }

    /**
     * Converts a text into snake_case format.
     */
    public static String toSnakeCase(String input) {
        if (isBlank(input)) {
            return EMPTY;
        } else {
            return input.trim()
                    .replaceAll("([a-z])([A-Z])", "$1_$2")
                    .replaceAll("[\\s-]+", "_")
                    .toLowerCase();
        }
    }

    /**
     * Converts a text into kebab-case format.
     */
    public static String toKebabCase(String input) {
        if (isBlank(input)) {
            return EMPTY;
        } else {
            return input.trim()
                    .replaceAll("([a-z])([A-Z])", "$1-$2")
                    .replaceAll("[\\s_]+", "-")
                    .toLowerCase();
        }
    }

    /**
     * Removes the first character of the string.
     */
    public static String removeFirstCharacter(String input) {
        if (input == null || input.isEmpty()) {
            return EMPTY;
        } else {
            return input.substring(1);
        }
    }

    /**
     * Trims spaces only from the start.
     */
    public static String trimStart(String input) {
        if (input == null) {
            return EMPTY;
        } else {
            return input.replaceAll("^\\s+", "");
        }
    }

    /**
     * Safely trims a string (returns empty string if null).
     */
    public static String safeTrim(String text) {
        if (text == null) {
            return EMPTY;
        } else {
            return text.trim();
        }
    }

    /**
     * Returns default value if text is blank.
     */
    public static String defaultIfBlank(String text, String defaultValue) {
        if (isBlank(text)) {
            return defaultValue;
        } else {
            return text;
        }
    }

    /**
     * Truncates text to a maximum length.
     */
    public static String truncate(String text, int maxLength) {
        if (text == null) {
            return EMPTY;
        } else if (text.length() <= maxLength) {
            return text;
        } else {
            return text.substring(0, maxLength);
        }
    }

    /**
     * Normalizes multiple spaces into a single space.
     */
    public static String normalizeSpaces(String text) {
        if (isBlank(text)) {
            return EMPTY;
        } else {
            return text.trim().replaceAll("\\s{2,}", SPACE);
        }
    }

    /**
     * Joins non-blank parts using a delimiter.
     */
    public static String joinNonBlank(String delimiter, String... parts) {
        if (parts == null || parts.length == 0) {
            return EMPTY;
        } else {
            return Stream.of(parts)
                    .filter(p -> !isBlank(p))
                    .collect(Collectors.joining(delimiter));
        }
    }

    /**
     * Pads string on the left with the given character.
     */
    public static String padLeft(String text, int length, char padChar) {
        if (text == null) {
            text = EMPTY;
        }
        if (text.length() >= length) {
            return text;
        } else {
            return String.valueOf(padChar).repeat(length - text.length()) + text;
        }
    }

    /**
     * Pads string on the right with the given character.
     */
    public static String padRight(String text, int length, char padChar) {
        if (text == null) {
            text = EMPTY;
        }
        if (text.length() >= length) {
            return text;
        } else {
            return text + String.valueOf(padChar).repeat(length - text.length());
        }
    }

    /**
     * Removes accents and diacritics from a string.
     */
    public static String stripAccents(String input) {
        if (isBlank(input)) {
            return input;
        } else {
            String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
            return normalized.replaceAll("\\p{M}", "");
        }
    }

    /**
     * Converts text into a URL-friendly slug.
     */
    public static String slugify(String input) {
        if (isBlank(input)) {
            return EMPTY;
        } else {
            String noAccents = stripAccents(input);
            String slug = Pattern.compile("[^\\p{L}\\p{Nd}]+").matcher(noAccents).replaceAll("-");
            return slug.toLowerCase(Locale.ROOT).replaceAll("-{2,}", "-").replaceAll("^-|-$", "");
        }
    }

    /**
     * Compares two strings ignoring case.
     */
    public static boolean equalsIgnoreCase(String firstText, String secondText) {
        if (firstText == null && secondText == null) {
            return true;
        } else if (firstText == null || secondText == null) {
            return false;
        } else {
            return firstText.equalsIgnoreCase(secondText);
        }
    }

    /**
     * Compares two strings safely (null-safe equals).
     */
    public static boolean equals(String firstText, String secondText) {
        if (firstText == null && secondText == null) {
            return true;
        } else if (firstText == null || secondText == null) {
            return false;
        } else {
            return firstText.equals(secondText);
        }
    }

    /**
     * Converts Base64-encoded Arabic digits to normal decimal digits.
     */
    public static String convertArabicToNumber(String data) {
        Base64.Decoder decoder = Base64.getDecoder();
        String decoded = new String(decoder.decode(data), StandardCharsets.UTF_8);
        return arabicToDecimal(decoded);
    }

    private static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669) {
                ch -= 0x0660 - '0';
            } else if (ch >= 0x06f0 && ch <= 0x06F9) {
                ch -= 0x06f0 - '0';
            }
            chars[i] = ch;
        }
        return new String(chars);
    }

    public static String repeat(String text, int count) {
        if (count <= 0 || isBlank(text)) {
            return EMPTY;
        } else {
            return text.repeat(count);
        }
    }

    public static String capitalize(String text) {
        if (isBlank(text)) {
            return EMPTY;
        } else {
            return Character.toUpperCase(text.charAt(0)) + text.substring(1);
        }
    }

    public static String removeNonAlphanumeric(String input) {
        if (isBlank(input)) {
            return EMPTY;
        } else {
            return input.replaceAll("[^A-Za-z0-9]", "");
        }
    }

    public static int countWords(String input) {
        if (isBlank(input)) {
            return 0;
        } else {
            return input.trim().split("\\s+").length;
        }
    }

    public static String abbreviate(String input, int maxLength) {
        if (isBlank(input) || maxLength < 4) {
            return input;
        } else if (input.length() <= maxLength) {
            return input;
        } else {
            return input.substring(0, maxLength - 3) + "...";
        }
    }
}