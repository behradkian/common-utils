package ir.radman.common.util.validation;

import java.util.regex.Pattern;

/**
 * @author : Pedram Behradkian
 * @date : 2025/10/31
 */
public abstract class Validator {

    protected static final String IRANIAN_POSTAL_CODE_ALGORITHM = "^(?!([0-9])\1{3})[13-9]{4}[1346-9][013-9]{5}$";
    protected static final String EMAIL_ADDRESS_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    protected static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(EMAIL_ADDRESS_REGEX, Pattern.CASE_INSENSITIVE);

    protected Validator() {
    }

}
