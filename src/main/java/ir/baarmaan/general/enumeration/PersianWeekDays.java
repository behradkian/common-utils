package ir.baarmaan.general.enumeration;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum PersianWeekDays implements Serializable {

    SHANBE(1,true),
    YEKSHANBE(2,true),
    DOSHANBE(3,true),
    SESHANBE(4,true),
    CHAHARSHANBE(5,true),
    PANJSHANBE(6,false),
    JOME(7,false);

    private int id;
    private boolean isWorkingDay;

    PersianWeekDays(int id, boolean isWorkingDay) {
        this.id = id;
        this.isWorkingDay = isWorkingDay;
    }
}
