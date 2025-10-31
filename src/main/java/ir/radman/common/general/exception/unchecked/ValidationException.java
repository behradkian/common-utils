package ir.radman.common.general.exception.unchecked;

import ir.radman.common.general.exception.BaarmaanRuntimeException;
import lombok.Data;

@Data
public class ValidationException extends BaarmaanRuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
