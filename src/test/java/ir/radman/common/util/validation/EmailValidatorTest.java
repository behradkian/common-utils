package ir.radman.common.util.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/02
 */
class EmailValidatorTest {

    @Test
    @DisplayName("should validate correct email formats")
    void testValidEmails() {
        assertTrue(EmailValidator.isEmailAddressValid("test@example.com"));
        assertTrue(EmailValidator.isEmailAddressValid("user.name+alias@domain.co.uk"));
    }

    @Test
    @DisplayName("should reject invalid email formats")
    void testInvalidEmails() {
        assertFalse(EmailValidator.isEmailAddressValid(null));
        assertFalse(EmailValidator.isEmailAddressValid(""));
        assertFalse(EmailValidator.isEmailAddressValid("invalid@"));
        assertFalse(EmailValidator.isEmailAddressValid("@domain.com"));
        assertFalse(EmailValidator.isEmailAddressValid("test@-domain.com"));
    }
}
