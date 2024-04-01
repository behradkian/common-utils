package com.panda.common.bank.enumeration;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum DepositType implements Serializable {

    LONG_TERM(1),
    SHORT_TERM(2),
    QARZ(3);

    private int id;

    DepositType(int id) {
        this.id = id;
    }
}
