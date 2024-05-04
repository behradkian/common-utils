package ir.baarmaan.utility.convertor;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class NumberToWordConvertorTest {

    Logger LOGGER = LoggerFactory.getLogger(NumberToWordConvertorTest.class.getName());

    @Test
    void getCorrectString_convertToPersian(){

        String number = "2";
        String expectedResult = "دو";
        String result = NumberToWordConvertor.convertNumberToPersianWord(number).trim();

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getCorrectString_convertToEnglish(){

        String number = "2";
        String expectedResult = "two";
        String result = NumberToWordConvertor.convertNumberToEnglishWord(number);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void giveWrongString_getEmptyString(){
        String number = "abc";
        String expectedResult = "";
        String result = NumberToWordConvertor.convertNumberToEnglishWord(number);

        Assertions.assertEquals(expectedResult, result);
    }


}
