package radman.util.exception.domain;

import radman.util.exception.base.RadmanRuntimeException;

/**
 * @author : Pedram Behradkian
 */
public class HashException extends RadmanRuntimeException {
    public HashException(String message) {
        super(message);
    }

    public HashException(String message, Throwable cause) {
        super(message, cause);
    }
}
