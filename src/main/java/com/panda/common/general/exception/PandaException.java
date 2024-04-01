package com.panda.common.general.exception;

import lombok.Data;

@Data
public abstract class PandaException extends Exception{
    private PandaException() {
    }

    public PandaException(String message) {
        super(message);
    }

    public PandaException(String message, Throwable cause) {
        super(message, cause);
    }

    public PandaException(Throwable cause) {
        super(cause);
    }

}
