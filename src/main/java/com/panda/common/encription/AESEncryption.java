package com.panda.common.encription;

import com.panda.common.convert.GeneralConvertor;
import com.panda.common.general.exception.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AESEncryption {

    private static final Logger LOGGER = LoggerFactory.getLogger(AESEncryption.class.getName());
    private static final String DEFAULT_SECRET_KEY = "ThisIsAVeryVerySecretKey";
    private static final String ALGORITHM = "AES";
    private static Key secretKeySpec;

    private AESEncryption() {
    }

    public static String encrypt(String plainText, String secretKey) {

        try {
            secretKeySpec = generateKey(secretKey);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(1, secretKeySpec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return asHexString(encrypted);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                 BadPaddingException | InvalidKeyException e) {
            LOGGER.error("in AES encryption an error occurred, ExceptionType is : " + e.getClass().getSimpleName() + ", ExceptionMessage is : " + e.getMessage());
            throw new EncryptionException("in AES encryption an error occurred", e);
        }
    }

    public static String encrypt(String plainText) {
        return encrypt(plainText, null);
    }

    public static String decrypt(String encryptedString, String secretKey) {

        try {
            secretKeySpec = generateKey(secretKey);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(2, secretKeySpec);
            byte[] original = cipher.doFinal(toByteArray(encryptedString));
            return GeneralConvertor.convertByteArrayToStringUTF8(original);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                 BadPaddingException | InvalidKeyException e) {
            LOGGER.error("in AES decryption an error occurred, ExceptionType is : " + e.getClass().getSimpleName() + ", ExceptionMessage is : " + e.getMessage());
            throw new EncryptionException("in AES decryption an error occurred", e);
        }

    }

    public static String decrypt(String encryptedString) {
        return decrypt(encryptedString, null);
    }

    private static Key generateKey(String secretKey) throws NoSuchAlgorithmException {

        if (secretKey == null)
            secretKey = DEFAULT_SECRET_KEY;

        byte[] secretKeyBytes = GeneralConvertor.convertStringToByteArrayUTF8(secretKey);
        MessageDigest shaMessageDigest = MessageDigest.getInstance("SHA-1");
        secretKeyBytes = shaMessageDigest.digest(secretKeyBytes);
        secretKeyBytes = Arrays.copyOf(secretKeyBytes, 16);
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128);
        return new SecretKeySpec(secretKeyBytes, ALGORITHM);
    }

    private static String asHexString(byte[] buf) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);

        for (int i = 0; i < buf.length; ++i) {
            if ((buf[i] & 255) < 16) {
                strbuf.append("0");
            }

            strbuf.append(Long.toString((long) (buf[i] & 255), 16));
        }

        return strbuf.toString();
    }

    private static byte[] toByteArray(String hexString) {

        int sizeOfArray = hexString.length() >> 1;
        byte[] buf = new byte[sizeOfArray];

        for (int i = 0; i < sizeOfArray; ++i) {
            int index = i << 1;
            String l_digit = hexString.substring(index, index + 2);
            buf[i] = (byte) Integer.parseInt(l_digit, 16);
        }

        return buf;
    }

}
