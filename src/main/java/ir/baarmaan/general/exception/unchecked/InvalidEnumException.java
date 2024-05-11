package ir.baarmaan.general.exception.unchecked;

import ir.baarmaan.general.exception.BaarmaanRuntimeException;

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
