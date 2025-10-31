package ir.radman.common.general.exception.unchecked;

import ir.radman.common.general.exception.BaarmaanRuntimeException;

public class InvalidEnumException extends BaarmaanRuntimeException {

    public InvalidEnumException(String message) {
        super(message);
    }

    public InvalidEnumException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEnumException(Throwable cause) {
        super(cause);
    }
}
