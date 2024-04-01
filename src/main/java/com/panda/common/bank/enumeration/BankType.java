package com.panda.common.bank.enumeration;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum BankType implements Serializable {

    SAMAN(1, "SAM"),
    MELLI(2,"MLI"),
    MELLAT(3,"MLT"),
    EGHTESAD_NOVIN(4,"EGN"),
    RESALAT(5, "RES"),
    AYANDEH(6,"AYN"),
    SHAHR(7, "SHR");

    private int id;
    private String name;

    BankType(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
