package ir.radman.common.util.convertor;

import ir.radman.common.util.primitive.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class WordsOfNumberConvertor {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordsOfNumberConvertor.class);

    private WordsOfNumberConvertor() {
    }

    public static String convertNumberToPersianWord(BigDecimal number){
        return convertNumberToPersianWord(number.toString());
    }

    public static String convertNumberToEnglishWord(BigDecimal number){
        return convertNumberToEnglishWord(number.toString());
    }

    public static String convertNumberToPersianWord(String number) {

        if (StringUtility.isNumber(number))
            return doNum(new BigDecimal(number), 0).trim();
        else return "";
    }

    public static String convertNumberToEnglishWord(String number) {
        if (StringUtility.isNumber(number))
            return convertNumberToEnglishWord(Long.valueOf(number));
        else return "";
    }

    private static final String[] YEKAN = {"  یک ", "  دو ", " سه ", " چهار ", " پنج ", " شش ", " هفت ", " هشت ", " نه "};

    private static final String[] NUM_NAMES = {
            "",
            " one",
            " two",
            " three",
            " four",
            " five",
            " six",
            " seven",
            " eight",
            " nine",
            " ten",
            " eleven",
            " twelve",
            " thirteen",
            " fourteen",
            " fifteen",
            " sixteen",
            " seventeen",
            " eighteen",
            " nineteen"
    };

    private static final String[] TENS_NAMES = {
            "",
            " ten",
            " twenty",
            " thirty",
            " forty",
            " fifty",
            " sixty",
            " seventy",
            " eighty",
            " ninety"
    };

    private static final String[] DAHGAN = {" بیست ", " سی ", " چهل ", " پنجاه ", " شصت ", " هفتاد ", " هشتاد ", " نود "};

    private static final String[] SADGAN = {" یکصد ", " دویست ", " سیصد ", " چهارصد ", " پانصد ", " ششصد ", " هفتصد ", " هشتصد ", "نهصد "};

    private static final String[] DAH = {"  ده ", " یازده ", " دوازده ", " سیزده ", " چهارده ", " پانزده ", " شانزده ", " هفده ", " هیجده ", " نوزده "};

    private static String doNum(BigDecimal num, int level) {
        if (num == null)
            return "";
        if (num.compareTo(new BigDecimal(0)) < 0) {
            num = num.negate();
            return "منفی" + doNum(num, level);
        }
        if (num.compareTo(new BigDecimal(0)) == 0) {
            if (level == 0)
                return "صفر";
            return "";
        }
        String result = "";
        if (level > 0) {
            result = result + " و ";
            level--;
        }
        if (num.compareTo(new BigDecimal(10)) < 0) {
            result = result + YEKAN[num.add((new BigDecimal(1)).negate()).intValue()];
        } else if (num.compareTo(new BigDecimal(20)) < 0) {
            result = result + DAH[num.add((new BigDecimal(10)).negate()).intValue()];
        } else if (num.compareTo(new BigDecimal(100)) < 0) {
            result = result + DAHGAN[num.divide(new BigDecimal(10), 0, RoundingMode.FLOOR).add((new BigDecimal(2)).negate()).intValue()] + doNum(num.remainder(new BigDecimal(10)), level + 1);
        } else if (num.compareTo(new BigDecimal(1000)) < 0) {
            result = result + SADGAN[num.divide(new BigDecimal(100), 0, RoundingMode.FLOOR).add((new BigDecimal(1)).negate()).intValue()] + doNum(num.remainder(new BigDecimal(100)), level + 1);
        } else if (num.compareTo(new BigDecimal(1000000)) < 0) {
            result = result + doNum(num.divide(new BigDecimal(1000), 0, RoundingMode.FLOOR), level) + " هزار " + doNum(num.remainder(new BigDecimal(1000)), level + 1);
        } else if (num.compareTo(new BigDecimal(1000000000)) < 0) {
            result = result + doNum(num.divide(new BigDecimal(1000000), 0, RoundingMode.FLOOR), level) + " میلیون " + doNum(num.remainder(new BigDecimal(1000000)), level + 1);
        } else if (num.compareTo(new BigDecimal(Long.valueOf("1000000000000").longValue())) < 0) {
            result = result + doNum(num.divide(new BigDecimal(Long.parseLong("1000000000")), 0, RoundingMode.FLOOR), level) + " میلیارد " + doNum(num.remainder(new BigDecimal(Long.parseLong("1000000000"))), level + 1);
        } else if (num.compareTo(new BigDecimal(Long.valueOf("1000000000000000").longValue())) < 0) {
            result = result + doNum(num.divide(new BigDecimal(Long.parseLong("1000000000000")), 0, RoundingMode.FLOOR), level) + " تیلیارد " + doNum(num.remainder(new BigDecimal(Long.parseLong("1000000000000"))), level + 1);
        }
        return result;
    }

    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20) {
            soFar = NUM_NAMES[number % 100];
            number /= 100;
        } else {
            soFar = NUM_NAMES[number % 10];
            number /= 10;

            soFar = TENS_NAMES[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) return soFar;
        return NUM_NAMES[number] + " hundred" + soFar;
    }

    private static String convertNumberToEnglishWord(long number) {

        // 0 to 999 999 999 999
        if (number == 0) {
            return "zero";
        }

        String stringNumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        stringNumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(stringNumber.substring(0, 3));
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(stringNumber.substring(3, 6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(stringNumber.substring(6, 9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(stringNumber.substring(9, 12));

        String tradBillions = "";
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            default:
                tradBillions = convertLessThanOneThousand(billions) + " billion ";
        }
        String result = tradBillions;

        String tradMillions = "";
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            default:
                tradMillions = convertLessThanOneThousand(millions) + " million ";
        }
        result = result + tradMillions;

        String tradHundredThousands = "";
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1:
                tradHundredThousands = "one thousand ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
        }

        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = result + tradThousand;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

}
