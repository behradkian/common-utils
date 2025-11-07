package ir.radman.common.general.exception.domain;

import ir.radman.common.general.exception.base.RadmanRuntimeException;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
public class GenerateHashException extends RadmanRuntimeException {
    public GenerateHashException(String message) {
        super(message);
    }

    public GenerateHashException(String message, Throwable cause) {
        super(message, cause);
    }
}
