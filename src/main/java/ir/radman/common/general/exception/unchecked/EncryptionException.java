package ir.radman.common.general.exception.unchecked;

import ir.radman.common.general.exception.RadmanRuntimeException;
import lombok.Data;

@Data
public class EncryptionException extends RadmanRuntimeException {

    public EncryptionException(String message) {
        super(message);
    }

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
