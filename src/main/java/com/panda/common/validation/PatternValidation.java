package com.panda.common.validation;

import com.panda.common.general.exception.ValidationException;
import com.panda.common.string.StringUtils;

public class PatternValidation {


    private PatternValidation() {
    }

    /**
     * nationalCode is public number for identification of iranian citizens (is on the MelliCards)
     */
    public static Boolean IsNationalCodeValid(String nationalCode) {

        if (StringUtils.isBlank(nationalCode))
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
    public static Boolean IsMelliCardSerialValid(String nationalSerial) {

        if (StringUtils.isBlank(nationalSerial))
            throw new ValidationException("nationalSerial is null");
        if (nationalSerial.matches("^[0-9]{9}$") || nationalSerial.matches("^[0-9]{1}[A-Z]{1}[0-9]{8}$"))
            return true;
        return false;
    }

    /**
     * mobile phone is a unique number foe calling a person => structure : 09XXXXXXXXX (X is different numbers)
     */
    public static Boolean IsMobileNumberValid(String mobileNumber) {

        if (StringUtils.isBlank(mobileNumber))
            throw new ValidationException("mobileNumber is null");
        if (mobileNumber.matches("^09[0-9]{9}$") || mobileNumber.matches("^\\+989[0-9]{9}$"))
            return true;
        return false;
    }

    /**
     * postalCode is a unique number for find address
     */
    public static boolean isPostalCodeValid(String postalCode) {

        if (StringUtils.isBlank(postalCode))
            throw new ValidationException("postalCode is null");
        if (postalCode.matches("^(?!([0-9])\1{3})[13-9]{4}[1346-9][013-9]{5}$"))
            return true;
        return false;
    }

}
