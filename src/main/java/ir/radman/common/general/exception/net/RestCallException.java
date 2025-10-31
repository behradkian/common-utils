package ir.radman.common.general.exception.net;

public class RestCallException extends RuntimeException {
    public RestCallException() {

    }

    public RestCallException(String message) {
        super(message);
    }

    public RestCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
