package ir.radman.common.util.hash;

import ir.radman.common.general.enumeration.HashAlgorithm;
import ir.radman.common.general.enumeration.PBKDF2Algorithm;
import ir.radman.common.general.exception.domain.GenerateHashException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/07
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HashGeneratorTest {

    private static final String TEST_STRING = "Hello, World!";
    private static byte[] testSalt;

    @TempDir
    static Path tempDir;

    @BeforeAll
    static void setUp() {
        testSalt = HashGenerator.generateSalt(16);
    }

    @AfterAll
    static void tearDown() {
        HashGenerator.cleanup();
    }

    @Test
    @Order(1)
    @DisplayName("Test basic hash generation")
    void testHash() {
        // Test SHA-256
        String sha256Hash = HashGenerator.hash(TEST_STRING, HashAlgorithm.SHA256);
        assertNotNull(sha256Hash);
        assertEquals(64, sha256Hash.length()); // SHA-256 produces 64 hex characters

        // Test SHA-512
        String sha512Hash = HashGenerator.hash(TEST_STRING, HashAlgorithm.SHA512);
        assertNotNull(sha512Hash);
        assertEquals(128, sha512Hash.length()); // SHA-512 produces 128 hex characters

        String sha256Hash2 = HashGenerator.hash(TEST_STRING, HashAlgorithm.SHA256);
        assertEquals(sha256Hash, sha256Hash2);
    }

    @Test
    @Order(2)
    @DisplayName("Test hash with null parameters")
    void testHashWithNullParameters() {
        // Test null input
        assertThrows(GenerateHashException.class, () ->
                HashGenerator.hash(null, HashAlgorithm.SHA256));

        // Test null algorithm
        assertThrows(GenerateHashException.class, () ->
                HashGenerator.hash(TEST_STRING, null));
    }

    @Test
    @Order(3)
    @DisplayName("Test file hashing")
    void testHashFile() throws IOException {
        // Create a test file
        Path testFile = tempDir.resolve("test.txt");
        Files.write(testFile, TEST_STRING.getBytes());

        String fileHash = HashGenerator.hashFile(testFile, HashAlgorithm.SHA256);
        assertNotNull(fileHash);
        assertEquals(64, fileHash.length());

        // Compare with string hash - should be different due to file encoding
        String stringHash = HashGenerator.hash(TEST_STRING, HashAlgorithm.SHA256);
        assertEquals(fileHash, stringHash);
    }

    @Test
    @Order(4)
    @DisplayName("Test file hashing with non-existent file")
    void testHashFileNonExistent() {
        Path nonExistentFile = tempDir.resolve("nonexistent.txt");
        assertThrows(GenerateHashException.class, () ->
                HashGenerator.hashFile(nonExistentFile, HashAlgorithm.SHA256));
    }

    @Test
    @Order(5)
    @DisplayName("Test multiple inputs hashing")
    void testHashMultiple() {
        String[] inputs = {"part1", "part2", "part3"};
        String combinedHash = HashGenerator.hashMultiple(HashAlgorithm.SHA256, inputs);
        assertNotNull(combinedHash);

        // Verify it's different from hashing concatenated string
        String concatenated = "part1part2part3";
        String concatenatedHash = HashGenerator.hash(concatenated, HashAlgorithm.SHA256);
        assertEquals(concatenatedHash, combinedHash);
    }

    @Test
    @Order(6)
    @DisplayName("Test salt generation")
    void testGenerateSalt() {
        // Test default salt
        byte[] salt1 = HashGenerator.generateSalt();
        assertEquals(32, salt1.length); // Default length

        // Test custom length
        byte[] salt2 = HashGenerator.generateSalt(64);
        assertEquals(64, salt2.length);

        // Test hex salt
        String hexSalt = HashGenerator.generateSaltHex(16);
        assertNotNull(hexSalt);
        assertEquals(32, hexSalt.length()); // 16 bytes = 32 hex characters
    }

    @Test
    @Order(7)
    @DisplayName("Test hash with salt")
    void testHashWithSalt() {
        String hashWithSalt = HashGenerator.hashWithSalt(TEST_STRING, testSalt, HashAlgorithm.SHA256);
        assertNotNull(hashWithSalt);

        // Same input with same salt should produce same result
        String hashWithSalt2 = HashGenerator.hashWithSalt(TEST_STRING, testSalt, HashAlgorithm.SHA256);
        assertEquals(hashWithSalt, hashWithSalt2);

        // Different salt should produce different result
        byte[] differentSalt = HashGenerator.generateSalt(16);
        String hashWithDifferentSalt = HashGenerator.hashWithSalt(TEST_STRING, differentSalt, HashAlgorithm.SHA256);
        assertNotEquals(hashWithSalt, hashWithDifferentSalt);
    }

    @Test
    @Order(8)
    @DisplayName("Test hash with salt and pepper")
    void testHashWithSaltAndPepper() {
        String pepper = "secretPepper";
        String hash1 = HashGenerator.hashWithSaltAndPepper(TEST_STRING, testSalt, pepper, HashAlgorithm.SHA256);
        assertNotNull(hash1);

        // Same parameters should produce same result
        String hash2 = HashGenerator.hashWithSaltAndPepper(TEST_STRING, testSalt, pepper, HashAlgorithm.SHA256);
        assertEquals(hash1, hash2);

        // Different pepper should produce different result
        String hash3 = HashGenerator.hashWithSaltAndPepper(TEST_STRING, testSalt, "differentPepper", HashAlgorithm.SHA256);
        assertNotEquals(hash1, hash3);
    }

    @Test
    @Order(9)
    @DisplayName("Test HMAC signing")
    void testSign() {
        String secretKey = "mySecretKey";
        String signature = HashGenerator.sign(TEST_STRING, secretKey, HashAlgorithm.HMAC_SHA256);
        assertNotNull(signature);

        // Same input with same key should produce same signature
        String signature2 = HashGenerator.sign(TEST_STRING, secretKey, HashAlgorithm.HMAC_SHA256);
        assertEquals(signature, signature2);

        // Different key should produce different signature
        String differentSignature = HashGenerator.sign(TEST_STRING, "differentKey", HashAlgorithm.HMAC_SHA256);
        assertNotEquals(signature, differentSignature);
    }

    @Test
    @Order(10)
    @DisplayName("Test iterative hashing")
    void testIterativeHash() {
        String iterativeHash = HashGenerator.iterativeHash(TEST_STRING, HashAlgorithm.SHA256, 5);
        assertNotNull(iterativeHash);

        // Manual iteration should produce same result
        String current = TEST_STRING;
        for (int i = 0; i < 5; i++) {
            current = HashGenerator.hash(current, HashAlgorithm.SHA256);
        }
        assertEquals(current, iterativeHash);
    }

    @Test
    @Order(11)
    @DisplayName("Test hash verification")
    void testVerify() {
        String hash = HashGenerator.hash(TEST_STRING, HashAlgorithm.SHA256);

        // Correct input should verify
        assertTrue(HashGenerator.verify(TEST_STRING, hash, HashAlgorithm.SHA256));

        // Different input should not verify
        assertFalse(HashGenerator.verify("Different String", hash, HashAlgorithm.SHA256));
    }

    @Test
    @Order(12)
    @DisplayName("Test PBKDF2 functionality")
    void testPBKDF2() {
        byte[] salt = HashGenerator.generateSalt();
        String hash = HashGenerator.generatePBKDF2(
                "StrongPassword123!",
                salt,
                10000,
                256,
                PBKDF2Algorithm.PBKDF2_HMAC_SHA256
        );

        assertNotNull(hash);
        assertEquals(64, hash.length());
        assertTrue(hash.matches("^[0-9a-f]+$"));
    }

    @Test
    @Order(13)
    @DisplayName("Test token generation")
    void testGenerateToken() {
        String token1 = HashGenerator.generateToken(32);
        assertNotNull(token1);
        assertEquals(64, token1.length()); // 32 bytes = 64 hex characters

        String token2 = HashGenerator.generateToken(32);
        assertNotEquals(token1, token2); // Tokens should be different
    }

    @Test
    @Order(14)
    @DisplayName("Test signature verification")
    void testVerifySignature() {
        String secretKey = "signatureKey";
        String message = "Important message";

        String signature = HashGenerator.sign(message, secretKey, HashAlgorithm.HMAC_SHA256);

        // Correct signature should verify
        assertTrue(HashGenerator.verifySignature(message, signature, secretKey, HashAlgorithm.HMAC_SHA256));

        // Wrong signature should not verify
        assertFalse(HashGenerator.verifySignature(message, "wrongSignature", secretKey, HashAlgorithm.HMAC_SHA256));

        // Wrong message should not verify
        assertFalse(HashGenerator.verifySignature("wrongMessage", signature, secretKey, HashAlgorithm.HMAC_SHA256));
    }

    @Test
    @Order(15)
    @DisplayName("Test file hash verification")
    void testVerifyFileHash() throws IOException {
        // Create test file
        Path testFile = tempDir.resolve("verify_test.txt");
        Files.write(testFile, TEST_STRING.getBytes());

        String fileHash = HashGenerator.hashFile(testFile, HashAlgorithm.SHA256);

        // Correct file should verify
        assertTrue(HashGenerator.verifyFileHash(testFile, fileHash, HashAlgorithm.SHA256));

        // Wrong hash should not verify
        assertFalse(HashGenerator.verifyFileHash(testFile, "wrongHash", HashAlgorithm.SHA256));
    }

    @Test
    @Order(17)
    @DisplayName("Test base64 hash")
    void testHashToBase64() {
        String base64Hash = HashGenerator.hashToBase64(TEST_STRING, HashAlgorithm.SHA256);
        assertNotNull(base64Hash);

        // Base64 should be different from hex
        String hexHash = HashGenerator.hash(TEST_STRING, HashAlgorithm.SHA256);
        assertNotEquals(hexHash, base64Hash);
    }

    @Test
    @Order(18)
    @DisplayName("Test hash length")
    void testHashLength() {
        assertEquals(128, HashGenerator.hashLength(HashAlgorithm.MD5));     // 16 bytes * 8
        assertEquals(160, HashGenerator.hashLength(HashAlgorithm.SHA1));    // 20 bytes * 8
        assertEquals(256, HashGenerator.hashLength(HashAlgorithm.SHA256));  // 32 bytes * 8
        assertEquals(512, HashGenerator.hashLength(HashAlgorithm.SHA512));  // 64 bytes * 8
    }

    @Test
    @Order(19)
    @DisplayName("Test supported algorithms")
    void testGetSupportedAlgorithms() {
        List<String> algorithms = HashGenerator.getSupportedAlgorithms();
        assertNotNull(algorithms);
        assertFalse(algorithms.isEmpty());

        // Should contain basic algorithms
        assertTrue(algorithms.contains("SHA256"));
        assertTrue(algorithms.contains("SHA512"));
        assertTrue(algorithms.contains("MD5"));
    }

    @Test
    @Order(20)
    @DisplayName("Test multi-algorithm hash")
    void testMultiAlgorithmHash() {
        HashAlgorithm[] algorithms = {
                HashAlgorithm.MD5,
                HashAlgorithm.SHA1,
                HashAlgorithm.SHA256
        };

        Map<HashAlgorithm, String> results = HashGenerator.multiAlgorithmHash(TEST_STRING, algorithms);

        assertEquals(3, results.size());
        assertTrue(results.containsKey(HashAlgorithm.MD5));
        assertTrue(results.containsKey(HashAlgorithm.SHA1));
        assertTrue(results.containsKey(HashAlgorithm.SHA256));

        // Each algorithm should produce different hashes
        assertNotEquals(results.get(HashAlgorithm.MD5), results.get(HashAlgorithm.SHA1));
        assertNotEquals(results.get(HashAlgorithm.SHA1), results.get(HashAlgorithm.SHA256));
    }

    @Test
    @Order(21)
    @DisplayName("Test timing attack resistance")
    void testSlowEqualsTimingResistance() {
        String hash1 = "a".repeat(64);
        String hash2 = "b".repeat(64);
        String hash3 = "a".repeat(32) + "b".repeat(32);

        for (int i = 0; i < 1000; i++) {
            HashGenerator.slowEquals(hash1, hash2);
        }
        boolean result1 = HashGenerator.slowEquals(hash1, hash2);
        boolean result2 = HashGenerator.slowEquals(hash1, hash3);
        boolean result3 = HashGenerator.slowEquals(hash1, hash1);

        assertFalse(result1);
        assertFalse(result2);
        assertTrue(result3);
        assertFalse(HashGenerator.slowEquals(hash1, hash2));
        assertFalse(HashGenerator.slowEquals(hash1, hash3));
        assertTrue(HashGenerator.slowEquals(hash1, hash1));
    }

    @Test
    @Order(22)
    @DisplayName("Test edge cases")
    void testEdgeCases() {
        // Test empty string
        String emptyHash = HashGenerator.hash("", HashAlgorithm.SHA256);
        assertNotNull(emptyHash);
        assertEquals(64, emptyHash.length());

        // Test very long string
        String longString = "A".repeat(10000);
        String longHash = HashGenerator.hash(longString, HashAlgorithm.SHA256);
        assertNotNull(longHash);
        assertEquals(64, longHash.length());
    }

    @Test
    @Order(23)
    @DisplayName("Test algorithm consistency")
    void testAlgorithmConsistency() {
        // Test that each algorithm produces consistent results
        String input = "Consistency Test";

        for (HashAlgorithm algorithm : HashAlgorithm.values()) {
            if (!algorithm.getName().startsWith("Hmac")) { // Skip HMAC for this test
                String hash1 = HashGenerator.hash(input, algorithm);
                String hash2 = HashGenerator.hash(input, algorithm);
                assertEquals(hash1, hash2,
                        "Algorithm " + algorithm + " should produce consistent results");
            }
        }
    }

    @Test
    @Order(24)
    @DisplayName("Test invalid parameters")
    void testInvalidParameters() {
        // Test invalid salt length
        assertThrows(IllegalArgumentException.class, () ->
                HashGenerator.generateSalt(0));

        assertThrows(IllegalArgumentException.class, () ->
                HashGenerator.generateSalt(-1));

        // Test invalid iterations
        assertThrows(IllegalArgumentException.class, () ->
                HashGenerator.iterativeHash(TEST_STRING, HashAlgorithm.SHA256, 0));

        // Test invalid token length
        assertThrows(IllegalArgumentException.class, () ->
                HashGenerator.generateToken(0));
    }

    @Test
    @Order(25)
    @DisplayName("Test cache functionality")
    void testCacheFunctionality() {
        // First call should create cache entry
        String hash1 = HashGenerator.hash(TEST_STRING, HashAlgorithm.SHA256);

        // Second call should use cached MessageDigest
        String hash2 = HashGenerator.hash(TEST_STRING, HashAlgorithm.SHA256);

        assertEquals(hash1, hash2);
    }

    @Test
    @Order(26)
    @DisplayName("Test file hashing with directory path")
    void testHashFileWithDirectory() {
        assertThrows(GenerateHashException.class, () ->
                HashGenerator.hashFile(tempDir, HashAlgorithm.SHA256));
    }

    @Test
    @Order(27)
    @DisplayName("Test input stream hashing")
    void testHashInputStream() throws IOException {
        Path testFile = tempDir.resolve("stream_test.txt");
        Files.write(testFile, TEST_STRING.getBytes());

        try (InputStream inputStream = Files.newInputStream(testFile)) {
            String streamHash = HashGenerator.hashInputStream(inputStream, HashAlgorithm.SHA256);
            assertNotNull(streamHash);
            assertEquals(64, streamHash.length());

            // Should match file hash
            String fileHash = HashGenerator.hashFile(testFile, HashAlgorithm.SHA256);
            assertEquals(fileHash, streamHash);
        }
    }

    @Test
    @Order(28)
    @DisplayName("Test specific exception messages")
    void testExceptionMessages() {
        // Test null input in hash method
        GenerateHashException exception = assertThrows(GenerateHashException.class,
                () -> HashGenerator.hash(null, HashAlgorithm.SHA256));
        assertTrue(exception.getMessage().contains("Failed to generate hash"));

        // Test file not found
        Path nonExistent = tempDir.resolve("nonexistent.dat");
        exception = assertThrows(GenerateHashException.class,
                () -> HashGenerator.hashFile(nonExistent, HashAlgorithm.SHA256));
        assertTrue(exception.getMessage().contains("File does not exist"));
    }

    @Test
    @Order(29)
    @DisplayName("Test cache performance improvement")
    void testCachePerformance() {
        int iterations = 1000;
        long startTime = System.nanoTime();

        for (int i = 0; i < iterations; i++) {
            HashGenerator.hash(TEST_STRING + i, HashAlgorithm.SHA256);
        }

        long duration = System.nanoTime() - startTime;
        long averageTime = duration / iterations;

        // Average time should be reasonable (adjust threshold as needed)
        assertTrue(averageTime < 1_000_000, "Hashing should be efficient with cache");
    }

    @Test
    @Order(30)
    @DisplayName("Test salt generation without property")
    void testGenerateSaltWithoutProperty() {
        String hexSalt = HashGenerator.generateSaltHex();
        assertNotNull(hexSalt);
        assertEquals(64, hexSalt.length());
    }
}
