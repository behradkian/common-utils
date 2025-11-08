package ir.radman.common.general.enumeration.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum AuthorizationType implements Serializable {
    NO_AUTH(""),
    BEARER_TOKEN("Bearer "),
    BASIC_AUTH("Basic "),
    API_KEY("ApiKey "),
    DIGEST_AUTH("Digest "),
    AWS_SIGNATURE("AWS4-HMAC-SHA256 "),
    OAUTH1("OAuth "),
    OAUTH2("Bearer "),
    JWT_BEARER("Bearer "),
    CUSTOM("Custom ");

    private final String value;
}