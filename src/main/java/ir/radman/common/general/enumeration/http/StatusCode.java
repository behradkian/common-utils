package ir.radman.common.general.enumeration.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Enumeration representing standard HTTP status codes.
 * Provides helper methods for reverse lookup and category detection.
 *
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
@Getter
@AllArgsConstructor
public enum StatusCode implements Serializable {

    // --- 2xx: Success ---
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NON_AUTHORITATIVE_INFORMATION(203),
    NO_CONTENT(204),
    RESET_CONTENT(205),
    PARTIAL_CONTENT(206),

    // --- 3xx: Redirection ---
    MULTIPLE_CHOICES(300),
    MOVED_PERMANENTLY(301),
    FOUND(302),
    SEE_OTHER(303),
    NOT_MODIFIED(304),
    USE_PROXY(305),

    // --- 4xx: Client Error ---
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    PAYMENT_REQUIRED(402),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    NOT_ACCEPTABLE(406),
    PROXY_AUTHENTICATION_REQUIRED(407),
    REQUEST_TIMEOUT(408),
    CONFLICT(409),
    GONE(410),
    LENGTH_REQUIRED(411),
    PRECONDITION_FAILED(412),
    PAYLOAD_TOO_LARGE(413),
    URI_TOO_LONG(414),
    UNSUPPORTED_MEDIA_TYPE(415),

    // --- 5xx: Server Error ---
    INTERNAL_SERVER_ERROR(500),
    NOT_IMPLEMENTED(501),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    GATEWAY_TIMEOUT(504),
    HTTP_VERSION_NOT_SUPPORTED(505);

    private final int code;

    /**
     * Find StatusCode by integer value.
     *
     * @param code integer HTTP code
     * @return matching StatusCode or null if not found
     */
    public static StatusCode getByCode(int code) {
        return Arrays.stream(values())
                .filter(status -> status.code == code)
                .findFirst()
                .orElse(null);
    }

    /**
     * Check if this status code represents success (2xx).
     */
    public boolean isSuccess() {
        return code >= 200 && code < 300;
    }

    /**
     * Check if this status code represents client error (4xx).
     */
    public boolean isClientError() {
        return code >= 400 && code < 500;
    }

    /**
     * Check if this status code represents server error (5xx).
     */
    public boolean isServerError() {
        return code >= 500 && code < 600;
    }

    @Override
    public String toString() {
        return code + " " + name();
    }
}
