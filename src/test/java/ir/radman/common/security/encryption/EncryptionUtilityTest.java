package ir.radman.common.security.encryption;

import org.junit.jupiter.api.*;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EncryptionUtilityTest {

    private static SecretKey aesKey;
    private static KeyPair rsaKeyPair;

    @BeforeAll
    static void setup() {
        aesKey = EncryptionUtility.generateAESKey();
        rsaKeyPair = EncryptionUtility.generateRSAKeyPair(2048);
    }

    @Test
    @Order(1)
    void testAESGCMEncryptionDecryption() {
        String plain = "Hello AES-GCM!";
        String cipher = EncryptionUtility.encryptAES(plain, aesKey, EncryptionAlgorithm.AES_GCM_NO_PADDING);
        String decrypted = EncryptionUtility.decryptAES(cipher, aesKey, EncryptionAlgorithm.AES_GCM_NO_PADDING);
        assertEquals(plain, decrypted);
    }

    @Test
    @Order(2)
    void testAESCBCEncryptionDecryption() {
        String plain = "Hello AES-CBC!";
        String cipher = EncryptionUtility.encryptAES(plain, aesKey, EncryptionAlgorithm.AES_CBC_PKCS5_PADDING);
        String decrypted = EncryptionUtility.decryptAES(cipher, aesKey, EncryptionAlgorithm.AES_CBC_PKCS5_PADDING);
        assertEquals(plain, decrypted);
    }

    @Test
    @Order(3)
    void testPBKDF2PasswordEncryption() {
        String password = "MySecretPassword";
        String plain = "Sensitive data";
        String encrypted = EncryptionUtility.encryptWithPassword(
                plain, password, KeyDerivationAlgorithm.PBKDF2_WITH_HMAC_SHA256, SecurityLevel.HIGH
        );
        String decrypted = EncryptionUtility.decryptWithPassword(
                encrypted, password, KeyDerivationAlgorithm.PBKDF2_WITH_HMAC_SHA256, SecurityLevel.HIGH
        );
        assertEquals(plain, decrypted);
    }

    @Test
    @Order(4)
    void testRSAEncryptionDecryption() {
        String plain = "RSA test";
        byte[] cipher = EncryptionUtility.encryptRSA(plain.getBytes(), rsaKeyPair.getPublic());
        byte[] decrypted = EncryptionUtility.decryptRSA(cipher, rsaKeyPair.getPrivate());
        assertEquals(plain, new String(decrypted));
    }

    @Test
    @Order(5)
    void testHybridEncryptionDecryption() {
        String plain = "Hybrid encryption test";
        byte[] cipher = EncryptionUtility.encryptHybrid(plain, rsaKeyPair.getPublic());
        String decrypted = EncryptionUtility.decryptHybrid(cipher, rsaKeyPair.getPrivate());
        assertEquals(plain, decrypted);
    }

    @Test
    @Order(6)
    void testKeyBase64Conversion() {
        String base64Key = EncryptionUtility.keyToBase64(aesKey);
        SecretKey decodedKey = EncryptionUtility.base64ToAESKey(base64Key);
        assertArrayEquals(aesKey.getEncoded(), decodedKey.getEncoded());
    }

    @Test
    @Order(7)
    void testRSAKeyPairBase64Generation() {
        Map<String, String> keys = EncryptionUtility.generateRSAKeyPairBase64(2048);
        assertTrue(keys.containsKey("public"));
        assertTrue(keys.containsKey("private"));
        assertTrue(EncryptionUtility.isValidBase64(keys.get("public")));
        assertTrue(EncryptionUtility.isValidBase64(keys.get("private")));
    }

    @Test
    @Order(8)
    void testSlowEquals() {
        byte[] a = {1, 2, 3};
        byte[] b = {1, 2, 3};
        byte[] c = {1, 2, 4};
        assertTrue(EncryptionUtility.slowEquals(a, b));
        assertFalse(EncryptionUtility.slowEquals(a, c));
        assertTrue(EncryptionUtility.slowEquals("abc", "abc"));
        assertFalse(EncryptionUtility.slowEquals("abc", "abd"));
    }

    @Test
    @Order(9)
    void testBase64Validation() {
        String valid = Base64.getEncoder().encodeToString("data".getBytes());
        String invalid = "not_base64!";
        assertTrue(EncryptionUtility.isValidBase64(valid));
        assertFalse(EncryptionUtility.isValidBase64(invalid));
    }

    @Test
    @Order(10)
    void testEncryptedDataValidation() {
        String valid = EncryptionUtility.encryptWithPassword("hello", "password",
                KeyDerivationAlgorithm.PBKDF2_WITH_HMAC_SHA256, SecurityLevel.HIGH);
        String invalid = Base64.getEncoder().encodeToString("short".getBytes());
        assertTrue(EncryptionUtility.isValidEncryptedData(valid));
        assertFalse(EncryptionUtility.isValidEncryptedData(invalid));
    }

    @Test
    @Order(11)
    void testEncryptDecryptFileAESGCM() throws IOException {
        // فایل موقت ورودی
        File inputFile = File.createTempFile("input", ".txt");
        inputFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write("This is a secret file content!");
        }

        // فایل خروجی برای رمزگذاری
        File encryptedFile = File.createTempFile("encrypted", ".bin");
        encryptedFile.deleteOnExit();

        // فایل خروجی برای بازگشایی
        File decryptedFile = File.createTempFile("decrypted", ".txt");
        decryptedFile.deleteOnExit();

        // رمزگذاری فایل
        EncryptionUtility.encryptFileAES(inputFile, encryptedFile, aesKey, EncryptionAlgorithm.AES_GCM_NO_PADDING);
        assertTrue(encryptedFile.length() > 0);

        // بازگشایی فایل
        EncryptionUtility.decryptFileAES(encryptedFile, decryptedFile, aesKey, EncryptionAlgorithm.AES_GCM_NO_PADDING);
        assertTrue(decryptedFile.length() > 0);

        // مقایسه محتوا
        String originalContent = Files.readString(inputFile.toPath());
        String decryptedContent = Files.readString(decryptedFile.toPath());
        assertEquals(originalContent, decryptedContent);
    }

    @AfterAll
    static void cleanup() {
        EncryptionUtility.cleanup();
    }
}
