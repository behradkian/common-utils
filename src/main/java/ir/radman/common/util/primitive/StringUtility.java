package ir.radman.common.util.primitive;

import ir.radman.common.util.convertor.JsonConvertor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

public final class StringUtility {

    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String NULL = "null";
    public static final char DASH = '-';
    public static final char SLASH = '/';
    public static final char BACK_SLASH = '\\';
    public static final char UNDERLINE = '_';

    private StringUtility() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static int countCharacter(String text, char character) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == character) {
                count++;
            }
        }
        return count;
    }

    public static boolean isNumber(String text) {
        if (isBlank(text)) {
            return false;
        }
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isBlank(String text) {
        return text == null || text.equals(EMPTY) || text.equals(SPACE);
    }

    public static String addSpaceBetweenDigitAndWord(String digitWord) {
        return Pattern.compile("(?<=[^0-9])(?=[0-9])|(?<=[0-9])(?=[^0-9])").matcher(digitWord).find() ? digitWord.replaceAll("(?<=[^0-9])(?=[0-9])|(?<=[0-9])(?=[^0-9])", SPACE) : digitWord;
    }

    public static String toString(Object object) {
        return JsonConvertor.object2JsonString(object);
    }

    public static String reverse(String text) {
        StringBuilder builder = new StringBuilder(text);
        return builder.reverse().toString();
    }

    public static boolean isPalindrome(String word) {
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

    public static String toCamelCase(String input) {
        if (isBlank(input)) {
            return EMPTY;
        }
        StringBuilder camelCaseString = new StringBuilder();
        boolean capitalizeNext = false;
        input = input.trim().toLowerCase();
        for (char ch : input.toCharArray()) {
            if (Character.isWhitespace(ch) || ch == DASH || ch == UNDERLINE) {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    camelCaseString.append(Character.toUpperCase(ch));
                    capitalizeNext = false;
                } else {
                    camelCaseString.append(ch);
                }
            }
        }
        if (!camelCaseString.isEmpty()) {
            camelCaseString.setCharAt(0, Character.toLowerCase(camelCaseString.charAt(0)));
        }
        return camelCaseString.toString();
    }

    public static String toPascalCase(String input) {
        if (isBlank(input)) {
            return EMPTY;
        }
        StringBuilder pascalCaseString = new StringBuilder();
        boolean capitalizeNext = true;
        input = input.trim().toLowerCase();
        for (char ch : input.toCharArray()) {
            if (Character.isWhitespace(ch) || ch == DASH || ch == UNDERLINE || !Character.isLetterOrDigit(ch)) {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    pascalCaseString.append(Character.toUpperCase(ch));
                    capitalizeNext = false;
                } else {
                    pascalCaseString.append(ch);
                }
            }
        }
        if (pascalCaseString.isEmpty()) {
            return EMPTY;
        }
        return pascalCaseString.toString();
    }

    public static String beforeTrim(String input) {
        if (!isBlank(input)) {
            return input.substring(1);
        }
        return EMPTY;
    }

    public static String convertArabicToNumber(String data) {
        Base64.Decoder decoder = Base64.getDecoder();
        String decoded = new String(decoder.decode(data), StandardCharsets.UTF_8);
        return arabicToDecimal(decoded);
    }

    private static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }
}