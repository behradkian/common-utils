package ir.radman.common.general.exception;

import ir.radman.common.util.primitive.StringUtility;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Base abstract application exception.
 * - Unchecked (extends RuntimeException)
 * - Carries a numeric HTTP-like status code, an optional errorCode, and a params map.
 */
@Getter
public abstract class RadmanRuntimeException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final int status; // HTTP-style status code (e.g. 400, 404, 500)
    private final String errorCode; // logical error code (e.g. "USER_NOT_FOUND")
    private final Map<String, Object> params; // additional metadata
    private final Instant timestamp;


    public RadmanRuntimeException(String message) {
        super(message);
        status = 400;
        errorCode = this.getClass().getSimpleName();
        timestamp = Instant.now();
        params = new HashMap<>();
    }

    public RadmanRuntimeException(String message, Throwable cause) {
        super(message, cause);
        errorCode = this.getClass().getSimpleName();
        status = 400;
        timestamp = Instant.now();
        params = new HashMap<>();

    }

    protected RadmanRuntimeException(int status, String errorCode, Map<String, Object> params, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.errorCode = errorCode;
        if (params == null) {
            this.params = Collections.emptyMap();
        } else {
            this.params = Map.copyOf(params);
        }
        this.timestamp = Instant.now();
    }

    protected RadmanRuntimeException(int status, String errorCode, Map<String, Object> params, String message) {
        this(status, errorCode, params, message, null);
    }

    // convenience factory for subclasses
    protected static Map<String, Object> paramsOf(Object... keyValues) {
        if (keyValues == null || keyValues.length == 0) return Collections.emptyMap();
        if (keyValues.length % 2 != 0) {
            throw new IllegalArgumentException("paramsOf requires even number of arguments (key, value) pairs");
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            Object k = keyValues[i];
            Object v = keyValues[i + 1];
            if (k == null) continue;
            map.put(String.valueOf(k), v);
        }
        return map;
    }

    /**
     * Represent exception as a map suitable for serializing to JSON in HTTP responses.
     */
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("status", status);
        m.put("errorCode", errorCode);
        m.put("message", getMessage());
        m.put("timestamp", timestamp.toString());
        if (!params.isEmpty()) m.put("params", params);
        return Collections.unmodifiableMap(m);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RadmanRuntimeException that = (RadmanRuntimeException) o;
        return status == that.status &&
                Objects.equals(errorCode, that.errorCode) &&
                Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(params, that.params) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, errorCode, getMessage(), params, timestamp);
    }

    @Override
    public String toString() {
        return StringUtility.toString(this);
    }
}
