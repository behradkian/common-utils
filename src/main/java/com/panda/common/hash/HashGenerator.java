package com.panda.common.hash;

import com.panda.common.convert.GeneralConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(HashGenerator.class.getName());
    private static final String MDF = "MD5";
    private static final String SHA256 = "SHA-256";
    private static final String SHA1 = "SHA-1";

    public static String generateMD5(final String plainText) {

        try {
            byte[] plainTextByteArray = GeneralConvertor.convertStringToByteArrayUTF8(plainText);
            final MessageDigest messageDigest = MessageDigest.getInstance(MDF);
            messageDigest.reset();
            messageDigest.update(plainTextByteArray);
            final byte[] digestBytesArray = messageDigest.digest();
            final BigInteger bigInt = new BigInteger(1, digestBytesArray);
            String hashText = bigInt.toString(16);

            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;

        } catch (Exception e) {
            LOGGER.error("exception occurred in generateToMD5, exceptionType is : " + e.getClass().getSimpleName() + ", exceptionMessage is : " + e.getMessage());
        }
        return null;

    }

    private static String generateSHA1(final String plainText) {

        try {
            byte[] plainTextByteArray = GeneralConvertor.convertStringToByteArrayUTF8(plainText);
            final MessageDigest messageDigest = MessageDigest.getInstance(SHA1);
            messageDigest.reset();
            messageDigest.update(plainTextByteArray);
            return new BigInteger(1, messageDigest.digest()).toString(16);

        } catch (Exception e) {
            LOGGER.error("exception occurred in generateToSHA1, exceptionType is : " + e.getClass().getSimpleName() + ", exceptionMessage is : " + e.getMessage());
        }
        return null;
    }

    public static String generateSHA256(final String plainText) {

        try {
            byte[] plainTextByteArray = GeneralConvertor.convertStringToByteArrayUTF8(plainText);
            final MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
            final byte[] hash = messageDigest.digest(plainTextByteArray);
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            LOGGER.error("exception occurred in generateToSHA256, exceptionType is : " + e.getClass().getSimpleName() + ", exceptionMessage is : " + e.getMessage());
        }
        return null;
    }

}
