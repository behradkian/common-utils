package ir.radman.common.general.dto.http;

import ir.radman.common.general.enumeration.http.StatusCode;
import ir.radman.common.util.convertor.JsonConvertor;
import lombok.*;

import java.util.Map;

/**
 * Enhanced REST response DTO with additional metadata
 *
 * @author : Pedram Behradkian
 * @date : 2025/11/10
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestResponseDto {
    private StatusCode statusCode;
    private Map<String, String> headers;
    private String body;
    private String contentType;
    private String requestUrl;
    private long responseTime;

    public boolean isSuccess() {
        return statusCode != null && statusCode.isSuccess();
    }

    public boolean hasBody() {
        return body != null && !body.trim().isEmpty();
    }

    public boolean isClientError() {
        return statusCode != null && statusCode.isClientError();
    }

    public boolean isServerError() {
        return statusCode != null && statusCode.isServerError();
    }

    public String getHeader(String name) {
        return headers != null ? headers.get(name) : null;
    }

    public <T> T getBodyAs(Class<T> clazz) {
        if (!hasBody()) {
            return null;
        }
        return JsonConvertor.jsonString2Object(body, clazz);
    }
}