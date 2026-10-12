package radman.util.general.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import radman.util.exception.domain.InvalidEnumException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public enum PersonType implements Serializable {

    REAL_PERSON(1, "REAL"),
    CORPORATE_PERSON(2, "CORPORATE");

    private static final Map<Integer, PersonType> LOOKUP_BY_ID = Arrays.stream(values()).collect(Collectors.toUnmodifiableMap(PersonType::getId, e -> e));
    private static final Map<String, PersonType> LOOKUP_BY_CODE = Arrays.stream(values()).collect(Collectors.toUnmodifiableMap(PersonType::getCode, e -> e));
    private final int id;
    private final String code;

    public static PersonType getById(int id) {
        PersonType type = LOOKUP_BY_ID.get(id);
        if (type == null) {
            throw new InvalidEnumException(PersonType.class.getSimpleName(), id);
        }
        return type;
    }

    public static PersonType getByCode(String code) {
        if (code == null) {
            throw new InvalidEnumException(PersonType.class.getSimpleName(), null);
        }
        PersonType type = LOOKUP_BY_CODE.get(code.toUpperCase());
        if (type == null) {
            throw new InvalidEnumException(PersonType.class.getSimpleName(), code);
        }
        return type;
    }
}
