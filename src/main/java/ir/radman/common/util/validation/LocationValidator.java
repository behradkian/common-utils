package ir.radman.common.util.validation;

import ir.radman.common.general.exception.unchecked.ValidationException;
import ir.radman.common.util.primitive.StringUtility;

public class LocationValidator extends Validator {

    public static final String IRANIAN_POSTAL_CODE_ALGORITHM = "^(?!([0-9])\1{3})[13-9]{4}[1346-9][013-9]{5}$";

    private LocationValidator() {
    }

    /**
     * postalCode is a unique number for find address
     */
    public static boolean isIranianPostalCodeValid(String postalCode) {

        if (StringUtility.isBlank(postalCode))
            throw new ValidationException("postalCode is null");
        return postalCode.matches(IRANIAN_POSTAL_CODE_ALGORITHM);
    }

}