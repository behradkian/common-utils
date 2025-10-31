package ir.radman.common.general.exception;

import lombok.Data;

@Data
public abstract class BaarmaanException extends Exception{
    private BaarmaanException() {
    }

    public BaarmaanException(String message) {
        super(message);
    }

    public BaarmaanException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaarmaanException(Throwable cause) {
        super(cause);
    }

}
