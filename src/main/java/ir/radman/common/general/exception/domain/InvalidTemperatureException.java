package ir.radman.common.general.exception.domain;

import ir.radman.common.general.enumeration.rest.StatusCode;
import ir.radman.common.general.exception.base.RadmanRuntimeException;

public class InvalidTemperatureException extends RadmanRuntimeException {

    public InvalidTemperatureException(String message) {
        super(StatusCode.HTTP_BAD_REQUEST,message);
    }

    public InvalidTemperatureException(String message, Throwable cause) {
        super(message, cause);
    }

}
