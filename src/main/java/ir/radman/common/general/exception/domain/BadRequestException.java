package ir.radman.common.general.exception.domain;

import ir.radman.common.general.enumeration.rest.StatusCode;
import ir.radman.common.general.exception.base.RadmanRuntimeException;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/02
 */
public class BadRequestException extends RadmanRuntimeException {
    public BadRequestException(String message) {
        super(StatusCode.HTTP_BAD_REQUEST, message);
    }


    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
