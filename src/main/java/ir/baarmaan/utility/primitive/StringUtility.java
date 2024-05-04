package ir.baarmaan.utility.primitive;

import ir.baarmaan.utility.convertor.JsonConvertor;

import java.util.regex.Pattern;

public class StringUtility {

    private StringUtility() {
    }

    public static final String SPACE = " ";

    public static final String EMPTY = "";

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

    public static boolean isBlank(String checkString){
        if(checkString == null || checkString.isEmpty() || checkString == "" || checkString.length() == 0)
            return true;
        return false;
    }

    public static String addSpaceBetweenDigitAndWord(String digitWord) {
        return Pattern.compile("(?<=[^0-9])(?=[0-9])|(?<=[0-9])(?=[^0-9])").matcher(digitWord).find() ? digitWord.replaceAll("(?<=[^0-9])(?=[0-9])|(?<=[0-9])(?=[^0-9])", " ") : digitWord;
    }

    public static String toString(Object object){
        return JsonConvertor.object2JsonString(object);
    }


}
