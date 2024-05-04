package ir.baarmaan.general.enumeration;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum PersianWeekDays implements Serializable {

    SHANBE(1),
    YEKSHANBE(2),
    DOSHANBE(3),
    SESHANBE(4),
    CHAHARSHANBE(5),
    PANJSHANBE(6),
    JOME(7);

    private int id;

    PersianWeekDays(int id) {
        this.id = id;
    }
}
