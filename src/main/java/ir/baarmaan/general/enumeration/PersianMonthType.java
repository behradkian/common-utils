package ir.baarmaan.general.enumeration;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum PersianMonthType implements Serializable {

    FARVARDIN(1),
    ORDIBEHESHT(2),
    KHORDAD(3),
    TIR(4),
    MORDAD(5),
    SHAHRIVAR(6),
    MEHR(7),
    ABAN(8),
    AZAR(9),
    DEY(10),
    BAHMAN(11),
    ESFAND(12);

    private int id;

    PersianMonthType(int id) {
        this.id = id;
    }
}
