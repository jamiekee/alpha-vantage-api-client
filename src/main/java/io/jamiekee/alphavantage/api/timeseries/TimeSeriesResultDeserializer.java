package io.jamiekee.alphavantage.api.timeseries;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jamiekee.alphavantage.api.response.MetaData;
import io.jamiekee.alphavantage.api.utils.Regex;

public class TimeSeriesResultDeserializer extends JsonDeserializer<TimeSeriesResult> {

  @Override
  public TimeSeriesResult deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    TimeSeriesResult timeSeriesResult = new TimeSeriesResult();
    ObjectCodec oc = parser.getCodec();
    JsonNode node = oc.readTree(parser);
    try {
      Map<String, Object> sanitizedNodes = sanitizeNodeKeys(node.get(META_DATA_KEY));
      MetaData metaData = JSON_PARSER.readValue(
          JSON_PARSER.writeValueAsString(sanitizedNodes),
          MetaData.class
      );

      Map<Date, TimeSeries> timeSeriesMap = new HashMap<>();
      node.fields().forEachRemaining(nodeEntry -> {
        // ignore meta data, we want the time series data
        if (!nodeEntry.getKey().equals(META_DATA_KEY)) {
          nodeEntry.getValue().fields().forEachRemaining(timeSeriesEntry -> {
            try {
              timeSeriesMap.put(
                  DATE_PARSER.parse(timeSeriesEntry.getKey()),
                  getTimeSeries(sanitizeNodeKeys(timeSeriesEntry.getValue()))
              );
            }
            catch (IOException | ParseException e) {
              e.printStackTrace();
            }
          });
        }
      });
      timeSeriesResult.setMetaData(metaData);
      timeSeriesResult.setTimeSeries(timeSeriesMap);
    } catch (Throwable throwable) {
      System.out.println("Error when deserializing:");
      System.out.println(node.toString());
      throwable.printStackTrace();
    }
    return timeSeriesResult;
  }

  private TimeSeries getTimeSeries(Map<String, Object> sanitizeTimeSeriesEntries)
      throws IOException {
    return JSON_PARSER.readValue(
        JSON_PARSER.writeValueAsString(sanitizeTimeSeriesEntries),
        TimeSeries.class
    );
  }

  private Map<String, Object> sanitizeNodeKeys(JsonNode jsonNode) {
    Map<String, Object> sanitizedNodes = new HashMap<>();
    jsonNode.fields().forEachRemaining((node) ->
      sanitizedNodes.put(
        Regex.getMatch(REMOVE_NUMBER_REGEX, node.getKey()),
        node.getValue()
      )
    );
    return sanitizedNodes;
  }

  private static final ObjectMapper JSON_PARSER = new ObjectMapper();
  private static final String REMOVE_NUMBER_REGEX = "\\d*.\\s(.*)";
  private static final String META_DATA_KEY = "Meta Data";
  private static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-mm-dd");
}
