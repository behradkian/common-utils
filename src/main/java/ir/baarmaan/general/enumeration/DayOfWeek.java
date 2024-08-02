package ir.baarmaan.general.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.baarmaan.general.exception.unchecked.InvalidEnumException;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.io.Serializable;
import java.util.Arrays;

@Getter
@AllArgsConstructor
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public enum DayOfWeek implements Serializable {

    MONDAY(1, "MONDAY", "\u062f\u0648\u0634\u0646\u0628\u0647"),
    TUESDAY(2, "TUESDAY", "\u0633\u0647 \u0634\u0646\u0628\u0647"),
    WEDNESDAY(3, "WEDNESDAY", "\u0686\u0647\u0627\u0631\u0634\u0646\u0628\u0647"),
    THURSDAY(4, "THURSDAY", "\u067e\u0646\u062c \u0634\u0646\u0628\u0647"),
    FRIDAY(5, "FRIDAY", "\u062c\u0645\u0639\u0647"),
    SATURDAY(6, "SATURDAY", "\u0634\u0646\u0628\u0647"),
    SUNDAY(7, "SUNDAY", "\u06cc\u06a9\u0634\u0646\u0628\u0647");

    private final Integer id;
    private final String value;
    private final String persian;

    public static DayOfWeek getById(int id) {
        return Arrays.stream(values()).filter(v -> v.id.equals(id)).findFirst()
                .orElseThrow(() -> new InvalidEnumException(id + " in " + DayOfWeek.class.getName() + " is not valid"));
    }

    public static DayOfWeek getByValue(String value) {
        return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst()
                .orElseThrow(() -> new InvalidEnumException(value + " in " + DayOfWeek.class.getName() + " is not valid"));
    }
}
