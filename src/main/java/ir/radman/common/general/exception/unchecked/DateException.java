package ir.radman.common.general.exception.unchecked;

import ir.radman.common.general.exception.BaarmaanRuntimeException;
import lombok.Data;

@Data
public class DateException extends BaarmaanRuntimeException {

    public DateException(String message) {
        super(message);
    }

    public DateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateException(Throwable cause) {
        super(cause);
    }
}
