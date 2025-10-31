package ir.radman.common.general.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public enum Nationality implements Serializable {

    IRAN(1,"Iran");

    private final Integer id;
    private final String name;

}
