package ir.baarmaan.general.exception;

import lombok.Data;

@Data
public abstract class BaarmaanRuntimeException extends RuntimeException{

    private BaarmaanRuntimeException() {
    }

    public BaarmaanRuntimeException(String message) {
        super(message);
    }

    public BaarmaanRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaarmaanRuntimeException(Throwable cause) {
        super(cause);
    }

}
