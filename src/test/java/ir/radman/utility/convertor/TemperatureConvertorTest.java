package ir.radman.utility.convertor;

import ir.radman.common.util.convertor.TemperatureConvertor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TemperatureConvertorTest {

    @Test
    void getCelsius_convertToFahrenheit_AsString(){

        String number = "25.5";
        String expectedResult = "77.90000";
        String result = TemperatureConvertor.convertCelsiusToFahrenheit(number);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getCelsius_convertToFahrenheit_AsDouble(){

        Double number = 25.5d;
        Double expectedResult = 77.9d;
        Double result = TemperatureConvertor.convertCelsiusToFahrenheit(number);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getFahrenheit_convertToCelsius_AsString(){

        String number = "70.5";
        String expectedResult = "21.38889";
        String result = TemperatureConvertor.convertFahrenheitToCelsius(number);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getFahrenheit_convertToCelsius_AsDouble(){

        Double number = 70.5d;
        Double expectedResult = 21.38888888888889d;
        Double result = TemperatureConvertor.convertFahrenheitToCelsius(number);

        Assertions.assertEquals(expectedResult, result);
    }
}
