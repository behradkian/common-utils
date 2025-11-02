package ir.radman.common.general.exception.unchecked;

import ir.radman.common.general.exception.RadmanRuntimeException;
import lombok.Data;

@Data
public class InvalidTemperatureException extends RadmanRuntimeException {

    public InvalidTemperatureException(String message) {
        super(message);
    }

    public InvalidTemperatureException(String message, Throwable cause) {
        super(message, cause);
    }

}
