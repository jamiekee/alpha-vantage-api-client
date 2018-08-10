package timeseries;

import com.fasterxml.jackson.core.JsonParser;
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
import response.MetaData;
import utils.Regex;

public class TimeSeriesResultDeserializer extends JsonDeserializer<TimeSeriesResult> {

  @Override
  public TimeSeriesResult deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectCodec oc = parser.getCodec();
    JsonNode node = oc.readTree(parser);

    Map<String, Object> sanitizedNodes = sanitizeNodeKeys(node.get(META_DATA_KEY));
    MetaData metaData = objectMapper.readValue(
        objectMapper.writeValueAsString(sanitizedNodes),
        MetaData.class
    );

    Map<Date, TimeSeries> timeSeriesMap = new HashMap<>();
    node.fields().forEachRemaining(nodeEntry -> {
      // ignore meta data, we want the time series data
      if (!nodeEntry.getKey().equals(META_DATA_KEY)) {
        nodeEntry.getValue().fields().forEachRemaining(timeSeriesEntry -> {
          try {
            Date date = DATE_PARSER.parse(timeSeriesEntry.getKey());
            Map<String, Object> sanitizeTimeSeriesEntries =
                sanitizeNodeKeys(timeSeriesEntry.getValue());
            TimeSeries timeSeries = objectMapper.readValue(
                objectMapper.writeValueAsString(sanitizeTimeSeriesEntries),
                TimeSeries.class
            );
            timeSeriesMap.put(date, timeSeries);
          }
          catch (IOException | ParseException e) {
            e.printStackTrace();
          }
        });
      }
    });
    TimeSeriesResult timeSeriesResult = new TimeSeriesResult();
    timeSeriesResult.setMetaData(metaData);
    timeSeriesResult.setTimeSeries(timeSeriesMap);
    return timeSeriesResult;
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
  private static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-mm-dd");
}
