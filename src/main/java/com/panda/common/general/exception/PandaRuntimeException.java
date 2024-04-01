package com.panda.common.general.exception;

import lombok.Data;

@Data
public abstract class PandaRuntimeException extends RuntimeException{

    private PandaRuntimeException() {
    }

    public PandaRuntimeException(String message) {
        super(message);
    }

    public PandaRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PandaRuntimeException(Throwable cause) {
        super(cause);
    }

}
