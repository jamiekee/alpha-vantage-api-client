package io.jamiekee.alphavantage.api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import io.jamiekee.alphavantage.api.response.MetaData;
import io.jamiekee.alphavantage.api.timeseries.TimeSeries;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static io.jamiekee.alphavantage.api.response.MetaData.META_DATA_RESPONSE_KEY;

public class AlphaVantageResultDeserializerHelper<T> {

  public static Map<String, Object> sanitizeNodeKeys(JsonNode jsonNode) {
    Map<String, Object> sanitizedNodes = new HashMap<>();
    jsonNode.fields().forEachRemaining((node) ->
        sanitizedNodes.put(
            Regex.getMatch(REMOVE_NUMBER_REGEX, node.getKey()),
            node.getValue()
        )
    );
    return sanitizedNodes;
  }

  public static MetaData getMetaData(JsonNode node)
      throws IOException {
    Map<String, Object> sanitizedNodes = sanitizeNodeKeys(
        node.get(META_DATA_RESPONSE_KEY)
    );
    return JsonParser.toObject(
        JsonParser.toJson(sanitizedNodes),
        MetaData.class
    );
  }

  private static final String REMOVE_NUMBER_REGEX = "\\d*.\\s(.*)";
}
