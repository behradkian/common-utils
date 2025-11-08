package ir.radman.common.general.exception.domain;

import ir.radman.common.general.enumeration.http.StatusCode;
import ir.radman.common.general.exception.base.RadmanRuntimeException;

public class RestCallException extends RadmanRuntimeException {

    public RestCallException(StatusCode status, String message) {
        super(status, message);
    }

    public RestCallException(StatusCode status, String message, Throwable cause) {
        super(status, message, cause);
    }
}
