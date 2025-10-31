package ir.radman.common.util.convertor;

import ir.radman.common.general.enumeration.TimeUnit;

public class TimeConvertor {

    private TimeConvertor() {}

    public static double convertTime(TimeUnit fromUnit, TimeUnit toUnit, double value) {
        double valueInMilliseconds = convertToMilliseconds(fromUnit, value);
        return convertFromMilliseconds(toUnit, valueInMilliseconds);
    }

    private static double convertToMilliseconds(TimeUnit unit, double value) {
        switch (unit) {
            case WEEK:
                return value * 7 * 24 * 3600 * 1000;
            case DAY:
                return value * 24 * 3600 * 1000;
            case HOUR:
                return value * 3600 * 1000;
            case MINUTE:
                return value * 60 * 1000;
            case SECOND:
                return value * 1000;
            case MILLI_SECOND:
                return value;
            default:
                throw new IllegalArgumentException("Invalid from unit: " + unit);
        }
    }

    private static double convertFromMilliseconds(TimeUnit unit, double valueInMilliseconds) {
        switch (unit) {
            case WEEK:
                return valueInMilliseconds / (7 * 24 * 3600 * 1000);
            case DAY:
                return valueInMilliseconds / (24 * 3600 * 1000);
            case HOUR:
                return valueInMilliseconds / (3600 * 1000);
            case MINUTE:
                return valueInMilliseconds / (60 * 1000);
            case SECOND:
                return valueInMilliseconds / 1000;
            case MILLI_SECOND:
                return valueInMilliseconds;
            default:
                throw new IllegalArgumentException("Invalid to unit: " + unit);
        }
    }

}
