package ir.baarmaan.utility.convertor;

import org.json.JSONObject;
import org.json.XML;

public class XmlConvertor {

    private XmlConvertor() {
    }

    public static JSONObject convertXmlToJsonObject(String xml){
        return XML.toJSONObject(xml);
    }

    public static String convertXmlToJsonString(String xml){
        return convertXmlToJsonObject(xml).toString(2);
    }
}
