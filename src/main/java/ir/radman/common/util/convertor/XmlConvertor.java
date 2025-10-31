package ir.radman.common.util.convertor;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class XmlConvertor {

    private XmlConvertor() {
    }

    public static Document convertXmlToDocument(String xml) {

        Document document;
        try {
            byte[] bytes = GeneralConvertor.convertStringToByteArray(xml, StandardCharsets.UTF_8);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(new ByteArrayInputStream(bytes));
            document.normalizeDocument();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            document = null;
        }
        return document;
    }

    public static JSONObject convertXmlToJsonObject(String xml) {
        return XML.toJSONObject(xml);
    }

    public static String convertXmlToJsonString(String xml) {
        return convertXmlToJsonObject(xml).toString(2);
    }

}
