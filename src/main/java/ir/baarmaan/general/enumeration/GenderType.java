package ir.baarmaan.general.enumeration;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum GenderType implements Serializable {

    MALE(1),
    FEMALE(2);

    private int id;

    GenderType(int id) {
        this.id = id;
    }

}
