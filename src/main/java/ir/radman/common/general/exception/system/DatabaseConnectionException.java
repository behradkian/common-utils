package ir.radman.common.general.exception.system;

import ir.radman.common.general.exception.base.RadmanCheckedException;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/02
 */
public class DatabaseConnectionException extends RadmanCheckedException {
    public DatabaseConnectionException(String message) {
        super(message);
    }
}
