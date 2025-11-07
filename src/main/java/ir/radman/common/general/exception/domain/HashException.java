package ir.radman.common.general.exception.domain;

import ir.radman.common.general.exception.base.RadmanRuntimeException;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
public class HashException extends RadmanRuntimeException {
    public HashException(String message) {
        super(message);
    }

    public HashException(String message, Throwable cause) {
        super(message, cause);
    }
}
