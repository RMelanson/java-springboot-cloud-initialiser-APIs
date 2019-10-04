package springboot.ci.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class Convert_Service {

    public static int PRETTY_PRINT_INDENT_FACTOR = 4;
    public static String TEST_XML_STRING =
        "<?xml version=\"1.0\" ?><test attrib=\"moretest\">Turn this to JSON</test>";

    public static void main(String[] args) {
        String jsonString = xmlToJson(TEST_XML_STRING);
            System.out.println(jsonString);
    }
    
    public static String xmlToJson(String xmlString) {
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(xmlString);
            String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            return jsonPrettyPrintString;
        } catch (JSONException je) {
            return je.toString();
        }
    }
}