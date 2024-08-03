package ir.baarmaan.general.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public enum IranianNationalCardType {

    NORMAL(1),
    SMART(2);

    private final Integer id;

    public static IranianNationalCardType getById(int id) {
        return Arrays.stream(values()).filter(v -> v.id.equals(id)).findFirst().orElse(null);
    }

}
