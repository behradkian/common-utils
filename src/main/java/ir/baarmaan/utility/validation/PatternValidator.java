package ir.baarmaan.utility.validation;

import ir.baarmaan.general.exception.unchecked.ValidationException;
import ir.baarmaan.utility.primitive.StringUtility;

public class PatternValidator extends Validator {

    private PatternValidator() {
    }

    /**
     * mobile phone is a unique number foe calling a person => structure : 09XXXXXXXXX (X is different numbers)
     */
    public static boolean IsIranianMobileNumberValid(String mobileNumber) {

        if (StringUtility.isBlank(mobileNumber))
            throw new ValidationException("mobileNumber is null");
        if (mobileNumber.matches("^09[0|1|2|3][0-9]{8}$") || mobileNumber.matches("^\\+989[0|1|2|3][0-9]{8}$"))
            return true;
        return false;
    }

}
