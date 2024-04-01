package com.panda.common.validation;

import com.panda.common.general.exception.ValidationException;
import com.panda.common.string.StringUtils;

public class PatternValidator extends Validator {

    private PatternValidator() {
    }

    /**
     * mobile phone is a unique number foe calling a person => structure : 09XXXXXXXXX (X is different numbers)
     */
    public static boolean IsIranianMobileNumberValid(String mobileNumber) {

        if (StringUtils.isBlank(mobileNumber))
            throw new ValidationException("mobileNumber is null");
        if (mobileNumber.matches("^09[0|1|2|3][0-9]{8}$") || mobileNumber.matches("^\\+989[0|1|2|3][0-9]{8}$"))
            return true;
        return false;
    }

}
