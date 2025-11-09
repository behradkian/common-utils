package ir.radman.common.general.dto;

import ir.radman.common.general.enumeration.Currency;
import ir.radman.common.util.basic.string.StringUtility;

import java.math.BigDecimal;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/09
 */
public record AmountDto(BigDecimal amount, Currency currency) {

    @Override
    public String toString() {
        return StringUtility.toJsonString(this);
    }
}