package com.panda.common.bank.exception;

import com.panda.common.general.exception.PandaRuntimeException;

public abstract class BankRuntimeException extends PandaRuntimeException {

    public BankRuntimeException(String message) {
        super(message);
    }

    public BankRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankRuntimeException(Throwable cause) {
        super(cause);
    }

}
