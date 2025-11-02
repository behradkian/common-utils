package ir.radman.common.util.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/02
 */
public class IranianValidatorTest {

    @Test
    @DisplayName("should validate mobile numbers")
    void testMobileNumber() {
        assertTrue(IranianValidator.isMobileNumberValid("09123456789"));
        assertTrue(IranianValidator.isMobileNumberValid("+989123456789"));
        assertFalse(IranianValidator.isMobileNumberValid("08123456789"));
        assertFalse(IranianValidator.isMobileNumberValid(null));
    }

    @Test
    @DisplayName("should validate postal codes")
    void testPostalCode() {
        assertTrue(IranianValidator.isPostalCodeValid("1515673414"));
        assertFalse(IranianValidator.isPostalCodeValid("0000000000"));
        assertFalse(IranianValidator.isPostalCodeValid(null));
    }

    @Test
    @DisplayName("should validate national codes")
    void testNationalCode() {
        assertTrue(IranianValidator.isNationalCodeValid("0084575948"));
        assertFalse(IranianValidator.isNationalCodeValid("1111111111"));
        assertFalse(IranianValidator.isNationalCodeValid("123"));
        assertFalse(IranianValidator.isNationalCodeValid(null));
    }

    @Test
    @DisplayName("should validate corporate national codes")
    void testCorporateCode() {
        assertTrue(IranianValidator.isCorporateCodeValid("10380284790"));
        assertFalse(IranianValidator.isCorporateCodeValid("10380284791"));
        assertFalse(IranianValidator.isCorporateCodeValid("123"));
        assertFalse(IranianValidator.isCorporateCodeValid(null));
    }
}