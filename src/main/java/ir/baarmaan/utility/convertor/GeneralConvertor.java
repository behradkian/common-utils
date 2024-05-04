package ir.baarmaan.utility.convertor;

import ir.baarmaan.general.exception.unchecked.NetworkRuntimeException;
import ir.baarmaan.utility.primitive.StringUtility;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralConvertor {

    private GeneralConvertor() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralConvertor.class.getName());

    public static URL convertStringToURL(String urlString) {

        if (StringUtility.isBlank(urlString)) {
            throw new NetworkRuntimeException("url is null");
        }

        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new NetworkRuntimeException("somethings wrong with the url : " + urlString, e);
        }
        return url;
    }

    public static String convertByteArrayToString(byte[] bytes, Charset charset) {
        return new String(bytes, charset);
    }

    public static String convertByteArrayToStringUTF8(byte[] bytes) {
        return convertByteArrayToString(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] convertStringToByteArray(String string, Charset charset) {
        return string.getBytes(charset);
    }

    public static byte[] convertStringToByteArrayUTF8(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] convertBase64ToByteArray(String imageBase64) {
        if (StringUtility.isBlank(imageBase64))
            return null;
        return Base64.decodeBase64(convertStringToByteArrayUTF8(imageBase64));
    }

    public static String convertByteArrayToBase64(byte[] bytes) {
        if (bytes != null && bytes.length > 0)
            return Base64.encodeBase64String(bytes);
        return null;
    }


}
