package ir.radman.common.general.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
@AllArgsConstructor
@Getter
public enum AreaUnit {
    SQUARE_METER("m²", "متر مربع", 1.0),
    SQUARE_KILOMETER("km²", "کیلومتر مربع", 1_000_000.0),
    SQUARE_CENTIMETER("cm²", "سانتی‌متر مربع", 0.0001),
    SQUARE_MILLIMETER("mm²", "میلی‌متر مربع", 0.000001),
    HECTARE("ha", "هکتار", 10_000.0),
    ARE("a", "آر", 100.0),
    SQUARE_MILE("mi²", "مایل مربع", 2_589_988.11),
    SQUARE_YARD("yd²", "یارد مربع", 0.836127),
    SQUARE_FOOT("ft²", "فوت مربع", 0.092903),
    SQUARE_INCH("in²", "اینچ مربع", 0.00064516),
    ACRE("ac", "ایکر", 4_046.8564224),
    JERIB("jerib", "جریب", 1_000.0), // 1000 متر مربع
    HECTARE_JERIB("hajerib", "هکتار جریبی", 10_000.0); // 10000 متر مربع

    private final String symbol;
    private final String persianName;
    private final double squareMeters;

}
