package ir.baarmaan.utility.convertor;

import ir.baarmaan.general.exception.unchecked.InvalidTemperatureException;
import ir.baarmaan.utility.primitive.StringUtility;

public class TemperatureConvertor {

    private TemperatureConvertor() {
    }

    public static double convertFahrenheitToCelsius(double FahrenheitTemp) {
        return (FahrenheitTemp - 32) * 5 / 9;
    }

    public static String convertFahrenheitToCelsius(String FahrenheitTemp) {
        if (!StringUtility.isNumber(FahrenheitTemp))
            throw new InvalidTemperatureException("Temperature is invalid");

        return String.format("%.5f", convertFahrenheitToCelsius(Double.valueOf(FahrenheitTemp)));
    }

    public static double convertCelsiusToFahrenheit(double celsiusTemp) {
        return (celsiusTemp * 9 / 5) + 32;
    }

    public static String convertCelsiusToFahrenheit(String celsiusTemp) {
        if (!StringUtility.isNumber(celsiusTemp))
            throw new InvalidTemperatureException("Temperature is invalid");
        return String.format("%.5f", convertCelsiusToFahrenheit(Double.valueOf(celsiusTemp)));
    }

    public static double convertKelvinToFahrenheit(double kelvinTemp) {
        return (kelvinTemp - 273.15) * 9 / 5 + 32;
    }

    public static String convertKelvinToFahrenheit(String kelvinTemp) {
        if (!StringUtility.isNumber(kelvinTemp))
            throw new InvalidTemperatureException("Temperature is invalid");
        return String.format("%.5f", convertKelvinToFahrenheit(Double.valueOf(kelvinTemp)));
    }

    public static double convertFahrenheitToKelvin(double FahrenheitTemp) {
        return (FahrenheitTemp - 32) * 5 / 9 + 273.15;
    }

    public static String convertFahrenheitToKelvin(String FahrenheitTemp) {
        if (!StringUtility.isNumber(FahrenheitTemp))
            throw new InvalidTemperatureException("Temperature is invalid");
        return String.format("%.5f", convertFahrenheitToKelvin(Double.valueOf(FahrenheitTemp)));
    }

    public static double convertKelvinToCelsius(double KelvinTemp) {
        return KelvinTemp + 273.15;
    }

    public static String convertKelvinToCelsius(String KelvinTemp) {
        if (!StringUtility.isNumber(KelvinTemp))
            throw new InvalidTemperatureException("Temperature is invalid");
        return String.format("%.5f", convertKelvinToCelsius(Double.valueOf(KelvinTemp)));
    }

    public static double convertCelsiusToKelvin(double celsiusTemp) {
        return celsiusTemp - 273.15;
    }

    public static String convertCelsiusToKelvin(String celsiusTemp) {
        if (!StringUtility.isNumber(celsiusTemp))
            throw new InvalidTemperatureException("Temperature is invalid");
        return String.valueOf(convertCelsiusToKelvin(Double.valueOf(celsiusTemp)));
    }

}
