package ir.radman.common.general.exception.unchecked;

import ir.radman.common.general.exception.RadmanRuntimeException;

public class InvalidEnumException extends RadmanRuntimeException {

    public InvalidEnumException(String message) {
        super(message);
    }

    public InvalidEnumException(String message, Throwable cause) {
        super(message, cause);
    }

}
