package ir.radman.common.general.dto.http;

import ir.radman.common.general.enumeration.http.AuthorizationType;
import lombok.*;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationConfiguration {
    private AuthorizationType authorizationType;
    private String username;
    private String password;
    private String token;
    private String apiKey;
    private String apiKeyHeader;

    public static AuthorizationConfiguration basicAuth(String username, String password) {
        var config = new AuthorizationConfiguration();
        config.authorizationType = AuthorizationType.BASIC_AUTH;
        config.username = username;
        config.password = password;
        return config;
    }

    public static AuthorizationConfiguration bearerToken(String token) {
        var config = new AuthorizationConfiguration();
        config.authorizationType = AuthorizationType.BEARER_TOKEN;
        config.token = token;
        return config;
    }

    public static AuthorizationConfiguration apiKey(String apiKey, String headerName) {
        var config = new AuthorizationConfiguration();
        config.authorizationType = AuthorizationType.API_KEY;
        config.apiKey = apiKey;
        config.apiKeyHeader = headerName;
        return config;
    }
}