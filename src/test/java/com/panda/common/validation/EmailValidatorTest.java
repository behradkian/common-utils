package com.panda.common.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EmailValidatorTest {

    Logger LOGGER = LoggerFactory.getLogger(EmailValidatorTest.class.getName());

    @Test
    void giveCorrectEmail_getValidResponse(){

        String email = "behradkian@outlook.com";
        boolean isValid = EmailValidator.isEmailAddressValid(email);
        Assertions.assertTrue(isValid);

    }
}
