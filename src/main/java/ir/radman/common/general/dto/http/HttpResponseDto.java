package ir.radman.common.general.dto.http;

import ir.radman.common.general.enumeration.http.StatusCode;

import java.util.Map;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
public record HttpResponseDto(
        StatusCode statusCode,
        String body,
        Map<String, Object> headers) {

    public boolean isSuccess() {
        return statusCode.getCode() >= 200 && statusCode.getCode() < 300;
    }
}