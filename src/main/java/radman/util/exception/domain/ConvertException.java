package radman.util.exception.domain;

import radman.util.exception.base.RadmanRuntimeException;
import radman.util.rest.enumeration.HttpStatusCode;

/**
 * @author : Pedram Behradkian
 */
public class ConvertException extends RadmanRuntimeException {
    public ConvertException(String message) {
        super(HttpStatusCode.BAD_REQUEST, message);
    }

    public ConvertException(String message, Throwable cause) {
        super(HttpStatusCode.BAD_REQUEST, message, cause);
    }
}
