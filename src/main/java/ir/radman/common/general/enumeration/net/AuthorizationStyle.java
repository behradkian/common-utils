package ir.radman.common.general.enumeration.net;

public enum AuthorizationStyle {

    NO_AUTH(""),
    BEARER_TOKEN("Bearer "),
    BASIC_AUTH("Basic "),
    API_KEY("API_Key");

    private final String value;

    AuthorizationStyle(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
