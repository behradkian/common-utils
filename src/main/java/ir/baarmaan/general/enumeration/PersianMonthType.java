package ir.baarmaan.general.enumeration;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum PersianMonthType implements Serializable {

    FARVARDIN(1,31),
    ORDIBEHESHT(2,31),
    KHORDAD(3,31),
    TIR(4,31),
    MORDAD(5,31),
    SHAHRIVAR(6,31),
    MEHR(7,30),
    ABAN(8,30),
    AZAR(9,30),
    DEY(10,30),
    BAHMAN(11,30),
    ESFAND(12,29),
    ESFAND_IN_KABISE(12,30);

    private int id;
    private int days;

    PersianMonthType(int id, int days) {
        this.id = id;
        this.days = days;
    }
}
