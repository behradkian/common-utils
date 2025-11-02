package ir.radman.common.general.exception.base;

import ir.radman.common.general.enumeration.rest.StatusCode;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Base abstract runtime (unchecked) exception for all Radman services.
 * Provides:
 * - HTTP-like status codes via StatusCode enum
 * - logical errorCode (defaults to class name)
 * - metadata parameters (params)
 * - timestamp for traceability
 * <p>
 * This class is thread-safe and immutable.
 */
@Getter
public abstract class RadmanRuntimeException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final StatusCode status;
    private final String errorCode;
    private final Map<String, Object> params;
    private final Instant timestamp;

    public RadmanRuntimeException(String message) {
        this(StatusCode.HTTP_BAD_REQUEST, null, null, message, null);
    }

    public RadmanRuntimeException(String message, Throwable cause) {
        this(StatusCode.HTTP_BAD_REQUEST, null, null, message, cause);
    }

    public RadmanRuntimeException(StatusCode status, String message) {
        this(status, null, null, message, null);
    }

    public RadmanRuntimeException(StatusCode status, String errorCode, String message) {
        this(status, errorCode, null, message, null);
    }

    protected RadmanRuntimeException(StatusCode status, String errorCode, Map<String, Object> params, String message, Throwable cause) {
        super(message, cause);
        this.status = status != null ? status : StatusCode.HTTP_INTERNAL_ERROR;
        this.errorCode = (errorCode != null ? errorCode : this.getClass().getSimpleName());
        this.params = (params == null ? Collections.emptyMap() : Map.copyOf(params));
        this.timestamp = Instant.now();
    }

    public static Map<String, Object> paramsOf(Object... keyValues) {
        if (keyValues == null || keyValues.length == 0) return Collections.emptyMap();
        if (keyValues.length % 2 != 0)
            throw new IllegalArgumentException("paramsOf requires even number of arguments (key, value) pairs");
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            map.put(String.valueOf(keyValues[i]), keyValues[i + 1]);
        }
        return map;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("status", status.getCode());
        m.put("errorCode", errorCode);
        m.put("message", getMessage());
        m.put("timestamp", timestamp.toString());
        if (!params.isEmpty()) m.put("params", params);
        return Collections.unmodifiableMap(m);
    }

    @Override
    public String toString() {
        return "%s(status=%s, errorCode=%s, message=%s)"
                .formatted(getClass().getSimpleName(), status, errorCode, getMessage());
    }
}
