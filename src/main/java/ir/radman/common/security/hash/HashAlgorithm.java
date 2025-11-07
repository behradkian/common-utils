package ir.radman.common.security.hash;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Enum for supported hashing algorithms.
 *
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
@Getter
@AllArgsConstructor
public enum HashAlgorithm  implements Serializable {
    MD5("MD5", 128),
    SHA1("SHA-1", 160),
    SHA224("SHA-224", 224),
    SHA256("SHA-256", 256),
    SHA384("SHA-384", 384),
    SHA512("SHA-512", 512),
    SHA3_256("SHA3-256", 256),
    SHA3_512("SHA3-512", 512),
    HMAC_SHA256("HmacSHA256", 256),
    HMAC_SHA512("HmacSHA512", 512);

    private final String name;
    private final int bitLength;

    public boolean isHmac() {
        return name.startsWith("Hmac");
    }

    public boolean isSHA() {
        return name.split("-") [0].equals("SHA");
    }

    public boolean isCryptographicallySecure() {
        return !this.equals(MD5) && !this.equals(SHA1);
    }

}