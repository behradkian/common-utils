package ir.radman.common.util.validation;

import ir.radman.common.util.basic.string.StringUtility;

/**
 * @author : Pedram Behradkian
 * @date : 2025/10/31
 */
public final class EmailValidator {

    private EmailValidator() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static boolean isEmailAddressValid(String emailAddress) {
        if (StringUtility.isBlank(emailAddress)) {
            return false;
        }
        return emailAddress.matches("^(?=.{1,64}@)[A-Za-z0-9._%+-]+(\\.[A-Za-z0-9._%+-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }

}