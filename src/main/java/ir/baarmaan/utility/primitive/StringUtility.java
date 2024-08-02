package ir.baarmaan.utility.primitive;

import ir.baarmaan.utility.convertor.JsonConvertor;

import java.util.regex.Pattern;

public class StringUtility {

    public static final String SPACE = " ";
    public static final String EMPTY = "";

    private StringUtility() {
    }

    public static int countChar(String str, char c) {

        int count = 0;

        for (int i = 0; i < str.length(); i++)
            if (str.charAt(i) == c)
                count++;

        return count;
    }

    public static boolean isNumber(String numberString) {

        if (numberString == null || numberString.isEmpty())
            return false;

        Double doubleValue = null;
        try {
            doubleValue = Double.valueOf(Double.parseDouble(numberString));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isBlank(String checkString) {
        if (checkString == null || checkString.isEmpty() || checkString == EMPTY || checkString.length() == 0)
            return true;
        return false;
    }

    public static String addSpaceBetweenDigitAndWord(String digitWord) {
        return Pattern.compile("(?<=[^0-9])(?=[0-9])|(?<=[0-9])(?=[^0-9])").matcher(digitWord).find() ? digitWord.replaceAll("(?<=[^0-9])(?=[0-9])|(?<=[0-9])(?=[^0-9])", SPACE) : digitWord;
    }

    public static String toString(Object object) {
        return JsonConvertor.object2JsonString(object);
    }

    public static String reverse(String text) {
        StringBuilder reversed = new StringBuilder(text);
        return reversed.reverse().toString();
    }

    public static boolean isPalindrome(String word) {

        int left = 0;
        int right = word.length() - 1;

        while (left < right) {
            if (word.charAt(left) != word.charAt(right))
                return false;
            left++;
            right--;
        }
        return true;
    }

    public static String toCamelCase(String input) {

        if (input == null || input.isEmpty())
            return "";

        StringBuilder camelCaseString = new StringBuilder();
        boolean capitalizeNext = false;

        // Trim the input and convert to lowercase
        input = input.trim().toLowerCase();

        for (char ch : input.toCharArray()) {
            if (Character.isWhitespace(ch) || ch == '-' || ch == '_') {
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

        // Convert the first character to lowercase
        if (camelCaseString.length() > 0)
            camelCaseString.setCharAt(0, Character.toLowerCase(camelCaseString.charAt(0)));


        return camelCaseString.toString();
    }

    public static String toPascalCase(String input) {

        if (input == null || input.isEmpty())
            return "";

        StringBuilder pascalCaseString = new StringBuilder();
        boolean capitalizeNext = true; // Start by capitalizing the first letter

        // Trim the input and convert to lowercase
        input = input.trim().toLowerCase();

        for (char ch : input.toCharArray()) {
            if (Character.isWhitespace(ch) || ch == '-' || ch == '_' || !Character.isLetterOrDigit(ch)) {
                // Skip special characters and set flag to capitalize next valid character
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

        // Handle case where entire string is special characters or empty
        if (pascalCaseString.length() == 0) {
            return "";
        }

        return pascalCaseString.toString();
    }

}
