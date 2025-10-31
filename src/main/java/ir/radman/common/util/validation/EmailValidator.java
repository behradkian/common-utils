package ir.radman.common.util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : Pedram Behradkian
 * @date : 2025/10/31
 */
public class EmailValidator extends Validator {

    private EmailValidator() {
    }

    public static boolean isEmailAddressValid(String emailAddress) {
        Matcher matcher = EMAIL_ADDRESS_PATTERN.matcher(emailAddress);
        return matcher.matches();
    }

}
