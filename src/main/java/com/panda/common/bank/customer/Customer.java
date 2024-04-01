package com.panda.common.bank.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private String customerNumber;
    private boolean isActive;
    private List<RelatedPerson> relatedPeople;
}
