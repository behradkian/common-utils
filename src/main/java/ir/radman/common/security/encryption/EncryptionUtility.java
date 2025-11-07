package ir.radman.common.security.encryption;

import ir.radman.common.general.exception.domain.EncryptionException;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Production-hardened EncryptionUtility.
 * Supports AES-GCM/CBC, PBKDF2, RSA, Hybrid encryption.
 * Uses EncryptionAlgorithm, KeyDerivationAlgorithm, SecurityLevel enums.
 *
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
public final class EncryptionUtility {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final Map<String, ThreadLocal<Cipher>> CIPHER_CACHE = new ConcurrentHashMap<>();
    public static final int MAX_DATA_SIZE = 10 * 1024 * 1024; // 10MB

    private EncryptionUtility() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== AES ====================
    public static String encryptAES(String plaintext, SecretKey key, EncryptionAlgorithm algorithm) {
        try {
            Objects.requireNonNull(plaintext);
            Objects.requireNonNull(key);
            Objects.requireNonNull(algorithm);
            byte[] iv = randomBytes(algorithm.getIvLength());
            Cipher cipher = getCipher(algorithm.getTransformation(), Cipher.ENCRYPT_MODE, key, iv, algorithm);
            byte[] cipherBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(concat(iv, cipherBytes));
        } catch (Exception e) {
            throw new EncryptionException("AES encryption failed", e);
        }
    }

    public static String decryptAES(String cipherBase64, SecretKey key, EncryptionAlgorithm algorithm) {
        try {
            Objects.requireNonNull(cipherBase64);
            Objects.requireNonNull(key);
            Objects.requireNonNull(algorithm);
            byte[] data = Base64.getDecoder().decode(cipherBase64);
            byte[][] ivAndCipher = split(data, algorithm.getIvLength());
            Cipher cipher = getCipher(algorithm.getTransformation(), Cipher.DECRYPT_MODE, key, ivAndCipher[0], algorithm);
            return new String(cipher.doFinal(ivAndCipher[1]), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new EncryptionException("AES decryption failed", e);
        }
    }

    // ==================== PBKDF2 ====================
    public static SecretKey deriveKeyFromPassword(String password, byte[] salt, KeyDerivationAlgorithm algorithm, SecurityLevel securityLevel) {
        Objects.requireNonNull(password);
        Objects.requireNonNull(salt);
        Objects.requireNonNull(algorithm);
        Objects.requireNonNull(securityLevel);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm.getAlgorithm());
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, securityLevel.getPbkdf2Iterations(), algorithm.getDefaultKeyLength());
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey key = new SecretKeySpec(tmp.getEncoded(), "AES");
            setZero(tmp.getEncoded());
            return key;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new EncryptionException("PBKDF2 key derivation failed", e);
        }
    }

    public static String encryptWithPassword(String plaintext, String password, KeyDerivationAlgorithm algorithm, SecurityLevel securityLevel) {
        Objects.requireNonNull(plaintext);
        Objects.requireNonNull(password);
        byte[] salt = randomBytes(16);
        SecretKey key = deriveKeyFromPassword(password, salt, algorithm, securityLevel);
        String cipher = encryptAES(plaintext, key, EncryptionAlgorithm.AES_GCM_NO_PADDING);
        setZero(key.getEncoded());
        byte[] cipherBytes = Base64.getDecoder().decode(cipher);
        return Base64.getEncoder().encodeToString(concat(salt, cipherBytes));
    }

    public static String decryptWithPassword(String cipherBase64, String password, KeyDerivationAlgorithm algorithm, SecurityLevel securityLevel) {
        Objects.requireNonNull(cipherBase64);
        Objects.requireNonNull(password);
        byte[] data = Base64.getDecoder().decode(cipherBase64);
        byte[] salt = Arrays.copyOfRange(data, 0, 16);
        byte[] cipherBytes = Arrays.copyOfRange(data, 16, data.length);
        SecretKey key = deriveKeyFromPassword(password, salt, algorithm, securityLevel);
        String cipherText = Base64.getEncoder().encodeToString(cipherBytes);
        String decrypted = decryptAES(cipherText, key, EncryptionAlgorithm.AES_GCM_NO_PADDING);
        setZero(key.getEncoded());
        return decrypted;
    }

    // ==================== RSA / Hybrid ====================
    public static byte[] encryptRSA(byte[] data, PublicKey key) {
        return rsaCrypto(data, key, Cipher.ENCRYPT_MODE);
    }

    public static byte[] decryptRSA(byte[] data, PrivateKey key) {
        return rsaCrypto(data, key, Cipher.DECRYPT_MODE);
    }

    private static byte[] rsaCrypto(byte[] data, Key key, int mode) {
        Objects.requireNonNull(data);
        Objects.requireNonNull(key);
        try {
            Cipher cipher = Cipher.getInstance(EncryptionAlgorithm.RSA_ECB_OAEP_WITH_SHA256_AND_MGF1_PADDING.getTransformation());
            cipher.init(mode, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new EncryptionException("RSA operation failed", e);
        }
    }

    public static byte[] encryptHybrid(String plaintext, PublicKey rsaPublicKey) {
        SecretKey aesKey = generateAESKey();
        String cipherText = encryptAES(plaintext, aesKey, EncryptionAlgorithm.AES_GCM_NO_PADDING);
        byte[] encryptedAesKey = encryptRSA(aesKey.getEncoded(), rsaPublicKey);
        setZero(aesKey.getEncoded());
        byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
        return ByteBuffer.allocate(4 + encryptedAesKey.length + cipherBytes.length)
                .putInt(encryptedAesKey.length)
                .put(encryptedAesKey)
                .put(cipherBytes)
                .array();
    }

    public static String decryptHybrid(byte[] hybridCipher, PrivateKey rsaPrivateKey) {
        ByteBuffer buffer = ByteBuffer.wrap(hybridCipher);
        int keyLen = buffer.getInt();
        byte[] encryptedAesKey = new byte[keyLen];
        buffer.get(encryptedAesKey);
        byte[] cipherBytes = new byte[buffer.remaining()];
        buffer.get(cipherBytes);
        byte[] aesKeyBytes = decryptRSA(encryptedAesKey, rsaPrivateKey);
        SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");
        setZero(aesKeyBytes);
        String decrypted = decryptAES(Base64.getEncoder().encodeToString(cipherBytes), aesKey, EncryptionAlgorithm.AES_GCM_NO_PADDING);
        setZero(aesKey.getEncoded());
        return decrypted;
    }

    // ==================== File Helpers ====================
    public static void encryptFileAES(File input, File output, SecretKey key, EncryptionAlgorithm algorithm) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(output);
        Objects.requireNonNull(key);
        if (input.length() > MAX_DATA_SIZE) {
            throw new EncryptionException("File size too large");
        }
        try (FileInputStream fis = new FileInputStream(input);
             FileOutputStream fos = new FileOutputStream(output)) {
            byte[] iv = randomBytes(algorithm.getIvLength());
            fos.write(iv);
            Cipher cipher = getCipher(algorithm.getTransformation(), Cipher.ENCRYPT_MODE, key, iv, algorithm);
            try (CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = fis.read(buffer)) != -1) cos.write(buffer, 0, read);
            }
        } catch (Exception e) {
            throw new EncryptionException("File encryption failed", e);
        }
    }

    public static void decryptFileAES(File input, File output, SecretKey key, EncryptionAlgorithm algorithm) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(output);
        Objects.requireNonNull(key);
        try (FileInputStream fis = new FileInputStream(input);
             FileOutputStream fos = new FileOutputStream(output)) {
            byte[] iv = fis.readNBytes(algorithm.getIvLength());
            Cipher cipher = getCipher(algorithm.getTransformation(), Cipher.DECRYPT_MODE, key, iv, algorithm);
            try (CipherInputStream cis = new CipherInputStream(fis, cipher)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = cis.read(buffer)) != -1) fos.write(buffer, 0, read);
            }
        } catch (Exception e) {
            throw new EncryptionException("File decryption failed", e);
        }
    }

    // ==================== Key Management ====================
    public static String keyToBase64(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static SecretKey base64ToAESKey(String base64Key) {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static Map<String, String> generateRSAKeyPairBase64(int keySize) {
        KeyPair kp = generateRSAKeyPair(keySize);
        Map<String, String> map = new HashMap<>();
        map.put("public", Base64.getEncoder().encodeToString(kp.getPublic().getEncoded()));
        map.put("private", Base64.getEncoder().encodeToString(kp.getPrivate().getEncoded()));
        return map;
    }

    // ==================== Helpers ====================
    private static byte[] randomBytes(int length) {
        byte[] b = new byte[length];
        SECURE_RANDOM.nextBytes(b);
        return b;
    }

    private static byte[] concat(byte[] arrayA, byte[] arrayB) {
        byte[] r = new byte[arrayA.length + arrayB.length];
        System.arraycopy(arrayA, 0, r, 0, arrayA.length);
        System.arraycopy(arrayB, 0, r, arrayA.length, arrayB.length);
        return r;
    }

    private static byte[][] split(byte[] data, int splitIndex) {
        return new byte[][]{Arrays.copyOfRange(data, 0, splitIndex),
                Arrays.copyOfRange(data, splitIndex, data.length)};
    }

    private static void setZero(byte[] array) {
        if (array != null) Arrays.fill(array, (byte) 0);
    }

    private static Cipher getCipher(String transformation, int mode, SecretKey key, byte[] iv, EncryptionAlgorithm algorithm)
            throws GeneralSecurityException {
        String cacheKey = transformation + ":" + mode;
        ThreadLocal<Cipher> tlCipher = CIPHER_CACHE.computeIfAbsent(cacheKey, k -> ThreadLocal.withInitial(() -> {
            try {
                return Cipher.getInstance(transformation);
            } catch (GeneralSecurityException e) {
                throw new EncryptionException("Cipher init failed", e);
            }
        }));
        Cipher cipher = tlCipher.get();
        AlgorithmParameterSpec spec = algorithm.requiresIV() ?
                (algorithm.isSymmetric() && algorithm.getTransformation().contains("GCM") ?
                        new GCMParameterSpec(128, iv) : new IvParameterSpec(iv)) : null;
        cipher.init(mode, key, spec);
        return cipher;
    }

    public static SecretKey generateAESKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, SECURE_RANDOM);
            return kg.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException("AES key generation failed", e);
        }
    }

    public static KeyPair generateRSAKeyPair(int keySize) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(keySize, SECURE_RANDOM);
            return kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException("RSA key generation failed", e);
        }
    }

    // ==================== Constant-time comparison ====================
    public static boolean slowEquals(byte[] a, byte[] b) {
        if (a == null || b == null || a.length != b.length) return false;
        int result = 0;
        for (int i = 0; i < a.length; i++) result |= a[i] ^ b[i];
        return result == 0;
    }

    public static boolean slowEquals(String a, String b) {
        if (a == null || b == null) return false;
        return slowEquals(a.getBytes(StandardCharsets.UTF_8), b.getBytes(StandardCharsets.UTF_8));
    }

    // ==================== Validation ====================
    public static boolean isValidBase64(String base64) {
        if (base64 == null || base64.trim().isEmpty()) {
            return false;
        }
        try {
            Base64.getDecoder().decode(base64);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isValidEncryptedData(String encryptedData) {
        if (!isValidBase64(encryptedData)) return false;
        try {
            return Base64.getDecoder().decode(encryptedData).length >= 16;
        } catch (Exception e) {
            return false;
        }
    }

    public static void cleanup() {
        CIPHER_CACHE.clear();
    }

}
