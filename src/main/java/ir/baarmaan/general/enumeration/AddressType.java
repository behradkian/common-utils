package ir.baarmaan.general.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.baarmaan.general.exception.unchecked.InvalidEnumException;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public enum AddressType implements Serializable {

    HOME(1), WORK(2);

    private final Integer id;

    AddressType(int id) {
        this.id = id;
    }

    public static AddressType getById(int id) {
        return Arrays.stream(values()).filter(v -> v.id.equals(id)).findFirst()
                .orElseThrow(() -> new InvalidEnumException(id + " in " + AddressType.class.getName() + " is not valid"));
    }
}
