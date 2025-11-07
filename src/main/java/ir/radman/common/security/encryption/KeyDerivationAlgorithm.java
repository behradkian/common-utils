package ir.radman.common.security.encryption;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Key derivation algorithms enumeration
 * 
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
@Getter
@AllArgsConstructor
public enum KeyDerivationAlgorithm  implements Serializable {
    PBKDF2_WITH_HMAC_SHA256("PBKDF2WithHmacSHA256", 100000, 256),
    PBKDF2_WITH_HMAC_SHA512("PBKDF2WithHmacSHA512", 100000, 512),
    SCRYPT("SCRYPT", 16384, 256);

    private final String algorithm;
    private final int defaultIterations;
    private final int defaultKeyLength;

}