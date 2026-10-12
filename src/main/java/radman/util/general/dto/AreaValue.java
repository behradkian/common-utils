package radman.util.general.dto;

import radman.util.convertor.AreaConverter;
import radman.util.general.enumeration.AreaUnit;

import java.math.BigDecimal;

/**
 * @author : Pedram Behradkian
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