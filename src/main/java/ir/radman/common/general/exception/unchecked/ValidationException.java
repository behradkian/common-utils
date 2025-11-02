package ir.radman.common.general.exception.unchecked;

import ir.radman.common.general.exception.RadmanRuntimeException;
import lombok.Data;

@Data
public class ValidationException extends RadmanRuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
