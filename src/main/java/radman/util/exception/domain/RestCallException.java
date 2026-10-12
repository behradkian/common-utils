package radman.util.exception.domain;

import radman.util.exception.base.RadmanRuntimeException;
import radman.util.rest.enumeration.HttpStatusCode;

public class RestCallException extends RadmanRuntimeException {

    public RestCallException(HttpStatusCode status, String message) {
        super(status, message);
    }

    public RestCallException(HttpStatusCode status, String message, Throwable cause) {
        super(status, message, cause);
    }
}
