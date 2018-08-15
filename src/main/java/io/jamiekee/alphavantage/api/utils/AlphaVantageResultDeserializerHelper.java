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

public class AlphaVantageResultDeserializerHelper {

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

  public static <T> Map<Date, T> getDateObjectMap(JsonNode node, Class<T> resultObject) {
    Map<Date, T> dateObjMap = new TreeMap<>();
    node.fields().forEachRemaining(nodeEntry -> {
      // ignore meta data, we want the time series data
      if (!nodeEntry.getKey().equals(META_DATA_RESPONSE_KEY)) {
        nodeEntry.getValue().fields().forEachRemaining(timeSeriesEntry -> {
          try {
            dateObjMap.put(
                parseDate(timeSeriesEntry.getKey()),
                JsonParser.toObject(
                    JsonParser.toJson(sanitizeNodeKeys(timeSeriesEntry.getValue())),
                    resultObject
                )
            );
          }
          catch (IOException | ParseException e) {
            e.printStackTrace();
          }
        });
      }
    });
    return dateObjMap;
  }

  private static Date parseDate(String dateStr)
      throws ParseException {
    Date date = DATE_PARSER.parse(dateStr);
    if (dateStr.length() > 10)
      date = DATE_TIME_PARSER.parse(dateStr);
    return date;
  }

  private static final String REMOVE_NUMBER_REGEX = "\\d*.\\s(.*)";

  private static final SimpleDateFormat DATE_TIME_PARSER = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
  private static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd");
}
