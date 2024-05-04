package ir.baarmaan.general.enumeration;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum PersonType implements Serializable {

    REAL(1),
    LEGAL(2);

    private int id;

    PersonType(int id) {
        this.id = id;
    }
}
