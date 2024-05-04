package ir.baarmaan.utility.validation;

import ir.baarmaan.general.exception.unchecked.ValidationException;
import ir.baarmaan.utility.primitive.StringUtility;

public class LocationValidator extends Validator{

    private LocationValidator() {
    }

    /**
     * postalCode is a unique number for find address
     */
    public static boolean isIranianPostalCodeValid(String postalCode) {

        if (StringUtility.isBlank(postalCode))
            throw new ValidationException("postalCode is null");
        if (postalCode.matches("^(?!([0-9])\1{3})[13-9]{4}[1346-9][013-9]{5}$"))
            return true;
        return false;
    }

}
