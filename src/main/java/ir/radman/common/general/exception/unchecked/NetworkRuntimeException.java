package ir.radman.common.general.exception.unchecked;

import ir.radman.common.general.exception.RadmanRuntimeException;
import lombok.Data;

@Data
public class NetworkRuntimeException extends RadmanRuntimeException {

    public NetworkRuntimeException(String message) {
        super(message);
    }

    public NetworkRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
