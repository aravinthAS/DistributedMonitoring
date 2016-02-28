package dmon.core.commons.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Convert objects from and to json.
 */
public class JsonConverter {
    private static final Logger logger = LoggerFactory.getLogger(JsonConverter.class);
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Convert object to json string.
     *
     * @param obj object that will be serialized to string.
     * @return string with serialized object.
     */
    public static String objectToJsonString(Object obj) {
        String objToJson = null;
        try {
            objToJson = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return objToJson;
    }

    /**
     * Convert json string into object of defined type.
     *
     * @param jsonString string that will be deserialize into object of type T.
     * @param objClass class used to deserialize object.
     * @param <T> type to be used to deserialize.
     * @return object of type T.
     */
    public static <T> T jsonStringToObject(String jsonString, Class<T> objClass) {
        T object = null;
        try {
            object = mapper.readValue(jsonString, objClass);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return object;
    }

}
