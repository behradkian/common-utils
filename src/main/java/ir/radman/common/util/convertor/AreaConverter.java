package ir.radman.common.util.convertor;

import ir.radman.common.general.dto.AreaValue;
import ir.radman.common.general.enumeration.AreaUnit;
import ir.radman.common.general.exception.domain.ConvertException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
public final class AreaConverter {

    private static final Map<String, Double> CONVERSION_CACHE = new HashMap<>();
    private static final int DEFAULT_SCALE = 8;

    private AreaConverter() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static BigDecimal convert(double value, AreaUnit fromUnit, AreaUnit toUnit) {
        return convert(BigDecimal.valueOf(value), fromUnit, toUnit, DEFAULT_SCALE);
    }

    public static BigDecimal convert(BigDecimal value, AreaUnit fromUnit, AreaUnit toUnit) {
        return convert(value, fromUnit, toUnit, DEFAULT_SCALE);
    }

    public static BigDecimal convert(double value, AreaUnit fromUnit, AreaUnit toUnit, int scale) {
        return convert(BigDecimal.valueOf(value), fromUnit, toUnit, scale);
    }

    public static BigDecimal convert(BigDecimal value, AreaUnit fromUnit, AreaUnit toUnit, int scale) {
        if (value == null || fromUnit == null || toUnit == null) {
            throw new ConvertException("Input parameters cannot be null");
        }

        if (fromUnit == toUnit) {
            return value.setScale(scale, RoundingMode.HALF_UP);
        }

        BigDecimal conversionFactor = getConversionFactor(fromUnit, toUnit);
        return value.multiply(conversionFactor).setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal squareMetersToHectares(BigDecimal squareMeters) {
        return convert(squareMeters, AreaUnit.SQUARE_METER, AreaUnit.HECTARE, 4);
    }

    public static BigDecimal hectaresToSquareMeters(BigDecimal hectares) {
        return convert(hectares, AreaUnit.HECTARE, AreaUnit.SQUARE_METER, 2);
    }

    public static BigDecimal squareMetersToJerib(BigDecimal squareMeters) {
        return convert(squareMeters, AreaUnit.SQUARE_METER, AreaUnit.JERIB, 4);
    }

    public static BigDecimal jeribToSquareMeters(BigDecimal jerib) {
        return convert(jerib, AreaUnit.JERIB, AreaUnit.SQUARE_METER, 2);
    }

    public static BigDecimal acresToHectares(BigDecimal acres) {
        return convert(acres, AreaUnit.ACRE, AreaUnit.HECTARE, 4);
    }

    public static BigDecimal hectaresToAcres(BigDecimal hectares) {
        return convert(hectares, AreaUnit.HECTARE, AreaUnit.ACRE, 4);
    }

    public static BigDecimal calculateRectangleArea(BigDecimal length, BigDecimal width, AreaUnit unit) {
        return length.multiply(width).setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateCircleArea(BigDecimal radius, AreaUnit unit) {
        BigDecimal radiusSquared = radius.multiply(radius);
        return radiusSquared.multiply(BigDecimal.valueOf(Math.PI)).setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateTriangleArea(BigDecimal base, BigDecimal height, AreaUnit unit) {
        return base.multiply(height).multiply(BigDecimal.valueOf(0.5)).setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    public static boolean isValidAreaValue(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) >= 0;
    }

    public static void validateAreaValue(BigDecimal value) {
        if (!isValidAreaValue(value)) {
            throw new ConvertException("Area value must be non-negative");
        }
    }

    public static String format(BigDecimal value, AreaUnit unit, int decimalPlaces) {
        String formattedValue = value.setScale(decimalPlaces, RoundingMode.HALF_UP).toPlainString();
        return String.format("%s %s", formattedValue, unit.getSymbol());
    }

    public static String formatPersian(BigDecimal value, AreaUnit unit, int decimalPlaces) {
        String formattedValue = value.setScale(decimalPlaces, RoundingMode.HALF_UP).toPlainString();
        return String.format("%s %s", formattedValue, unit.getPersianName());
    }

    public static Map<String, String> getAvailableUnits() {
        return Arrays.stream(AreaUnit.values()).collect(Collectors.toMap(
                AreaUnit::name,
                unit -> unit.getPersianName() + " (" + unit.getSymbol() + ")")
        );
    }

    public static AreaUnit findUnit(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return null;
        }
        String term = searchTerm.trim().toLowerCase();
        for (AreaUnit unit : AreaUnit.values()) {
            if (unit.name().toLowerCase().equals(term) || unit.getSymbol().toLowerCase().equals(term) || unit.getPersianName().toLowerCase().contains(term) || term.contains(unit.getSymbol().toLowerCase())) {
                return unit;
            }
        }
        return null;
    }

    private static BigDecimal getConversionFactor(AreaUnit fromUnit, AreaUnit toUnit) {
        String cacheKey = fromUnit.name() + "_TO_" + toUnit.name();
        return BigDecimal.valueOf(CONVERSION_CACHE.computeIfAbsent(cacheKey, k -> fromUnit.getSquareMeters() / toUnit.getSquareMeters()));
    }

    public static BigDecimal getConversionRate(AreaUnit fromUnit, AreaUnit toUnit) {
        return getConversionFactor(fromUnit, toUnit).setScale(6, RoundingMode.HALF_UP);
    }

    public static AreaValue parse(String areaString) {
        if (areaString == null || areaString.trim().isEmpty()) {
            throw new ConvertException("Area string cannot be null or empty");
        }
        String[] parts = areaString.trim().split("\\s+");
        if (parts.length < 2) {
            throw new ConvertException("Invalid area format: " + areaString);
        }
        try {
            BigDecimal value = new BigDecimal(parts[0]);
            String unitString = parts[1];
            AreaUnit unit = findUnit(unitString);
            if (unit == null) {
                throw new ConvertException("Unknown area unit: " + unitString);
            }
            return new AreaValue(value, unit);
        } catch (NumberFormatException e) {
            throw new ConvertException("Invalid number format: " + parts[0], e);
        }
    }
}