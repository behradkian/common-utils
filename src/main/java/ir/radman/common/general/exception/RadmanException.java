package ir.radman.common.general.exception;

import java.util.Map;

public abstract class RadmanException extends Exception {

    private Map<String, Object> params;

    public RadmanException(String message) {
        super(message);
    }

    public RadmanException(String message, Throwable cause) {
        super(message, cause);
    }

}