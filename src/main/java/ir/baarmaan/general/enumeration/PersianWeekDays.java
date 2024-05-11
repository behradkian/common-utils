package ir.baarmaan.general.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public enum PersianWeekDays implements Serializable {

    SHANBE(1, true),
    YEKSHANBE(2, true),
    DOSHANBE(3, true),
    SESHANBE(4, true),
    CHAHARSHANBE(5, true),
    PANJSHANBE(6, false),
    JOME(7, false);

    private final Integer id;
    private final Boolean isWorkingDay;

    PersianWeekDays(int id, boolean isWorkingDay) {
        this.id = id;
        this.isWorkingDay = isWorkingDay;
    }

    public static PersianWeekDays getById(int id) {
        return Arrays.stream(values()).filter(v -> v.id.equals(id)).findFirst()
                .orElseThrow(() -> new InvalidEnumException(id + " in " + PersianWeekDays.class.getName() + " is not valid"));
    }
}
