package ir.radman.common.security.encryption;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Encryption algorithms enumeration
 *
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
@Getter
@AllArgsConstructor
public enum EncryptionAlgorithm  implements Serializable {
    AES_GCM_NO_PADDING("AES/GCM/NoPadding", 128, "AES", 12), // 96 bits IV for GCM
    AES_CBC_PKCS5_PADDING("AES/CBC/PKCS5Padding", 128, "AES", 16), // 128 bits IV for CBC
    RSA_ECB_OAEP_WITH_SHA256_AND_MGF1_PADDING("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", 2048, "RSA", 0), // No IV
    RSA_ECB_PKCS1_PADDING("RSA/ECB/PKCS1Padding", 2048, "RSA", 0), // No IV
    CHACHA20_POLY1305("ChaCha20-Poly1305", 256, "ChaCha20", 12); // 96 bits IV for ChaCha20

    private final String transformation;
    private final int defaultKeySize;
    private final String algorithm;
    private final int ivLength;

    public boolean isSymmetric() {
        return "AES".equals(algorithm) || "ChaCha20".equals(algorithm);
    }

    public boolean isAsymmetric() {
        return "RSA".equals(algorithm);
    }

    public boolean requiresIV() {
        return !transformation.contains("ECB");
    }
}