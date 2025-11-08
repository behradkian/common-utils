package ir.radman.common.general.exception.domain;

import ir.radman.common.general.enumeration.http.StatusCode;
import ir.radman.common.general.exception.base.RadmanRuntimeException;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
public class ConvertException extends RadmanRuntimeException {
    public ConvertException(String message) {
        super(StatusCode.BAD_REQUEST, message);
    }

    public ConvertException(String message, Throwable cause) {
        super(StatusCode.BAD_REQUEST, message, cause);
    }
}
