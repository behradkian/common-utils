package com.panda.common.general.exception;

public class DateException extends PandaRuntimeException{

    public DateException(String message) {
        super(message);
    }

    public DateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateException(Throwable cause) {
        super(cause);
    }
}
