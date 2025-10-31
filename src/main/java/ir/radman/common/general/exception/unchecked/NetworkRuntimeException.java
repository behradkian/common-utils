package ir.radman.common.general.exception.unchecked;

import ir.radman.common.general.exception.BaarmaanRuntimeException;
import lombok.Data;

@Data
public class NetworkRuntimeException extends BaarmaanRuntimeException {

    public NetworkRuntimeException(String message) {
        super(message);
    }

    public NetworkRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetworkRuntimeException(Throwable cause) {
        super(cause);
    }
}
