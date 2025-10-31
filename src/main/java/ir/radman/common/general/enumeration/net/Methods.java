package ir.radman.common.general.enumeration.net;

public enum Methods {

    GET("GET"),
    /**
     * use for Create and return successful code is Http 201 code (CREATE)
     */
    POST("POST"),
    /**
     * use for Read and return successful code is Http 200 code (OK)
     */
    PUT("PUT"),
    /**
     * use for Update or Replace On successful update, return 200 (or 204 if not returning any content in the body) from a PUT. If using PUT for create, return HTTP status 201 on successful creation.
     */
    PATCH("PATCH"),
    /**
     * Uddate and Modify On successful update, return 200 (or 204 if not returning any content in the body)
     */
    DELETE("DELETE"),
    /**
     * Delete and return successful code is Http 200 code (OK)
     */
    FORM("FORM");

    private final String value;

    Methods(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
