package com.panda.common.bank.enumeration;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum RelationType implements Serializable {


    ;


    private int id;

    RelationType(int id) {
        this.id = id;
    }
}
