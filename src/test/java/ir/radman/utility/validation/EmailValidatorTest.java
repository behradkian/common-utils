package ir.radman.utility.validation;

import ir.radman.common.util.validation.EmailValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailValidatorTest {

    @Test
    void giveCorrectEmail_getValidResponse() {
        String email = "behradkian@outlook.com";
        boolean isValid = EmailValidator.isEmailAddressValid(email);
        Assertions.assertTrue(isValid);
    }
}
