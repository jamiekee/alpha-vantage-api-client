package io.jamiekee.alphavantage.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * A simple utils class serializing and deserializing java objects to JSON using
 * Jackson's ObjectMapper.
 */
public class JsonParser {

  /**
   * Convert a JSON String to a java class.
   * @param json The String to perform deserialization.
   * @param obj The class in which the JSON represents.
   * @param <T> The type in which this method should return.
   * @return The object representation of the provided JSON.
   */
  public static <T> T toObject(String json, Class<T> obj)
      throws IOException {
    return JSON_PARSER.readValue(json, obj);
  }

  /**
   * Convert a java object to a JSON String.
   * @param obj The class in which should be serialized to JSON.
   * @return The JSON string representation of the object.
   */
  public static String toJson(Object obj)
      throws JsonProcessingException {
    return JSON_PARSER.writeValueAsString(obj);
  }

  /**
   * Add a custom deserializer for a class.
   * @param deserializationClass The class which the deserializer applies to.
   * @param deserializer The class which can deserialize the object.
   * @param <T> Used to dynamically type the JsonDeserializer instance.
   */
  public static <T> void addDeserializer(Class<T> deserializationClass, JsonDeserializer<T> deserializer) {
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addDeserializer(deserializationClass, deserializer);
    JSON_PARSER.registerModule(simpleModule);
  }

  private final static ObjectMapper JSON_PARSER = new ObjectMapper();

}
