package com.panda.common.bank.card;

import com.panda.common.bank.deposit.Deposit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    private Deposit depositDetails;
    private Date expireDate;
    private String CVV2;
    private boolean isActive;

}
