package ir.baarmaan.general.dto;

import ir.baarmaan.general.enumeration.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amount {

    private BigDecimal amount;
    private Currency currency;

}
