package ir.baarmaan.utility.convertor;

import ir.baarmaan.utility.primitive.StringUtility;

import java.nio.charset.StandardCharsets;

public class UnicodeConvertor {

    private UnicodeConvertor() {
    }

    public static String convertTextToUTF16(String input){
        StringBuilder utf16Builder = new StringBuilder();

        for (int i = 0; i < input.length(); ) {
            int codePoint = input.codePointAt(i);
            utf16Builder.append(String.format("\\U%04X", codePoint));
            i += Character.charCount(codePoint);
        }

        return utf16Builder.toString().toLowerCase();
    }

    public static String convertTextToUTF16WithoutU(String input){
        StringBuilder utf16Builder = new StringBuilder();

        for (int i = 0; i < input.length(); ) {
            int codePoint = input.codePointAt(i);
            utf16Builder.append(String.format("%04X", codePoint));
            i += Character.charCount(codePoint);
        }

        return utf16Builder.toString().toLowerCase();
    }

    //todo complete
    public static String convertUTF16ToText(String utf16) {

        return null;
    }

}
