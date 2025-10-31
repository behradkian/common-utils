package ir.radman.common.general.exception.unchecked;

import ir.radman.common.general.exception.BaarmaanRuntimeException;
import lombok.Data;

@Data
public class InvalidTemperatureException extends BaarmaanRuntimeException {

    public InvalidTemperatureException(String message) {
        super(message);
    }

    public InvalidTemperatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTemperatureException(Throwable cause) {
        super(cause);
    }
}
