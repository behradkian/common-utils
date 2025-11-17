package ir.radman.common.util.convertor;

import ir.radman.common.general.enumeration.date.TimeUnit;

public class TimeConvertor {

    private TimeConvertor() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static double convertTime(TimeUnit fromUnit, TimeUnit toUnit, double value) {
        double valueInMilliseconds = convertToMilliseconds(fromUnit, value);
        return convertFromMilliseconds(toUnit, valueInMilliseconds);
    }

    private static double convertToMilliseconds(TimeUnit unit, double value) {
        return switch (unit) {
            case WEEK -> value * 7 * 24 * 3600 * 1000;
            case DAY -> value * 24 * 3600 * 1000;
            case HOUR -> value * 3600 * 1000;
            case MINUTE -> value * 60 * 1000;
            case SECOND -> value * 1000;
            case MILLI_SECOND -> value;
        };
    }

    private static double convertFromMilliseconds(TimeUnit unit, double valueInMilliseconds) {
        return switch (unit) {
            case WEEK -> valueInMilliseconds / (7 * 24 * 3600 * 1000);
            case DAY -> valueInMilliseconds / (24 * 3600 * 1000);
            case HOUR -> valueInMilliseconds / (3600 * 1000);
            case MINUTE -> valueInMilliseconds / (60 * 1000);
            case SECOND -> valueInMilliseconds / 1000;
            case MILLI_SECOND -> valueInMilliseconds;
        };
    }

}
