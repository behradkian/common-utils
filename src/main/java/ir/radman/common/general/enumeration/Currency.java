package ir.radman.common.general.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
@Getter
@AllArgsConstructor
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public enum Currency implements Serializable {

    IRR(1,"IRR"),
    USD(2,"USD"),
    EUR(3,"EUR");

    private final Integer id;
    private final String code;
}
