package ir.radman.common.util.validation;

import ir.radman.common.general.exception.unchecked.ValidationException;
import ir.radman.common.util.primitive.StringUtility;

public class PatternValidator extends Validator {

    private PatternValidator() {
    }

    /**
     * mobile phone is a unique number foe calling a person => structure : 09XXXXXXXXX (X is different numbers)
     */
    public static boolean isIranianMobileNumberValid(String mobileNumber) {
        if (StringUtility.isBlank(mobileNumber))
            throw new ValidationException("mobileNumber is null");
        return mobileNumber.matches("^09[0|123][0-9]{8}$") || mobileNumber.matches("^\\+989[0|123][0-9]{8}$");
    }

}
