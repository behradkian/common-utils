package ir.radman.common.util.validation;

import ir.radman.common.general.exception.unchecked.ValidationException;
import ir.radman.common.util.primitive.StringUtility;

/**
 * @author : Pedram Behradkian
 * @date : 2025/10/31
 */
public class IranianValidator extends Validator {


    /**
     * mobile phone is a unique number foe calling a person => structure : 09XXXXXXXXX (X is different numbers)
     */
    public static boolean isIranianMobileNumberValid(String mobileNumber) {
        if (StringUtility.isBlank(mobileNumber))
            throw new ValidationException("mobileNumber is null");
        return mobileNumber.matches("^09[0|123][0-9]{8}$") || mobileNumber.matches("^\\+989[0|123][0-9]{8}$");
    }

    /**
     * postalCode is a unique number for find address
     */
    public static boolean isIranianPostalCodeValid(String postalCode) {

        if (StringUtility.isBlank(postalCode))
            throw new ValidationException("postalCode is null");
        return postalCode.matches(IRANIAN_POSTAL_CODE_REGEX);
    }

    /**
     * nationalCode is public number for identification of iranian citizens (is on the MelliCards)
     */
    public static boolean isIranianNationalCodeValid(String nationalCode) {

        if (StringUtility.isBlank(nationalCode))
            throw new ValidationException("nationalCode is null");

        if (!nationalCode.matches("^[0-9]{10}$"))
            return false;

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
        else if (mod < 2 && mod == checkDigit)
            return true;
        else
            return false;
    }

    /**
     * nationalSerial is a collections of numbers and a letter in back of MelliCard for some inquiries (like civil registration with photo)
     */
    public static boolean isIranianNationalSerialValid(String nationalSerial) {

        if (StringUtility.isBlank(nationalSerial))
            throw new ValidationException("nationalSerial is null");
        return nationalSerial.matches("^[0-9]{9}$") || nationalSerial.matches("^[0-9]{1}[A-Z]{1}[0-9]{8}$");
    }

}
