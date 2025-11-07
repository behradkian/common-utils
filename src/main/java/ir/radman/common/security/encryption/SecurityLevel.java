package ir.radman.common.security.encryption;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Security levels for cryptographic operations
 *
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
@Getter
@AllArgsConstructor
public enum SecurityLevel implements Serializable {
    STANDARD("STANDARD",128, 2048, 65536),    // NIST Level 1
    HIGH("HIGH",192, 3072, 131072),       // NIST Level 3
    ULTRA("ULTRA",256, 4096, 262144),      // NIST Level 5
    FUTURE_PROOF("FUTURE_PROOF",256, 8192, 524288); // Post-quantum preparation

    private final String value;
    private final int symmetricKeyBits;
    private final int rsaKeyBits;
    private final int pbkdf2Iterations;

    /**
     * Get security level based on requirements
     */
    public static SecurityLevel getByValue(String value) {
        return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElse(STANDARD);
    }
}