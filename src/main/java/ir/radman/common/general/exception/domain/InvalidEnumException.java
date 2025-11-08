package ir.radman.common.general.exception.domain;

import ir.radman.common.general.enumeration.http.StatusCode;
import ir.radman.common.general.exception.base.RadmanRuntimeException;

public class InvalidEnumException extends RadmanRuntimeException {

    public InvalidEnumException(String message) {
        super(message);
    }

    public InvalidEnumException(String enumName, Object invalidValue) {
        super(
                StatusCode.HTTP_BAD_REQUEST,
                "INVALID_ENUM_VALUE",
                paramsOf("enum", enumName, "invalidValue", invalidValue),
                String.format("Invalid value '%s' for enum '%s'", invalidValue, enumName),
                null
        );
    }

}
