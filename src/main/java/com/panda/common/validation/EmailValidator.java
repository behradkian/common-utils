package com.panda.common.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator extends Validator {

    public static final String EMAIL_ADDRESS_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(EMAIL_ADDRESS_REGEX, Pattern.CASE_INSENSITIVE);

    private EmailValidator() {
    }

    public static boolean isEmailAddressValid(String emailAddress) {
        Matcher matcher = EMAIL_ADDRESS_PATTERN.matcher(emailAddress);
        return matcher.matches();
    }

}
