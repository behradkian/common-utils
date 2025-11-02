package ir.radman.common.util.convertor;

import ir.radman.common.util.convertor.RomanNumberConvertor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RomanNumberConvertorTest {

    @Test
    public void convertRomanToNumberTest(){

        String roman = "XLII";

        int result = RomanNumberConvertor.convertRomanToNumber(roman);

        Assertions.assertEquals(42,result);
    }

    @Test
    public void convertNumberToRomanTest(){

        int number = 194;

        String result = RomanNumberConvertor.convertNumberToRoman(number);

        Assertions.assertEquals("CXCIV", result);
    }
}
