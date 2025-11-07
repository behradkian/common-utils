package ir.radman.common.security.hash;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
@Getter
@AllArgsConstructor
public enum PBKDF2Algorithm implements Serializable {
    PBKDF2_HMAC_SHA1("PBKDF2WithHmacSHA1"),
    PBKDF2_HMAC_SHA224("PBKDF2WithHmacSHA224"),
    PBKDF2_HMAC_SHA256("PBKDF2WithHmacSHA256"),
    PBKDF2_HMAC_SHA384("PBKDF2WithHmacSHA384"),
    PBKDF2_HMAC_SHA512("PBKDF2WithHmacSHA512");

    private final String value;

    public static PBKDF2Algorithm getByValue(String value) {
        return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElse(null);
    }
}