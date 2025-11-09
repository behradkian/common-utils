package ir.radman.common.general.dto.http;

import ir.radman.common.general.enumeration.http.ContentType;
import ir.radman.common.general.enumeration.http.HttpMethod;
import lombok.Builder;

import java.util.Map;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
@Builder
public record HttpRequestDto(
        String url,
        HttpMethod method,
        ContentType contentType,
        Map<String, Object> headers,
        String body,
        Map<String, String> formParams,
        int timeoutSeconds,
        boolean trustAllSsl
) {

    public boolean isFormRequest() {
        return formParams != null && !formParams.isEmpty();
    }

    public boolean isJsonRequest() {
        return contentType == ContentType.JSON;
    }
}
