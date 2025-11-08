package ir.radman.common.general.dto;

import ir.radman.common.general.enumeration.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountDto {
    private BigDecimal amount;
    private Currency currency;
}