package ir.radman.common.util.convertor;

public class UnicodeConvertor {

    private UnicodeConvertor() {
    }

    public static String convertTextToUTF16(String input) {
        StringBuilder utf16Builder = new StringBuilder();

        for (int i = 0; i < input.length(); ) {
            int codePoint = input.codePointAt(i);
            utf16Builder.append(String.format("\\U%04X", codePoint));
            i += Character.charCount(codePoint);
        }

        return utf16Builder.toString().toLowerCase();
    }

    public static String convertTextToUTF16WithoutU(String input) {
        StringBuilder utf16Builder = new StringBuilder();

        for (int i = 0; i < input.length(); ) {
            int codePoint = input.codePointAt(i);
            utf16Builder.append(String.format("%04X", codePoint));
            i += Character.charCount(codePoint);
        }

        return utf16Builder.toString().toLowerCase();
    }

    public static String convertUTF16ToText(String utf16String) {

        if (utf16String == null || utf16String.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        int length = utf16String.length();

        if (length % 6 != 0) {
            throw new IllegalArgumentException("Invalid UTF-16 representation format");
        }

        for (int i = 0; i < length; i += 6) {
            String unicodeEscape = utf16String.substring(i, i + 6);

            if (!unicodeEscape.startsWith("\\u") || unicodeEscape.length() != 6) {
                throw new IllegalArgumentException("Invalid UTF-16 escape sequence: " + unicodeEscape);
            }

            String hexValue = unicodeEscape.substring(2);
            int codePoint = Integer.parseInt(hexValue, 16);

            result.append((char) codePoint);
        }

        return result.toString();
    }

    public static String convertUTF16WithoutUToText(String utf16String) {

        if (utf16String == null || utf16String.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        int length = utf16String.length();

        if (length % 4 != 0) {
            throw new IllegalArgumentException("Invalid UTF-16 representation format");
        }

        for (int i = 0; i < length; i += 4) {
            String unicodeEscape = utf16String.substring(i, i + 4);

            if (unicodeEscape.length() != 4) {
                throw new IllegalArgumentException("Invalid UTF-16 escape sequence: " + unicodeEscape);
            }

            int codePoint = Integer.parseInt(unicodeEscape, 16);

            result.append((char) codePoint);
        }

        return result.toString();
    }

    public static String convertTextToDecimal(String input) {

        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder unicodeDecimalString = new StringBuilder();

        for (char ch : input.toCharArray()) {
            if (ch == ' ') {
                unicodeDecimalString.append(" ");
            } else {
                int unicodeDecimal = ch;
                unicodeDecimalString.append(String.format("%05d", unicodeDecimal));
            }
        }

        // حذف فضای خالی اضافی در انتهای رشته
        return unicodeDecimalString.toString().trim();
    }

    public static String convertDecimalToText(String unicodeDecimalString) {

        if (unicodeDecimalString == null || unicodeDecimalString.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        String[] parts = unicodeDecimalString.split(" ");

        for (String part : parts) {

            if (part.length() % 5 == 0) {
                for (int i = 0; i < part.length(); ) {

                    String code = part.substring(i, i + 5);
                    int unicodeDecimal = Integer.valueOf(code);
                    i = i + 5;
                    result.append((char) unicodeDecimal);
                }
                result.append(" ");

            } else
                break;
        }
        return result.toString();
    }

}
