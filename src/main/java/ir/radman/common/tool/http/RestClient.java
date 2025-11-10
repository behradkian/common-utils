package ir.radman.common.tool.http;

import ir.radman.common.general.dto.http.RestRequestDto;
import ir.radman.common.general.dto.http.RestResponseDto;
import ir.radman.common.general.enumeration.http.AuthorizationType;
import ir.radman.common.general.enumeration.http.ContentType;
import ir.radman.common.general.enumeration.http.HttpMethod;
import ir.radman.common.general.enumeration.http.StatusCode;
import ir.radman.common.util.basic.string.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Enhanced REST client with improved functionality and error handling
 *
 * @author : Pedram Behradkian
 * @date : 2025/11/10
 */
public class RestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

    private final boolean enableSSLVerification;
    private final int defaultConnectionTimeout;
    private final int defaultReadTimeout;
    private final boolean defaultFollowRedirects;

    private RestClient(Builder builder) {
        this.enableSSLVerification = builder.enableSSLVerification;
        this.defaultConnectionTimeout = builder.defaultConnectionTimeout;
        this.defaultReadTimeout = builder.defaultReadTimeout;
        this.defaultFollowRedirects = builder.defaultFollowRedirects;
    }

    public static class Builder {
        private boolean enableSSLVerification = true;
        private int defaultConnectionTimeout = 10000;
        private int defaultReadTimeout = 30000;
        private boolean defaultFollowRedirects = true;

        public Builder enableSSLVerification(boolean enableSSLVerification) {
            this.enableSSLVerification = enableSSLVerification;
            return this;
        }

        public Builder defaultConnectionTimeout(int defaultConnectionTimeout) {
            if (defaultConnectionTimeout < 1000) {
                throw new IllegalArgumentException("Connection timeout must be at least 1000ms");
            }
            this.defaultConnectionTimeout = defaultConnectionTimeout;
            return this;
        }

        public Builder defaultReadTimeout(int defaultReadTimeout) {
            if (defaultReadTimeout < 1000) {
                throw new IllegalArgumentException("Read timeout must be at least 1000ms");
            }
            this.defaultReadTimeout = defaultReadTimeout;
            return this;
        }

        public Builder defaultFollowRedirects(boolean defaultFollowRedirects) {
            this.defaultFollowRedirects = defaultFollowRedirects;
            return this;
        }

        public RestClient build() {
            return new RestClient(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public RestResponseDto call(RestRequestDto request) {
        validateRequest(request);
        long startTime = System.currentTimeMillis();
        HttpURLConnection connection = null;
        try {
            connection = createConnection(request);
            return executeRequest(connection, request, startTime);
        } catch (Exception e) {
            LOGGER.error("Error executing REST call to {}: {}", request.getUrl(), e.getMessage(), e);
            return buildErrorResponse(e, request.getUrl(), startTime);
        } finally {
            closeConnection(connection);
        }
    }

    // === Utility Methods for Common HTTP Operations ===

    public RestResponseDto get(String url) {
        return call(buildDefaultRequest(url, HttpMethod.GET));
    }

    public RestResponseDto get(String url, Map<String, String> queryParams) {
        return call(buildDefaultRequest(url, HttpMethod.GET)
                .toBuilder()
                .queryParams(queryParams)
                .build());
    }

    public RestResponseDto get(String url, Map<String, String> headers, Map<String, String> queryParams) {
        return call(buildDefaultRequest(url, HttpMethod.GET)
                .toBuilder()
                .headers(headers)
                .queryParams(queryParams)
                .build());
    }

    public RestResponseDto post(String url, Object body) {
        return call(buildDefaultRequest(url, HttpMethod.POST)
                .toBuilder()
                .body(body)
                .build());
    }

    public RestResponseDto post(String url, Object body, Map<String, String> headers) {
        return call(buildDefaultRequest(url, HttpMethod.POST)
                .toBuilder()
                .body(body)
                .headers(headers)
                .build());
    }

    public RestResponseDto post(String url, Object body, ContentType contentType) {
        return call(buildDefaultRequest(url, HttpMethod.POST)
                .toBuilder()
                .body(body)
                .contentType(contentType)
                .build());
    }

    public RestResponseDto put(String url, Object body) {
        return call(buildDefaultRequest(url, HttpMethod.PUT)
                .toBuilder()
                .body(body)
                .build());
    }

    public RestResponseDto put(String url, Object body, Map<String, String> headers) {
        return call(buildDefaultRequest(url, HttpMethod.PUT)
                .toBuilder()
                .body(body)
                .headers(headers)
                .build());
    }

    public RestResponseDto patch(String url, Object body) {
        return call(buildDefaultRequest(url, HttpMethod.PATCH)
                .toBuilder()
                .body(body)
                .build());
    }

    public RestResponseDto delete(String url) {
        return call(buildDefaultRequest(url, HttpMethod.DELETE));
    }

    public RestResponseDto delete(String url, Map<String, String> headers) {
        return call(buildDefaultRequest(url, HttpMethod.DELETE)
                .toBuilder()
                .headers(headers)
                .build());
    }

    private RestRequestDto buildDefaultRequest(String url, HttpMethod method) {
        return RestRequestDto.builder()
                .url(url)
                .method(method)
                .contentType(ContentType.JSON)
                .authorizationType(AuthorizationType.NO_AUTH)
                .connectionTimeout(defaultConnectionTimeout)
                .readTimeout(defaultReadTimeout)
                .build();
    }

    private void validateRequest(RestRequestDto request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (StringUtility.isBlank(request.getUrl())) {
            throw new IllegalArgumentException("URL cannot be blank");
        }
        if (request.getMethod() == null) {
            throw new IllegalArgumentException("HTTP method is required");
        }
        if (request.getContentType() == null) {
            throw new IllegalArgumentException("Content type is required");
        }

        // Validate timeouts
        if (request.getConnectionTimeout() < 0) {
            throw new IllegalArgumentException("Connection timeout cannot be negative");
        }
        if (request.getReadTimeout() < 0) {
            throw new IllegalArgumentException("Read timeout cannot be negative");
        }
    }

    private HttpURLConnection createConnection(RestRequestDto request) throws IOException {
        String fullUrl = buildFullUrl(request);
        URL url = new URL(fullUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        configureConnection(connection, request);
        setHeaders(connection, request);

        return connection;
    }

    private String buildFullUrl(RestRequestDto request) {
        String baseUrl = request.getUrl();

        // Add query parameters if present
        if (request.getQueryParams() != null && !request.getQueryParams().isEmpty()) {
            baseUrl = appendQueryParams(baseUrl, request.getQueryParams());
        }

        return baseUrl;
    }

    private String appendQueryParams(String url, Map<String, String> queryParams) {
        String queryString = queryParams.entrySet().stream()
                .map(entry -> encode(entry.getKey()) + "=" + encode(entry.getValue()))
                .collect(Collectors.joining("&"));

        String separator = url.contains("?") ? "&" : "?";
        return url + separator + queryString;
    }

    private void configureConnection(HttpURLConnection connection, RestRequestDto request) throws IOException {
        connection.setRequestMethod(request.getMethod().getValue());

        // Set timeouts (use request values if provided, otherwise use defaults)
        int connectionTimeout = request.getConnectionTimeout() > 0 ?
                request.getConnectionTimeout() : defaultConnectionTimeout;
        int readTimeout = request.getReadTimeout() > 0 ?
                request.getReadTimeout() : defaultReadTimeout;

        connection.setConnectTimeout(connectionTimeout);
        connection.setReadTimeout(readTimeout);
        connection.setUseCaches(false);
        connection.setDoInput(true);

        // Handle redirects
        boolean followRedirects = request.isFollowRedirects();
        connection.setInstanceFollowRedirects(followRedirects);

        // Configure SSL if needed
        if (connection instanceof HttpsURLConnection && !enableSSLVerification) {
            configureSSL((HttpsURLConnection) connection);
        }

        // Enable output stream for methods with request body
        if (hasRequestBody(request.getMethod())) {
            connection.setDoOutput(true);
        }
    }

    private void configureSSL(HttpsURLConnection connection) {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            connection.setSSLSocketFactory(sc.getSocketFactory());

            // Don't verify hostname either
            connection.setHostnameVerifier((hostname, session) -> true);

        } catch (Exception e) {
            LOGGER.warn("Failed to configure SSL bypass: {}", e.getMessage());
        }
    }

    private void setHeaders(HttpURLConnection connection, RestRequestDto request) {
        // Set default headers
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", request.getContentType().getMimeType());

        // Set custom headers
        if (request.getHeaders() != null) {
            request.getHeaders().forEach(connection::setRequestProperty);
        }

        // Set authorization header
        setAuthorizationHeader(connection, request);
    }

    private void setAuthorizationHeader(HttpURLConnection connection, RestRequestDto request) {
        if (request.getAuthorizationType() == null ||
                request.getAuthorizationType() == AuthorizationType.NO_AUTH) {
            return;
        }

        String authValue = request.getAuthorizationType().getValue() +
                (request.getAuthorizationValue() != null ? request.getAuthorizationValue() : "");

        if (StringUtility.isNotBlank(authValue)) {
            connection.setRequestProperty("Authorization", authValue.trim());
        }
    }

    private RestResponseDto executeRequest(HttpURLConnection connection, RestRequestDto request, long startTime) throws IOException {
        if (hasRequestBody(request.getMethod()) && request.getBody() != null) {
            writeRequestBody(connection, request);
        }
        int statusCode = connection.getResponseCode();
        String responseBody = readResponseBody(connection);
        Map<String, String> responseHeaders = extractResponseHeaders(connection);
        String responseContentType = connection.getContentType();
        long responseTime = System.currentTimeMillis() - startTime;
        if (request.isEnableLogging()) {
            LOGGER.info("HTTP {} {} - Status: {} - Time: {}ms", request.getMethod(), request.getUrl(), statusCode, responseTime);
        }
        return buildSuccessResponse(statusCode, responseBody, responseHeaders, responseContentType, request.getUrl(), responseTime);
    }

    private void writeRequestBody(HttpURLConnection connection, RestRequestDto request) throws IOException {
        if (request.getBody() == null) return;

        // مدیریت خاص برای OCTET_STREAM با byte[]
        if (request.getContentType() == ContentType.OCTET_STREAM && request.getBody() instanceof byte[]) {
            try (OutputStream os = connection.getOutputStream()) {
                os.write((byte[]) request.getBody());
                os.flush();
            }
            return;
        }

        // مدیریت MULTIPART_FORM_DATA (نیاز به پیاده سازی پیچیده‌تر)
        if (request.getContentType() == ContentType.MULTIPART_FORM_DATA) {
            writeMultipartFormData(connection, request);
            return;
        }

        // برای سایر انواع محتوا
        String bodyContent = convertBodyToString(request);
        if (StringUtility.isBlank(bodyContent)) return;

        try (OutputStream os = connection.getOutputStream();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8))) {
            writer.write(bodyContent);
            writer.flush();
        }
    }

    private void writeMultipartFormData(HttpURLConnection connection, RestRequestDto request) throws IOException {
        String boundary = "----WebKitFormBoundary" + System.currentTimeMillis();
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (OutputStream os = connection.getOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8), true)) {

            if (request.getBody() instanceof Map) {
                Map<?, ?> formData = (Map<?, ?>) request.getBody();
                for (Map.Entry<?, ?> entry : formData.entrySet()) {
                    writer.append("--").append(boundary).append("\r\n");
                    writer.append("Content-Disposition: form-data; name=\"").append(entry.getKey().toString()).append("\"\r\n\r\n");
                    writer.append(entry.getValue().toString()).append("\r\n");
                }
            }
            writer.append("--").append(boundary).append("--\r\n");
        }
    }


    private String convertBodyToString(RestRequestDto request) {
        if (request.getBody() == null) {
            return null;
        }

        try {
            return switch (request.getContentType()) {
                case FORM_URLENCODED -> convertToFormUrlEncoded(request.getBody());
                case JSON -> StringUtility.toJsonString(request.getBody());
                case XML -> convertToXml(request.getBody());
                case TEXT_PLAIN -> {
                    if (request.getBody() instanceof String) {
                        yield (String) request.getBody();
                    }
                    yield request.getBody().toString();
                }
                case MULTIPART_FORM_DATA ->
                        throw new UnsupportedOperationException("Multipart form data requires special handling. Use writeMultipartFormData method instead.");
                case OCTET_STREAM -> convertToOctetStream(request.getBody());
            };
        } catch (Exception e) {
            LOGGER.error("Error converting body to string for content type {}: {}", request.getContentType(), e.getMessage(), e);
            throw new RuntimeException("Failed to convert request body", e);
        }
    }

    private String convertToXml(Object body) {
        if (body instanceof String) {
            return (String) body;
        }
        try {
            return "<root>" + StringUtility.toJsonString(body).replace("\"", "") + "</root>";
        } catch (Exception e) {
            LOGGER.warn("Failed to convert object to XML, using toString: {}", e.getMessage());
            return body.toString();
        }
    }

    private String convertToOctetStream(Object body) {
        if (body instanceof String) {
            return (String) body;
        } else if (body instanceof byte[]) {
            throw new IllegalArgumentException("byte[] should be handled directly in output stream");
        } else {
            return body.toString();
        }
    }

    private String convertToFormUrlEncoded(Object body) {
        if (body instanceof Map<?, ?> map) {
            return map.entrySet().stream()
                    .map(entry -> encode(entry.getKey().toString()) + "=" + encode(entry.getValue().toString()))
                    .collect(Collectors.joining("&"));
        }
        throw new IllegalArgumentException("Body for form-urlencoded must be a Map");
    }

    private String readResponseBody(HttpURLConnection connection) throws IOException {
        InputStream stream = getResponseStream(connection);
        if (stream == null) {
            return "";
        }

        String charset = getCharsetFromContentType(connection.getContentType());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private InputStream getResponseStream(HttpURLConnection connection) throws IOException {
        int statusCode = connection.getResponseCode();
        if (statusCode >= 400) {
            return connection.getErrorStream();
        } else {
            return connection.getInputStream();
        }
    }

    private String getCharsetFromContentType(String contentType) {
        if (contentType == null) {
            return StandardCharsets.UTF_8.name();
        }

        // Extract charset from content-type header
        String[] parts = contentType.split(";");
        for (String part : parts) {
            if (part.trim().startsWith("charset=")) {
                return part.split("=", 2)[1].trim();
            }
        }

        return StandardCharsets.UTF_8.name();
    }

    private Map<String, String> extractResponseHeaders(HttpURLConnection connection) {
        Map<String, String> headers = new HashMap<>();
        connection.getHeaderFields().forEach((key, values) -> {
            if (key != null && values != null && !values.isEmpty()) {
                headers.put(key, values.get(0));
            }
        });
        return headers;
    }

    private RestResponseDto buildSuccessResponse(int statusCode, String body,
                                                 Map<String, String> headers, String contentType,
                                                 String requestUrl, long responseTime) {
        return RestResponseDto.builder()
                .statusCode(StatusCode.getByCode(statusCode))
                .body(body)
                .headers(headers)
                .contentType(contentType)
                .requestUrl(requestUrl)
                .responseTime(responseTime)
                .build();
    }

    private RestResponseDto buildErrorResponse(Exception e, String requestUrl, long startTime) {
        long responseTime = System.currentTimeMillis() - startTime;

        return RestResponseDto.builder()
                .statusCode(StatusCode.INTERNAL_SERVER_ERROR)
                .body("{\"error\":\"" + e.getMessage() + "\"}")
                .requestUrl(requestUrl)
                .responseTime(responseTime)
                .build();
    }

    private void closeConnection(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }

    private boolean hasRequestBody(HttpMethod method) {
        return method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.PATCH;
    }

    private String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn("UTF-8 encoding not supported, using default encoding");
            return URLEncoder.encode(value);
        }
    }
}