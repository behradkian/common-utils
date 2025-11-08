package ir.radman.common.tool.http;

import ir.radman.common.general.dto.http.HttpRequestDto;
import ir.radman.common.general.dto.http.HttpResponseDto;
import ir.radman.common.general.enumeration.http.StatusCode;
import ir.radman.common.general.exception.domain.RestCallException;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;

public final class HttpCall {

    private static final HttpClient DEFAULT_CLIENT = createDefaultClient(false);
    private static final HttpClient TRUST_ALL_CLIENT = createDefaultClient(true);

    private HttpCall() {}

    public static HttpResponseDto send(HttpRequestDto requestData) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(requestData.url()))
                    .timeout(Duration.ofSeconds(requestData.timeoutSeconds()));

            // Headers
            if (requestData.headers() != null) {
                requestData.headers().forEach(builder::header);
            }

            // Content-Type
            if (requestData.contentType() != null) {
                builder.header("Content-Type", requestData.contentType().getMimeType());
            }

            // Build body
            String body = "";
            if (requestData.isFormRequest()) {
                body = HttpFormEncoder.encode(requestData.formParams());
            } else if (requestData.body() != null) {
                body = requestData.body();
            }

            // Method
            switch (requestData.method()) {
                case GET -> builder.GET();
                case DELETE -> builder.DELETE();
                case POST, PUT ->
                        builder.method(requestData.method().name(), HttpRequest.BodyPublishers.ofString(body));
                default -> throw new IllegalArgumentException("Unsupported HTTP method: " + requestData.method());
            }

            HttpRequest request = builder.build();

            HttpClient client = selectClient(requestData.url(), requestData.trustAllSsl());
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return new HttpResponseDto(StatusCode.getByCode(response.statusCode()), response.body(), response.headers().map());

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RestCallException(null,"HTTP request failed: " + e.getMessage(), e);
        }
    }

    private static HttpClient selectClient(String url, boolean trustAll) {
        if (url.startsWith("https://")) {
            return trustAll ? TRUST_ALL_CLIENT : DEFAULT_CLIENT;
        }
        return DEFAULT_CLIENT;
    }

    private static HttpClient createDefaultClient(boolean trustAll) {
        HttpClient.Builder builder = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .version(HttpClient.Version.HTTP_2);

        if (trustAll) {
            try {
                SSLContext sslContext = createTrustAllSslContext();
                builder.sslContext(sslContext)
                        .sslParameters(new SSLParameters())
                        .hostnameVerifier((s, session) -> true);
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize trust-all SSL context", e);
            }
        }

        return builder.build();
    }

    private static SSLContext createTrustAllSslContext()
            throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        return sslContext;
    }
}
