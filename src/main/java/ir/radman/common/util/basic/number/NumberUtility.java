package ir.radman.common.util.basic.number;

import ir.radman.common.util.basic.string.StringUtility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

/**
 * Utility class for performing various number-related operations
 * such as parsing, formatting, range checking, random generation,
 * and mathematical validations.
 *
 * @author : Pedram Behradkian
 * @date : 2025/11/02
 */
public final class NumberUtility {

    private NumberUtility() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static boolean isValidNumber(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return value.matches("[-+]?\\d*(\\.\\d+)?");
    }

    /**
     * Checks if a string is a valid integer.
     *
     * @param value the input string
     * @return true if it represents a valid integer
     */
    public static boolean isInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        return value.matches("[-+]?\\d+");
    }

    /**
     * Parses a string to integer safely.
     * Returns defaultValue if parsing fails.
     *
     * @param value        the input string
     * @param defaultValue the default value to return if parsing fails
     * @return parsed integer or defaultValue
     */
    public static int safeParseInt(String value, int defaultValue) {
        if (!isInteger(value)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parses a string to double safely.
     * Returns defaultValue if parsing fails.
     *
     * @param value        the input string
     * @param defaultValue the default value to return if parsing fails
     * @return parsed double or defaultValue
     */
    public static double safeParseDouble(String value, double defaultValue) {
        if (!StringUtility.isNumber(value)) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Checks if a number is within a range [min, max].
     *
     * @param value the value to check
     * @param min   minimum value
     * @param max   maximum value
     * @return true if within range
     */
    public static boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    /**
     * Calculates average of a list of numbers.
     *
     * @param numbers list of Double values
     * @return average or 0 if list is empty or null
     */
    public static double average(List<Double> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }
        double sum = 0;
        int count = 0;
        for (Double num : numbers) {
            if (num != null) {
                sum += num;
                count++;
            }
        }
        return count == 0 ? 0 : sum / count;
    }

    /**
     * Returns maximum value from a list of numbers.
     *
     * @param numbers list of Double values
     * @return maximum or null if list is empty
     */
    public static Double max(List<Double> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return null;
        }
        Double max = null;
        for (Double num : numbers) {
            if (num != null && (max == null || num > max)) {
                max = num;
            }
        }
        return max;
    }

    /**
     * Returns minimum value from a list of numbers.
     *
     * @param numbers list of Double values
     * @return minimum or null if list is empty
     */
    public static Double min(List<Double> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return null;
        }
        Double min = null;
        for (Double num : numbers) {
            if (num != null && (min == null || num < min)) {
                min = num;
            }
        }
        return min;
    }

    /**
     * Rounds a number to the given number of decimal places.
     *
     * @param value the number to round
     * @param scale number of decimal places
     * @return rounded number
     */
    public static double round(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("Scale must be non-negative");
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Formats a number with a thousand separators.
     *
     * @param value the number to format
     * @return formatted string
     */
    public static String formatWithComma(Double value) {
        if (value == null) {
            return StringUtility.EMPTY;
        }
        DecimalFormat df = new DecimalFormat("#,###.##");
        return df.format(value);
    }

    /**
     * Checks if a number is even.
     *
     * @param number input integer
     * @return true if even
     */
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    /**
     * Checks if a number is odd.
     *
     * @param number input integer
     * @return true if odd
     */
    public static boolean isOdd(int number) {
        return number % 2 != 0;
    }

    /**
     * Checks if a number is positive.
     *
     * @param number input double
     * @return true if number > 0
     */
    public static boolean isPositive(double number) {
        return number > 0;
    }

    /**
     * Checks if a number is negative.
     *
     * @param number input double
     * @return true if number < 0
     */
    public static boolean isNegative(double number) {
        return number < 0;
    }

    /**
     * Generates a random integer between min and max (inclusive).
     *
     * @param min minimum value
     * @param max maximum value
     * @return random integer
     */
    public static int randomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }
        return new Random().nextInt((max - min) + 1) + min;
    }

    /**
     * Checks if a number is prime.
     *
     * @param number input integer
     * @return true if prime
     */
    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Safely compares two numbers (handles nulls).
     *
     * @param a first number
     * @param b second number
     * @return true if equal or both null
     */
    public static boolean safeEquals(Number a, Number b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return Double.compare(a.doubleValue(), b.doubleValue()) == 0;
    }

    /**
     * Clamps a number to a given range [min, max].
     *
     * @param value input number
     * @param min   minimum allowed value
     * @param max   maximum allowed value
     * @return clamped number
     */
    public static double clamp(double value, double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }
        if (value < min) {
            return min;
        }
        return Math.min(value, max);
    }

    /**
     * Calculates the sum of a list of numbers.
     *
     * @param numbers list of Double values
     * @return sum or 0 if list is null or empty
     */
    public static double sum(List<Double> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }
        double total = 0;
        for (Double num : numbers) {
            if (num != null) {
                total += num;
            }
        }
        return total;
    }

    /**
     * Calculates percentage (part / total * 100) with specified scale.
     *
     * @param part  part value
     * @param total total value
     * @param scale number of decimal places
     * @return percentage value or 0 if total is 0
     */
    public static double percentage(double part, double total, int scale) {
        if (total == 0) {
            return 0;
        }
        double result = (part / total) * 100;
        return round(result, scale);
    }

    /**
     * Calculates factorial of a number.
     *
     * @param n non-negative integer
     * @return factorial value
     */
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    /**
     * Checks if a number is between two bounds.
     *
     * @param value     the number to check
     * @param start     lower bound
     * @param end       upper bound
     * @param inclusive if true, boundaries are inclusive
     * @return true if within range
     */
    public static boolean isBetween(Number value, Number start, Number end, boolean inclusive) {
        if (value == null || start == null || end == null) {
            return false;
        }
        double v = value.doubleValue();
        double s = start.doubleValue();
        double e = end.doubleValue();
        if (inclusive) {
            return v >= s && v <= e;
        } else {
            return v > s && v < e;
        }
    }

    /**
     * Generates a random double between min and max.
     *
     * @param min minimum value
     * @param max maximum value
     * @return random double
     */
    public static double randomDouble(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }
        return min + (max - min) * new Random().nextDouble();
    }

    /**
     * Safely parses a string to BigDecimal.
     *
     * @param value        input string
     * @param defaultValue value to return if parsing fails
     * @return parsed BigDecimal or defaultValue
     */
    public static BigDecimal safeParseBigDecimal(String value, BigDecimal defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}