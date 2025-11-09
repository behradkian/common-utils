package ir.radman.common.general.dto.http;

import ir.radman.common.general.enumeration.http.ContentType;
import ir.radman.common.general.enumeration.http.HttpMethod;
import lombok.*;

import java.io.Serializable;
import java.net.URL;
import java.util.Map;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequest implements Serializable {
    private URL url;
    private HttpMethod method;
    private Map<String, Object> headers;
    private Map<String, String> queryParams;
    private Object body;
    private ContentType contentType;
    private int timeout;
    private AuthorizationConfiguration authorizationConfiguration;
}