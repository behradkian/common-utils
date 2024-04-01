package com.panda.common.encryption;

import com.panda.common.encription.AESEncryption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class AESEncryptionTest {

    Logger LOGGER = LoggerFactory.getLogger(AESEncryptionTest.class.getName());;

    @Test
    void giveText_encryptCorrectly(){

        String firstText = "admin"; //824790cf9e17d0d75048f272b197655a
        String firstEncrypted = "824790cf9e17d0d75048f272b197655a"; //admin

        String encrypted = AESEncryption.encrypt(firstText);

        Assertions.assertEquals(firstEncrypted,encrypted);
    }

    @Test
    void giveEncryptData_decryptCorrectly(){

        String firstText = "admin"; //824790cf9e17d0d75048f272b197655a
        String firstEncrypted = "824790cf9e17d0d75048f272b197655a"; //admin

        String decrypted = AESEncryption.decrypt(firstEncrypted);

        Assertions.assertEquals(firstText,decrypted);
    }

    @Test
    void encryptAndDecryptBuilder(){

        String firstText = "admin"; //824790cf9e17d0d75048f272b197655a
        String encrypted = AESEncryption.encrypt(firstText);
        LOGGER.info("text is : " + firstText + ", encryptData is : " + encrypted);
        System.out.println("text is : " + firstText + "\nencryptData is : " + encrypted);

        String firstEncrypted = "824790cf9e17d0d75048f272b197655a"; //admin
        String decrypted = AESEncryption.decrypt(firstEncrypted);
        LOGGER.info("encryptData is : " + firstEncrypted + ", decryptData is : " + decrypted);
        System.out.println("encryptData is : " + firstEncrypted + "\ndecryptData is : " + decrypted);


    }
}
