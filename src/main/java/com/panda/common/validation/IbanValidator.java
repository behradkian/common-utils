package com.panda.common.validation;

import java.math.BigDecimal;

public class IbanValidator extends Validator {

    private static final String IRAN_COUNTRY_CODE = "IR";

    private IbanValidator() {
    }

    public static boolean isIranianIbanValid(String iban) {
        iban = iban.replace(" ", "");
        iban = iban.replace(IRAN_COUNTRY_CODE.toLowerCase(), "");
        iban = iban.replace(IRAN_COUNTRY_CODE.toUpperCase(), "");
        String bankCode = iban.substring(0, 2);
        String depositCode = iban.substring(2, 24);
        String validateIban = depositCode + "1827" + bankCode;
        BigDecimal mod = new BigDecimal(validateIban).remainder(new BigDecimal("97"));
        return mod.equals(new BigDecimal("1")) ? true : false;
    }

}
