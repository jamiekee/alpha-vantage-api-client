package io.jamiekee.alphavantage.api.timeseries;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.jamiekee.alphavantage.api.response.MetaData;
import io.jamiekee.alphavantage.api.utils.JsonParser;
import io.jamiekee.alphavantage.api.utils.Regex;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimeSeriesResultDeserializer extends JsonDeserializer<TimeSeriesResult> {

  @Override
  public TimeSeriesResult deserialize(com.fasterxml.jackson.core.JsonParser parser, DeserializationContext context)
      throws IOException {
    TimeSeriesResult timeSeriesResult = new TimeSeriesResult();
    ObjectCodec oc = parser.getCodec();
    JsonNode node = oc.readTree(parser);
    try {
      Map<String, Object> sanitizedNodes = sanitizeNodeKeys(node.get(META_DATA_KEY));
      MetaData metaData = JsonParser.toObject(
          JsonParser.toJson(sanitizedNodes),
          MetaData.class
      );

      Map<Date, TimeSeries> timeSeriesMap = new TreeMap<>();
      node.fields().forEachRemaining(nodeEntry -> {
        // ignore meta data, we want the time series data
        if (!nodeEntry.getKey().equals(META_DATA_KEY)) {
          nodeEntry.getValue().fields().forEachRemaining(timeSeriesEntry -> {
            try {
              timeSeriesMap.put(
                  parseDate(timeSeriesEntry.getKey()),
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

  private Date parseDate(String dateStr)
      throws ParseException {
    Date date = DATE_PARSER.parse(dateStr);
    if (dateStr.length() > 10)
      date = DATE_TIME_PARSER.parse(dateStr);
    return date;
  }

  private TimeSeries getTimeSeries(Map<String, Object> sanitizeTimeSeriesEntries)
      throws IOException {
    return JsonParser.toObject(
        JsonParser.toJson(sanitizeTimeSeriesEntries),
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

  private static final String REMOVE_NUMBER_REGEX = "\\d*.\\s(.*)";
  private static final String META_DATA_KEY = "Meta Data";
  private static final SimpleDateFormat DATE_TIME_PARSER = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
  private static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd");
}
