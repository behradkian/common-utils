package ir.radman.common.general.exception.domain;

import ir.radman.common.general.exception.base.RadmanRuntimeException;

public class ResourceNotFoundException extends RadmanRuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
