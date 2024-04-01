package com.panda.common.general.exception;

public class NetworkRuntimeException extends PandaRuntimeException{

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
