package ir.radman.common.general.exception.domain;

import ir.radman.common.general.exception.base.RadmanRuntimeException;

public class EncryptionException extends RadmanRuntimeException {

    public EncryptionException(String message) {
        super(message);
    }

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
