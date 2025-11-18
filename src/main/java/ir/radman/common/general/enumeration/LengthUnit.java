package ir.radman.common.general.enumeration;

import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
public enum LengthUnit implements Serializable {
    // Metric System
    NANOMETER("nm", "nanometer", new BigDecimal("1e-9")),
    MICROMETER("µm", "micrometer", new BigDecimal("1e-6")),
    MILLIMETER("mm", "millimeter", new BigDecimal("0.001")),
    CENTIMETER("cm", "centimeter", new BigDecimal("0.01")),
    DECIMETER("dm", "decimeter", new BigDecimal("0.1")),
    METER("m", "meter", BigDecimal.ONE),
    DECAMETER("dam", "decameter", new BigDecimal("10")),
    HECTOMETER("hm", "hectometer", new BigDecimal("100")),
    KILOMETER("km", "kilometer", new BigDecimal("1000")),

    // Imperial/US Customary System
    INCH("in", "inch", new BigDecimal("0.0254")),
    FOOT("ft", "foot", new BigDecimal("0.3048")),
    YARD("yd", "yard", new BigDecimal("0.9144")),
    MILE("mi", "mile", new BigDecimal("1609.344")),
    NAUTICAL_MILE("nmi", "nautical_mile", new BigDecimal("1852")),

    // Astronomical Units
    ASTRONOMICAL_UNIT("au", "astronomical_unit", new BigDecimal("1.495978707e11")),
    LIGHT_YEAR("ly", "light_year", new BigDecimal("9.4607304725808e15")),
    PARSEC("pc", "parsec", new BigDecimal("3.08567758149137e16"));

    private final String symbol;
    private final String name;
    private final BigDecimal toMetersFactor;
    private final BigDecimal fromMetersFactor;

    LengthUnit(String symbol, String name, BigDecimal toMetersFactor) {
        this.symbol = symbol;
        this.name = name;
        this.toMetersFactor = toMetersFactor;
        this.fromMetersFactor = BigDecimal.ONE.divide(toMetersFactor, MathContext.DECIMAL128);
    }

    private static final Map<String, LengthUnit> LOOKUP_MAP;

    static {
        Map<String, LengthUnit> map = new HashMap<>();
        for (LengthUnit unit : values()) {
            // Add all possible lookup keys
            map.put(unit.name().toLowerCase(Locale.ROOT), unit);
            map.put(unit.symbol.toLowerCase(Locale.ROOT), unit);
            map.put(unit.name.toLowerCase(Locale.ROOT), unit);

            // Add common aliases
            if (unit.symbol.equals("m")) {
                map.put("meter", unit);
                map.put("metre", unit);
            } else if (unit.symbol.equals("km")) {
                map.put("kilometer", unit);
                map.put("kilometre", unit);
            } else if (unit.symbol.equals("cm")) {
                map.put("centimeter", unit);
                map.put("centimetre", unit);
            } else if (unit.symbol.equals("in")) {
                map.put("inch", unit);
                map.put("inches", unit);
            } else if (unit.symbol.equals("ft")) {
                map.put("foot", unit);
                map.put("feet", unit);
            }
        }
        LOOKUP_MAP = Collections.unmodifiableMap(map);
    }

    public static LengthUnit from(String key) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit key cannot be null or empty");
        }

        LengthUnit unit = LOOKUP_MAP.get(key.trim().toLowerCase(Locale.ROOT));
        if (unit == null) {
            throw new IllegalArgumentException("Unknown length unit: " + key);
        }
        return unit;
    }

    public BigDecimal convertTo(BigDecimal value, LengthUnit targetUnit) {
        if (this == targetUnit) return value;
        BigDecimal meters = value.multiply(this.toMetersFactor);
        return meters.multiply(targetUnit.fromMetersFactor);
    }

    public boolean isMetric() {
        return this == METER || this.name().endsWith("METER");
    }

    public boolean isImperial() {
        return this == INCH || this == FOOT || this == YARD || this == MILE;
    }
}