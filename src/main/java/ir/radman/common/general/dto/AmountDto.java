package ir.radman.common.general.dto;

import ir.radman.common.general.enumeration.Currency;

import java.math.BigDecimal;

public record AmountDto(BigDecimal amount, Currency currency) {
}