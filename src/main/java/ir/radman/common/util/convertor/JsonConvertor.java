package ir.radman.common.util.convertor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class JsonConvertor {

    private static final Logger logger = LoggerFactory.getLogger(JsonConvertor.class.getName());

    private JsonConvertor() {
    }

    public static JSONObject object2Json(Object object) {
        return jsonString2ObjectOptional(object2JsonString(object), JSONObject.class, true, true);
    }

    public static String object2JsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error(String.format("an error occurred when class mapped to stringJson. exceptionType is : %s, message is : %s", e.getClass().getSimpleName(), e.getMessage()));
        }
        return null;
    }

    public static <T> T jsonString2Object(String stringJson, Class<T> targetClass) {
        return jsonString2ObjectOptional(stringJson, targetClass, true, false);
    }

    public static <T> T jsonString2ObjectOptional(String stringJson, Class<T> targetClass, boolean includeNull, boolean ignoreCaseSensitive) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            if (!includeNull)
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            if (ignoreCaseSensitive) {
                mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            }
            return mapper.readValue(stringJson, targetClass);

        } catch (JsonProcessingException e) {
            logger.error(String.format("an error occurred when stringJson mapped to class. exceptionType is : %s, message is : %s", e.getClass().getSimpleName(), e.getMessage()));
        }
        return null;
    }

    public static Map<String, Object> jsonString2Map(String stringJson) {
        return jsonString2ObjectOptional(stringJson, Map.class, true, true);
    }


}
