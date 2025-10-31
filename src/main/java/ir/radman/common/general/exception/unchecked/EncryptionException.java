package ir.radman.common.general.exception.unchecked;

import ir.radman.common.general.exception.BaarmaanRuntimeException;
import lombok.Data;

@Data
public class EncryptionException extends BaarmaanRuntimeException {

    public EncryptionException(String message) {
        super(message);
    }

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
