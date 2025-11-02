package ir.radman.common.general.exception.domain;

import ir.radman.common.general.exception.base.RadmanRuntimeException;

public class NetworkRuntimeException extends RadmanRuntimeException {

    public NetworkRuntimeException(String message) {
        super(message);
    }

    public NetworkRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
