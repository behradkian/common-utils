package com.panda.common.bank.exception;

import com.panda.common.general.exception.PandaException;

public abstract class BankException extends PandaException {

    public BankException(String message) {
        super(message);
    }

    public BankException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankException(Throwable cause) {
        super(cause);
    }
}
