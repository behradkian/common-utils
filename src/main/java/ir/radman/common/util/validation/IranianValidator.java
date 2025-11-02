package ir.radman.common.util.validation;

import ir.radman.common.util.string.StringUtility;

/**
 * @author : Pedram Behradkian
 * @date : 2025/10/31
 */
public final class IranianValidator {

    private IranianValidator() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static boolean isMobileNumberValid(String mobileNumber) {
        if (StringUtility.isBlank(mobileNumber)){
            return false;
        }
        return mobileNumber.matches("^(09\\d{9}|\\+989\\d{9})$");
    }

    public static boolean isPostalCodeValid(String postalCode) {
        if (StringUtility.isBlank(postalCode)){
            return false;
        }
        return postalCode.matches("^[13-9]{4}[1346-9][1-9](([0-9]{3}[1-9])|([1-9][0-9]{3})|([0-9][1-9][0-9]{2})|([0-9]{2}[1-9][0-9]))$");
    }

    public static boolean isNationalCodeValid(String nationalCode) {
        if (StringUtility.isBlank(nationalCode) || !nationalCode.matches("\\d{10}") || nationalCode.chars().distinct().count() == 1) {
            return false;
        }
        char[] nationalCodeCharsArray = nationalCode.toCharArray();
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Integer.parseInt(String.valueOf(nationalCodeCharsArray[i]));
            sum = sum + digit * (10 - i);
        }
        int checkDigit = Integer.parseInt(String.valueOf(nationalCodeCharsArray[9]));
        int mod = sum % 11;

        if (mod > 1 && (11 - mod) == checkDigit)
            return true;
        else return mod < 2 && mod == checkDigit;
    }

    public static boolean isCorporateCodeValid(String corporateNationalCode) {
        if (StringUtility.isBlank(corporateNationalCode) || !corporateNationalCode.matches("\\d{11}")) {
            return false;
        }
        int[] coefficients = {29, 27, 23, 19, 17, 29, 27, 23, 19, 17};
        int baseDigit = corporateNationalCode.charAt(9) - '0';
        int checkDigit = corporateNationalCode.charAt(10) - '0';
        int sum = 0;

        for (int i = 0; i < 10; i++) {
            int digit = corporateNationalCode.charAt(i) - '0';
            sum += coefficients[i] * (digit + baseDigit + 2);
        }
        int calculated = sum % 11;
        if (calculated == 10) {
            calculated = 0;
        }
        return calculated == checkDigit;
    }

}