package com.panda.common.validation;

import com.panda.common.general.exception.ValidationException;
import com.panda.common.string.StringUtils;

public class LocationValidator extends Validator{

    private LocationValidator() {
    }

    /**
     * postalCode is a unique number for find address
     */
    public static boolean isIranianPostalCodeValid(String postalCode) {

        if (StringUtils.isBlank(postalCode))
            throw new ValidationException("postalCode is null");
        if (postalCode.matches("^(?!([0-9])\1{3})[13-9]{4}[1346-9][013-9]{5}$"))
            return true;
        return false;
    }

}
