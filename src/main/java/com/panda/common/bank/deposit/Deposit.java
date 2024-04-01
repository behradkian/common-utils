package com.panda.common.bank.deposit;

import com.panda.common.bank.enumeration.DepositType;
import com.panda.common.bank.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deposit {

    private String iban;
    private String depositNumber;
    private Customer customerDetails;
    private boolean isActive;
    private boolean isBlocked;
    private DepositType depositType;
    private float profitPercent;
    private BigDecimal withdrawAbleAmount;
    private BigDecimal totalAmount;

}
