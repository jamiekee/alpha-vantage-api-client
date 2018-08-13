package io.jamiekee.alphavantage.api.timeseries;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.jamiekee.alphavantage.api.utils.JsonParser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static io.jamiekee.alphavantage.api.response.MetaData.META_DATA_RESPONSE_KEY;
import static io.jamiekee.alphavantage.api.utils.AlphaVantageResultDeserializerHelper.getMetaData;
import static io.jamiekee.alphavantage.api.utils.AlphaVantageResultDeserializerHelper.sanitizeNodeKeys;

/**
 * A custom deserializer for the TimeSeries API response.
 * It is a custom deserializer due to the complex structure of the response.
 * Firstly, field names are numbered, and are numbered differently between different
 * requests, so the fields need to be sanitized before deserialization.
 * Secondly, the field name for the actual data returned by the API i.e. the non
 * meta data section, is a dynamic string that changes between requests.
 */
public class TimeSeriesResultDeserializer extends JsonDeserializer<TimeSeriesResult> {

  @Override
  public TimeSeriesResult deserialize(
      com.fasterxml.jackson.core.JsonParser parser, DeserializationContext context)
      throws IOException {
    TimeSeriesResult timeSeriesResult = new TimeSeriesResult();
    ObjectCodec oc = parser.getCodec();
    JsonNode node = oc.readTree(parser);
    try {
      timeSeriesResult.setMetaData(getMetaData(node));
      timeSeriesResult.setTimeSeries(getDateObjectMap(node));
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

  private Map<Date, TimeSeries> getDateObjectMap(JsonNode node) {
    Map<Date, TimeSeries> timeSeriesMap = new TreeMap<>();
    node.fields().forEachRemaining(nodeEntry -> {
      // ignore meta data, we want the time series data
      if (!nodeEntry.getKey().equals(META_DATA_RESPONSE_KEY)) {
        nodeEntry.getValue().fields().forEachRemaining(timeSeriesEntry -> {
          try {
            timeSeriesMap.put(
                parseDate(timeSeriesEntry.getKey()),
                JsonParser.toObject(
                    JsonParser.toJson(sanitizeNodeKeys(timeSeriesEntry.getValue())),
                    TimeSeries.class
                )
            );
          }
          catch (IOException | ParseException e) {
            e.printStackTrace();
          }
        });
      }
    });
    return timeSeriesMap;
  }

  private static final SimpleDateFormat DATE_TIME_PARSER = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
  private static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd");
}
