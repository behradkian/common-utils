package ir.radman.common.security.hash;

import ir.radman.common.general.exception.domain.HashException;

import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Production-hardened HashGenerator.
 * Improvements over the original:
 * - resets MessageDigest instances before use
 * - bounded, thread-safe LRU cache for Mac instances to avoid unbounded growth
 * - cache keys for Mac are SHA-256 of secret to avoid long keys
 * - parameterized PBKDF2 algorithm + sensible defaults
 * - consistent hex output (lowercase)
 * - clearer exception messages
 *
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
public final class HashGenerator {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int DEFAULT_SALT_LENGTH = 32;
    private static final int BUFFER_SIZE = 8192;
    private static final Map<String, ThreadLocal<MessageDigest>> DIGEST_CACHE = new ConcurrentHashMap<>();
    private static final MacLruCache MAC_CACHE = new MacLruCache(256);

    private HashGenerator() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    private static MessageDigest createMessageDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new HashException("Unsupported algorithm: " + algorithm, e);
        }
    }

    private static MessageDigest getMessageDigest(String algorithm) {
        return DIGEST_CACHE.computeIfAbsent(algorithm, alg -> ThreadLocal.withInitial(() -> createMessageDigest(alg))).get();
    }

    private static String secretToCacheKey(String secret) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(secret.getBytes(StandardCharsets.UTF_8));
            return bytesToHexLower(digest);
        } catch (NoSuchAlgorithmException e) {
            return Integer.toHexString(secret.hashCode());
        }
    }

    private static Mac createMac(String algorithm, byte[] secretKeyBytes) {
        try {
            Mac mac = Mac.getInstance(algorithm);
            SecretKeySpec keySpec = new SecretKeySpec(secretKeyBytes, algorithm);
            mac.init(keySpec);
            return mac;
        } catch (Exception e) {
            throw new HashException("Failed to initialize Mac: " + algorithm, e);
        }
    }

    private static Mac getMac(String algorithm, String secretKey) {
        Objects.requireNonNull(secretKey, "Secret key cannot be null");
        String cacheKey = algorithm + ":" + secretToCacheKey(secretKey);
        ThreadLocal<Mac> threadLocal = MAC_CACHE.computeIfAbsent(cacheKey, key -> ThreadLocal.withInitial(() -> createMac(algorithm, secretKey.getBytes(StandardCharsets.UTF_8))));
        return threadLocal.get();
    }

    public static String hash(String input, HashAlgorithm algorithm) {
        try {
            Objects.requireNonNull(input, "Input cannot be null");
            Objects.requireNonNull(algorithm, "Algorithm cannot be null");

            MessageDigest md = getMessageDigest(algorithm.getName());
            md.reset();
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHexLower(digest);
        } catch (Exception e) {
            throw new HashException("Failed to generate hash for algorithm: " + algorithm, e);
        }
    }

    public static String hashFile(Path path, HashAlgorithm algorithm) {
        if (path == null || !Files.exists(path)) {
            throw new HashException("File does not exist: " + path);
        }
        try (InputStream in = Files.newInputStream(path)) {
            Objects.requireNonNull(algorithm, "Algorithm cannot be null");
            MessageDigest messageDigest = getMessageDigest(algorithm.getName());
            messageDigest.reset();
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, bytesRead);
            }
            return bytesToHexLower(messageDigest.digest());
        } catch (Exception e) {
            throw new HashException("Failed to hash file: " + path, e);
        }
    }

    public static String hashMultiple(HashAlgorithm algorithm, String... inputs) {
        Objects.requireNonNull(algorithm, "Algorithm cannot be null");
        Objects.requireNonNull(inputs, "Inputs cannot be null");

        try {
            MessageDigest md = getMessageDigest(algorithm.getName());
            md.reset();
            for (String input : inputs) {
                if (input != null) {
                    md.update(input.getBytes(StandardCharsets.UTF_8));
                }
            }
            return bytesToHexLower(md.digest());
        } catch (Exception e) {
            throw new HashException("Failed to generate multiple hash", e);
        }
    }

    public static byte[] generateSalt() {
        return generateSalt(DEFAULT_SALT_LENGTH);
    }

    public static byte[] generateSalt(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Salt length must be positive");
        }
        byte[] salt = new byte[length];
        SECURE_RANDOM.nextBytes(salt);
        return salt;
    }

    public static String generateSaltHex() {
        return bytesToHexLower(generateSalt());
    }

    public static String generateSaltHex(int length) {
        return bytesToHexLower(generateSalt(length));
    }

    public static String hashWithSalt(String input, byte[] salt, HashAlgorithm algorithm) {
        Objects.requireNonNull(input, "Input cannot be null");
        Objects.requireNonNull(salt, "Salt cannot be null");
        Objects.requireNonNull(algorithm, "Algorithm cannot be null");

        try {
            MessageDigest md = getMessageDigest(algorithm.getName());
            md.reset();
            md.update(salt);
            md.update(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHexLower(md.digest());
        } catch (Exception e) {
            throw new HashException("Failed to generate hash with salt", e);
        }
    }

    public static String hashWithSaltAndPepper(String input, byte[] salt, String pepper, HashAlgorithm algorithm) {
        Objects.requireNonNull(pepper, "Pepper cannot be null");
        return hashWithSalt(input + pepper, salt, algorithm);
    }

    public static String sign(String message, String secretKey, HashAlgorithm algorithm) {
        Objects.requireNonNull(message, "Message cannot be null");
        Objects.requireNonNull(secretKey, "Secret key cannot be null");
        Objects.requireNonNull(algorithm, "Algorithm cannot be null");

        try {
            Mac mac = getMac(algorithm.getName(), secretKey);
            // mac.doFinal resets internal state after call
            byte[] out = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return bytesToHexLower(out);
        } catch (Exception e) {
            throw new HashException("Failed to sign message", e);
        }
    }

    public static String iterativeHash(String input, HashAlgorithm algorithm, int iterations) {
        Objects.requireNonNull(input, "Input cannot be null");
        Objects.requireNonNull(algorithm, "Algorithm cannot be null");

        if (iterations <= 0) {
            throw new IllegalArgumentException("Iterations must be positive");
        }

        String current = input;
        for (int i = 0; i < iterations; i++) {
            current = hash(current, algorithm);
        }
        return current;
    }

    public static boolean verify(String input, String expectedHash, HashAlgorithm algorithm) {
        Objects.requireNonNull(input, "Input cannot be null");
        Objects.requireNonNull(expectedHash, "Expected hash cannot be null");
        Objects.requireNonNull(algorithm, "Algorithm cannot be null");

        String generated = hash(input, algorithm);
        return slowEquals(generated, expectedHash);
    }

    public static boolean verifyPBKDF2(String password, String storedHash, byte[] salt, int iterations, int keyLength, PBKDF2Algorithm pbkdf2Algorithm) {
        Objects.requireNonNull(password, "Password cannot be null");
        Objects.requireNonNull(storedHash, "Stored hash cannot be null");
        Objects.requireNonNull(salt, "Salt cannot be null");
        Objects.requireNonNull(pbkdf2Algorithm, "PBKDF2 algorithm cannot be null");

        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(pbkdf2Algorithm.getValue());
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return slowEquals(bytesToHexLower(hash), storedHash);
        } catch (Exception e) {
            throw new HashException("PBKDF2 verification failed", e);
        }
    }

    public static String generatePBKDF2(String password, byte[] salt, int iterations, int keyLength, PBKDF2Algorithm pbkdf2Algorithm) {
        Objects.requireNonNull(password, "Password cannot be null");
        Objects.requireNonNull(salt, "Salt cannot be null");
        Objects.requireNonNull(pbkdf2Algorithm, "PBKDF2 algorithm cannot be null");

        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(pbkdf2Algorithm.getValue());
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return bytesToHexLower(hash);
        } catch (Exception e) {
            throw new HashException("Failed to generate PBKDF2 hash", e);
        }
    }

    public static String generateToken(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Token length must be positive");
        }
        byte[] token = new byte[length];
        SECURE_RANDOM.nextBytes(token);
        return bytesToHexLower(token);
    }

    public static boolean verifySignature(String message, String signature, String secretKey, HashAlgorithm algorithm) {
        Objects.requireNonNull(message, "Message cannot be null");
        Objects.requireNonNull(signature, "Signature cannot be null");
        Objects.requireNonNull(secretKey, "Secret key cannot be null");
        Objects.requireNonNull(algorithm, "Algorithm cannot be null");

        String computedSignature = sign(message, secretKey, algorithm);
        return slowEquals(computedSignature, signature);
    }

    public static boolean verifyFileHash(Path filePath, String expectedHash, HashAlgorithm algorithm) {
        Objects.requireNonNull(filePath, "File path cannot be null");
        Objects.requireNonNull(expectedHash, "Expected hash cannot be null");
        Objects.requireNonNull(algorithm, "Algorithm cannot be null");

        String fileHash = hashFile(filePath, algorithm);
        return slowEquals(fileHash, expectedHash);
    }

    public static String hashToBase64(String input, HashAlgorithm algorithm) {
        Objects.requireNonNull(input, "Input cannot be null");
        Objects.requireNonNull(algorithm, "Algorithm cannot be null");

        try {
            MessageDigest md = getMessageDigest(algorithm.getName());
            md.reset();
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (Exception e) {
            throw new HashException("Failed to generate base64 hash", e);
        }
    }

    public static int hashLength(HashAlgorithm algorithm) {
        Objects.requireNonNull(algorithm, "Algorithm cannot be null");

        try {
            MessageDigest md = createMessageDigest(algorithm.getName()); // new instance to avoid state issues
            return md.getDigestLength() * 8;
        } catch (Exception e) {
            return -1;
        }
    }

    public static List<String> getSupportedAlgorithms() {
        return Arrays.stream(HashAlgorithm.values()).map(Enum::name).toList();
    }

    public static Map<HashAlgorithm, String> multiAlgorithmHash(String input, HashAlgorithm... algorithms) {
        Objects.requireNonNull(input, "Input cannot be null");
        Objects.requireNonNull(algorithms, "Algorithms cannot be null");

        Map<HashAlgorithm, String> results = new LinkedHashMap<>();
        for (HashAlgorithm algo : algorithms) {
            results.put(algo, hash(input, algo));
        }
        return results;
    }

    public static boolean slowEquals(String a, String b) {
        if (a == null || b == null) {
            return false;
        }

        byte[] aBytes = a.getBytes(StandardCharsets.UTF_8);
        byte[] bBytes = b.getBytes(StandardCharsets.UTF_8);

        if (aBytes.length != bBytes.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < aBytes.length; i++) {
            result |= aBytes[i] ^ bBytes[i];
        }
        return result == 0;
    }

    public static void cleanup() {
        DIGEST_CACHE.clear();
        MAC_CACHE.clear();
    }

    public static String hashInputStream(InputStream inputStream, HashAlgorithm algorithm) {
        Objects.requireNonNull(inputStream, "InputStream cannot be null");
        Objects.requireNonNull(algorithm, "Algorithm cannot be null");

        try {
            MessageDigest messageDigest = getMessageDigest(algorithm.getName());
            messageDigest.reset();
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, bytesRead);
            }
            return bytesToHexLower(messageDigest.digest());
        } catch (Exception e) {
            throw new HashException("Failed to hash InputStream", e);
        }
    }

    private static String bytesToHexLower(byte[] bytes) {
        Objects.requireNonNull(bytes, "Bytes cannot be null");
        char[] hexChars = new char[bytes.length * 2];
        final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static final class MacLruCache {
        private final int maxEntries;
        private final Map<String, ThreadLocal<Mac>> map;

        MacLruCache(int maxEntries) {
            this.maxEntries = maxEntries;
            this.map = Collections.synchronizedMap(new LinkedHashMap<>() {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, ThreadLocal<Mac>> eldest) {
                    return size() > MacLruCache.this.maxEntries;
                }
            });
        }

        ThreadLocal<Mac> computeIfAbsent(String key, java.util.function.Function<String, ThreadLocal<Mac>> mappingFunction) {
            synchronized (map) {
                return map.computeIfAbsent(key, mappingFunction);
            }
        }

        void clear() {
            synchronized (map) {
                map.clear();
            }
        }

        void put(String key, ThreadLocal<Mac> value) {
            synchronized (map) {
                map.put(key, value);
            }
        }

        ThreadLocal<Mac> get(String key) {
            synchronized (map) {
                return map.get(key);
            }
        }

        ThreadLocal<Mac> remove(String key) {
            synchronized (map) {
                return map.remove(key);
            }
        }
    }
}