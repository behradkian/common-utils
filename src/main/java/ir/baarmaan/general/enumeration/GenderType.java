package ir.baarmaan.general.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.baarmaan.general.exception.unchecked.InvalidEnumException;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public enum GenderType implements Serializable {

    MALE(1),
    FEMALE(2);

    private final Integer id;

    GenderType(int id) {
        this.id = id;
    }

    public static GenderType getById(int id) {
        return Arrays.stream(values()).filter(v -> v.id.equals(id)).findFirst()
                .orElseThrow(() -> new InvalidEnumException(id + " in " + GenderType.class.getName() + " is not valid"));
    }

}
