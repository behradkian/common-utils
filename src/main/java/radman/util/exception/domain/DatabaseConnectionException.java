package radman.util.exception.domain;

import radman.util.exception.base.RadmanCheckedException;

/**
 * @author : Pedram Behradkian
 */
public class DatabaseConnectionException extends RadmanCheckedException {
    public DatabaseConnectionException(String message) {
        super(message);
    }
}
