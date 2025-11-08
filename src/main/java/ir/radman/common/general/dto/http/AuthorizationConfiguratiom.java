package ir.radman.common.general.dto.http;

import ir.radman.common.general.enumeration.http.AuthorizationType;
import lombok.Getter;


@Getter
public class AuthorizationConfiguratiom {
    private AuthorizationType authType;
    private String username;
    private String password;
    private String token;
    private String apiKey;
    private String apiKeyHeader;

    public static AuthorizationConfiguratiom basicAuth(String username, String password) {
        AuthorizationConfiguratiom config = new AuthorizationConfiguratiom();
        config.authType = AuthorizationType.BASIC_AUTH;
        config.username = username;
        config.password = password;
        return config;
    }

    public static AuthorizationConfiguratiom bearerToken(String token) {
        AuthorizationConfiguratiom config = new AuthorizationConfiguratiom();
        config.authType = AuthorizationType.BEARER_TOKEN;
        config.token = token;
        return config;
    }

    public static AuthorizationConfiguratiom apiKey(String apiKey, String headerName) {
        AuthorizationConfiguratiom config = new AuthorizationConfiguratiom();
        config.authType = AuthorizationType.API_KEY;
        config.apiKey = apiKey;
        config.apiKeyHeader = headerName;
        return config;
    }
}