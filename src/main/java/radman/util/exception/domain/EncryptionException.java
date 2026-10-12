package radman.util.exception.domain;

import radman.util.exception.base.RadmanRuntimeException;

/**
 * Encryption exception class
 *
 * @author : Pedram Behradkian
 */
public class EncryptionException extends RadmanRuntimeException {
    public EncryptionException(String message) {
        super(message);
    }

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}