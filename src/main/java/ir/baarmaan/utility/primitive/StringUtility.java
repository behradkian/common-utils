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

}
