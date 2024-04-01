package com.panda.common.general.exception;

import lombok.Data;

@Data
public class EncryptionException extends PandaRuntimeException {

    public EncryptionException(String message) {
        super(message);
    }

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
