package ir.baarmaan.general.enumeration;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum AddressType implements Serializable {

    HOME(1),
    WORK(2);

    private int id;

    AddressType(int id) {
        this.id = id;
    }
}
