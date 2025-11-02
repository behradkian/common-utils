package ir.radman.common.general.enumeration;

import ir.radman.common.general.exception.domain.InvalidEnumException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AddressType implements Serializable {

    HOME(1),
    WORK(2);

    private final Integer id;

    public static AddressType getById(int id) {
        return Arrays.stream(values()).filter(v -> v.id.equals(id)).findFirst()
                .orElseThrow(() -> new InvalidEnumException(id + " in " + AddressType.class.getName() + " is not valid"));
    }

}