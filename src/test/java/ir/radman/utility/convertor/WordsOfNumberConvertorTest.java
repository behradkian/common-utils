package ir.radman.utility.convertor;

import ir.radman.common.util.convertor.WordsOfNumberConvertor;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

class WordsOfNumberConvertorTest {

    @Test
    void getCorrectString_convertToPersian(){

        String number = "2";
        String expectedResult = "دو";
        String result = WordsOfNumberConvertor.convertNumberToPersianWord(number);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getCorrectString_convertToEnglish(){

        String number = "2";
        String expectedResult = "two";
        String result = WordsOfNumberConvertor.convertNumberToEnglishWord(number);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getCorrectNumber_convertToPersian(){

        BigDecimal number = new BigDecimal(2);
        String expectedResult = "دو";
        String result = WordsOfNumberConvertor.convertNumberToPersianWord(number);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getCorrectNumber_convertToEnglish(){

        BigDecimal number = new BigDecimal(2);
        String expectedResult = "two";
        String result = WordsOfNumberConvertor.convertNumberToEnglishWord(number);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void giveWrongString_getEmptyString(){
        String number = "abc";
        String expectedResult = "";
        String result = WordsOfNumberConvertor.convertNumberToEnglishWord(number);

        Assertions.assertEquals(expectedResult, result);
    }


}
