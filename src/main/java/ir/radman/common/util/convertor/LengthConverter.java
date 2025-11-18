package ir.radman.common.util.convertor;

import ir.radman.common.general.enumeration.LengthUnit;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class LengthConverter {

    private static final MathContext DEFAULT_MATH_CONTEXT = MathContext.DECIMAL64;
    private static final Map<LengthUnit, Map<LengthUnit, BigDecimal>> CONVERSION_CACHE = new ConcurrentHashMap<>();

    private LengthConverter() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // Core conversion methods
    public static BigDecimal convert(BigDecimal value, LengthUnit from, LengthUnit to) {
        return convert(value, from, to, DEFAULT_MATH_CONTEXT);
    }

    public static BigDecimal convert(BigDecimal value, LengthUnit from, LengthUnit to, MathContext mathContext) {
        Objects.requireNonNull(value, "Value cannot be null");
        Objects.requireNonNull(from, "From unit cannot be null");
        Objects.requireNonNull(to, "To unit cannot be null");

        if (from == to) return value;

        BigDecimal conversionFactor = getConversionFactor(from, to, mathContext);
        return value.multiply(conversionFactor, mathContext);
    }

    public static double convert(double value, LengthUnit from, LengthUnit to) {
        return convert(BigDecimal.valueOf(value), from, to).doubleValue();
    }

    public static BigDecimal convert(BigDecimal value, String from, String to) {
        return convert(value, LengthUnit.from(from), LengthUnit.from(to));
    }

    public static double convert(double value, String from, String to) {
        return convert(BigDecimal.valueOf(value), from, to).doubleValue();
    }

    // Batch conversion methods
    public static List<BigDecimal> convertBatch(List<BigDecimal> values, LengthUnit from, LengthUnit to) {
        return convertBatch(values, from, to, DEFAULT_MATH_CONTEXT);
    }

    public static List<BigDecimal> convertBatch(List<BigDecimal> values, LengthUnit from, LengthUnit to, MathContext mathContext) {
        Objects.requireNonNull(values, "Values list cannot be null");

        if (from == to) return new ArrayList<>(values);

        BigDecimal conversionFactor = getConversionFactor(from, to, mathContext);
        return values.stream()
                .map(value -> value.multiply(conversionFactor, mathContext))
                .collect(Collectors.toList());
    }

    public static List<BigDecimal> convertBatchParallel(List<BigDecimal> values, LengthUnit from, LengthUnit to) {
        return convertBatchParallel(values, from, to, DEFAULT_MATH_CONTEXT);
    }

    public static List<BigDecimal> convertBatchParallel(List<BigDecimal> values, LengthUnit from, LengthUnit to, MathContext mathContext) {
        Objects.requireNonNull(values, "Values list cannot be null");

        if (from == to) return new ArrayList<>(values);

        BigDecimal conversionFactor = getConversionFactor(from, to, mathContext);
        return values.parallelStream()
                .map(value -> value.multiply(conversionFactor, mathContext))
                .collect(Collectors.toList());
    }

    public static double[] convertBatch(double[] values, LengthUnit from, LengthUnit to) {
        Objects.requireNonNull(values, "Values array cannot be null");

        if (from == to) return Arrays.copyOf(values, values.length);

        double conversionFactor = getConversionFactor(from, to, DEFAULT_MATH_CONTEXT).doubleValue();
        double[] result = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] * conversionFactor;
        }
        return result;
    }

    // High-performance methods for common conversions
    public static BigDecimal metersToCentimeters(BigDecimal meters) {
        return meters.movePointRight(2);
    }

    public static BigDecimal centimetersToMeters(BigDecimal centimeters) {
        return centimeters.movePointLeft(2);
    }

    public static BigDecimal kilometersToMeters(BigDecimal kilometers) {
        return kilometers.movePointRight(3);
    }

    public static BigDecimal metersToKilometers(BigDecimal meters) {
        return meters.movePointLeft(3);
    }

    public static BigDecimal inchesToCentimeters(BigDecimal inches) {
        return inches.multiply(new BigDecimal("2.54"));
    }

    public static BigDecimal centimetersToInches(BigDecimal centimeters) {
        return centimeters.divide(new BigDecimal("2.54"), DEFAULT_MATH_CONTEXT);
    }

    // Utility methods
    public static BigDecimal getConversionFactor(LengthUnit from, LengthUnit to) {
        return getConversionFactor(from, to, DEFAULT_MATH_CONTEXT);
    }

    private static BigDecimal getConversionFactor(LengthUnit from, LengthUnit to, MathContext mathContext) {
        return CONVERSION_CACHE
                .computeIfAbsent(from, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(to, k -> from.getToMetersFactor().multiply(to.getFromMetersFactor(), mathContext));
    }

    public static void clearCache() {
        CONVERSION_CACHE.clear();
    }

    public static int getCacheSize() {
        return CONVERSION_CACHE.values().stream().mapToInt(Map::size).sum();
    }

    // Validation methods
    public static void validatePositive(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Length value cannot be negative: " + value);
        }
    }

    public static void validateFinite(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Length value must be finite: " + value);
        }
    }

    // Comparison methods
    public static boolean areEqual(BigDecimal value1, LengthUnit unit1, BigDecimal value2, LengthUnit unit2) {
        BigDecimal converted1 = convert(value1, unit1, LengthUnit.METER);
        BigDecimal converted2 = convert(value2, unit2, LengthUnit.METER);
        return converted1.compareTo(converted2) == 0;
    }

    public static int compare(BigDecimal value1, LengthUnit unit1, BigDecimal value2, LengthUnit unit2) {
        BigDecimal converted1 = convert(value1, unit1, LengthUnit.METER);
        BigDecimal converted2 = convert(value2, unit2, LengthUnit.METER);
        return converted1.compareTo(converted2);
    }

    // Functional style conversion
    public static Function<BigDecimal, BigDecimal> createConverter(LengthUnit from, LengthUnit to) {
        return createConverter(from, to, DEFAULT_MATH_CONTEXT);
    }

    public static Function<BigDecimal, BigDecimal> createConverter(LengthUnit from, LengthUnit to, MathContext mathContext) {
        if (from == to) return Function.identity();

        BigDecimal conversionFactor = getConversionFactor(from, to, mathContext);
        return value -> value.multiply(conversionFactor, mathContext);
    }
}