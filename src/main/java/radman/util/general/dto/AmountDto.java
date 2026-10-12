package radman.util.general.dto;

import radman.util.general.enumeration.Currency;
import radman.util.string.StringUtility;

import java.math.BigDecimal;

/**
 * @author : Pedram Behradkian
 */
public record AmountDto(BigDecimal amount, Currency currency) {

    @Override
    public String toString() {
        return StringUtility.toJsonString(this);
    }
}