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

    private final Integer id;
    private final Integer days;


    public static PersianMonthType getById(int id) {
        return Arrays.stream(values()).filter(v -> v.id.equals(id)).findFirst()
                .orElseThrow(() -> new InvalidEnumException(id + " in " + PersianMonthType.class.getName() + " is not valid"));
    }

}
