package ir.radman.common.general.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@AllArgsConstructor
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public enum RomanNumberType implements Serializable {

    I(1, "I"),
    V(5, "V"),
    X(10, "X"),
    L(50, "L"),
    C(100, "C"),
    D(500, "D"),
    M(1000, "M");

    final int num;
    final String character;

    public static RomanNumberType getByCharacter(String character) {
        return Arrays.stream(values()).filter(v -> v.character.equals(character)).findFirst().orElse(null);
    }

}
