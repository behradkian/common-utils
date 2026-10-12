package radman.util.exception.domain;

import radman.util.exception.base.RadmanRuntimeException;
import radman.util.rest.enumeration.HttpStatusCode;

/**
 * @author : Pedram Behradkian
 */
public class BadRequestException extends RadmanRuntimeException {
    public BadRequestException(String message) {
        super(HttpStatusCode.BAD_REQUEST, message);
    }


    public BadRequestException(String message, Throwable cause) {
        super(HttpStatusCode.BAD_REQUEST, message, cause);
    }
}
