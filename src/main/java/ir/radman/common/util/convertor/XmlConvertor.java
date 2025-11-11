package ir.radman.common.util.convertor;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

public final class XmlConvertor {

    private XmlConvertor() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static Document convertXmlToDocument(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setExpandEntityReferences(false);
            factory.setNamespaceAware(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));
            doc.normalizeDocument();
            return doc;

        } catch (Exception e) {
            throw new RuntimeException("Error while parsing XML to Document", e);
        }
    }

    public static JSONObject convertXmlToJsonObject(String xml) {
        try {
            return XML.toJSONObject(xml);
        } catch (JSONException e) {
            throw new RuntimeException("Invalid XML format", e);
        }
    }

    public static String convertXmlToJsonString(String xml) {
        return convertXmlToJsonObject(xml).toString(2);
    }

    public static <T> void toXmlFile(T object, File file) {
        if (object == null || file == null)
            throw new IllegalArgumentException("Object and file cannot be null");

        try (OutputStream os = new FileOutputStream(file)) {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, os);
        } catch (Exception e) {
            throw new RuntimeException("Error while writing XML to file", e);
        }
    }

    public static <T> T fromXmlFile(File file, Class<T> type) {
        if (file == null || !file.exists())
            throw new IllegalArgumentException("File does not exist");

        try (InputStream is = new FileInputStream(file)) {
            JAXBContext context = JAXBContext.newInstance(type);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return type.cast(unmarshaller.unmarshal(is));
        } catch (Exception e) {
            throw new RuntimeException("Error while reading XML from file", e);
        }
    }

    public static String convertDocumentToXmlString(Document document) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.toString();

        } catch (TransformerException e) {
            throw new RuntimeException("Error while converting Document to XML string", e);
        }
    }

    public static String convertXmlToJsonString(String xml, int indentFactor) {
        return convertXmlToJsonObject(xml).toString(indentFactor);
    }


    public static String convertJsonToXml(JSONObject jsonObject, String rootName) {
        try {
            String xml = XML.toString(jsonObject, rootName);
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xml;
        } catch (Exception e) {
            throw new RuntimeException("Error while converting JSON to XML", e);
        }
    }

    public static String convertJsonToXml(String json, String rootName) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return convertJsonToXml(jsonObject, rootName);
        } catch (JSONException e) {
            throw new RuntimeException("Invalid JSON format", e);
        }
    }

    public static <T> String convertObjectToXml(T object) {
        if (object == null)
            throw new IllegalArgumentException("Object cannot be null");

        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);
            return writer.toString();

        } catch (JAXBException e) {
            throw new RuntimeException("Error while converting Object to XML", e);
        }
    }

    public static <T> T convertXmlToObject(String xml, Class<T> type) {
        if (xml == null || xml.isBlank())
            throw new IllegalArgumentException("XML string cannot be null or empty");

        try {
            JAXBContext context = JAXBContext.newInstance(type);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return type.cast(unmarshaller.unmarshal(new StringReader(xml)));

        } catch (JAXBException e) {
            throw new RuntimeException("Error while converting XML to Object", e);
        }
    }

    public static boolean validateXmlWithXsd(String xmlPath, String xsdPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            return true;

        } catch (Exception e) {
            return false;
        }
    }

}
