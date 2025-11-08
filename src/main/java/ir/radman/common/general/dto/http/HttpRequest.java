package ir.radman.common.general.dto.http;

import ir.radman.common.general.enumeration.http.ContentType;
import ir.radman.common.general.enumeration.http.HttpMethod;

import java.util.Map;

public class HttpRequest {
    private String url;
    private HttpMethod method;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private Object body;
    private ContentType contentType;
    private int timeout;
    private AuthorizationConfiguratiom authorizationConfiguratiom;
}