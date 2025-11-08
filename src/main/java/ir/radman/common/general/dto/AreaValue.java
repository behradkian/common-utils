package ir.radman.common.general.dto;

import ir.radman.common.general.enumeration.AreaUnit;
import ir.radman.common.util.convertor.AreaConverter;

import java.math.BigDecimal;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
public record AreaValue(BigDecimal value, AreaUnit unit) {

    public AreaValue convertTo(AreaUnit targetUnit) {
        return new AreaValue(AreaConverter.convert(value, unit, targetUnit), targetUnit);
    }

    @Override
    public String toString() {
        return AreaConverter.format(value, unit, 2);
    }

    public String toPersianString() {
        return AreaConverter.formatPersian(value, unit, 2);
    }
}