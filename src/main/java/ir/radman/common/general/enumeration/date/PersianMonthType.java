package ir.radman.common.general.enumeration.date;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.radman.common.general.exception.unchecked.InvalidEnumException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@AllArgsConstructor
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public enum PersianMonthType implements Serializable {

    FARVARDIN(1, "\u0641\u0631\u0648\u0631\u062f\u06cc\u0646"),
    ORDIBEHESHT(2, "\u0627\u0631\u062f\u06cc\u0628\u0647\u0634\u062a"),
    KHORDAD(3, "\u062e\u0631\u062f\u0627\u062f"),
    TIR(4, "\u062a\u06cc\u0631"),
    MORDAD(5, "\u0645\u0631\u062f\u0627\u062f"),
    SHAHRIVAR(6, "\u0634\u0647\u0631\u06cc\u0648\u0631"),
    MEHR(7, "\u0645\u0647\u0631"),
    ABAN(8, "\u0622\u0628\u0627\u0646"),
    AZAR(9, "\u0622\u0630\u0631"),
    DEY(10, "\u062f\u06cc"),
    BAHMAN(11, "\u0628\u0647\u0645\u0646"),
    ESFAND(12, "\u0627\u0633\u0641\u0646\u062f");

    private final Integer id;
    private final String value;


    public static PersianMonthType getById(int id) {
        return Arrays.stream(values()).filter(v -> v.id.equals(id)).findFirst()
                .orElseThrow(() -> new InvalidEnumException(id + " in " + PersianMonthType.class.getName() + " is not valid"));
    }

    public static PersianMonthType getByValue(String value) {
        return Arrays.stream(values()).filter(v -> v.getValue().equals(value)).findFirst().orElse(null);
    }


}
