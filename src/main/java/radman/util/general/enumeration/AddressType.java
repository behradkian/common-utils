package radman.util.general.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import radman.util.exception.domain.InvalidEnumException;

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