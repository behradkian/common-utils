package ir.baarmaan.general.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public enum PersonType implements Serializable {

    REAL(1),
    LEGAL(2);

    private final Integer id;

    PersonType(int id) {
        this.id = id;
    }

    public static PersonType getById(int id) {
        return Arrays.stream(values()).filter(v -> v.id.equals(id)).findFirst()
                .orElseThrow(() -> new InvalidEnumException(""));
    }
}

