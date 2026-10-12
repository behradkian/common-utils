package radman.util.exception.domain;

import radman.util.exception.base.RadmanRuntimeException;
import radman.util.rest.enumeration.HttpStatusCode;

public class InvalidTemperatureException extends RadmanRuntimeException {

    public InvalidTemperatureException(String message) {
        super(HttpStatusCode.BAD_REQUEST, message);
    }

    public InvalidTemperatureException(String message, Throwable cause) {
        super(message, cause);
    }

}
