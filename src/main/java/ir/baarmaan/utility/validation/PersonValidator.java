package ir.baarmaan.utility.validation;

import ir.baarmaan.general.exception.unchecked.ValidationException;
import ir.baarmaan.utility.primitive.StringUtility;

public class PersonValidator extends Validator {

    private PersonValidator() {
    }

    /**
     * nationalCode is public number for identification of iranian citizens (is on the MelliCards)
     */
    public static boolean IsIranianNationalCodeValid(String nationalCode) {

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
    public static boolean IsIranianNationalSerialValid(String nationalSerial) {

        if (StringUtility.isBlank(nationalSerial))
            throw new ValidationException("nationalSerial is null");
        if (nationalSerial.matches("^[0-9]{9}$") || nationalSerial.matches("^[0-9]{1}[A-Z]{1}[0-9]{8}$"))
            return true;
        return false;
    }


}
