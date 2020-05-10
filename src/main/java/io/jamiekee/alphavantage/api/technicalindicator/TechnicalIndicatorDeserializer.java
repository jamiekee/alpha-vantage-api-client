package io.jamiekee.alphavantage.api.technicalindicator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.jamiekee.alphavantage.api.response.MetaData.META_DATA_RESPONSE_KEY;
import static io.jamiekee.alphavantage.api.utils.AlphaVantageResultDeserializerHelper.getMetaData;

public class TechnicalIndicatorDeserializer extends JsonDeserializer<TechnicalIndicatorResult> {

  @Override
  public TechnicalIndicatorResult deserialize(JsonParser parser,
                                              DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    TechnicalIndicatorResult technicalIndicatorResult = new TechnicalIndicatorResult();
    ObjectCodec oc = parser.getCodec();
    JsonNode node = oc.readTree(parser);

    try {
      technicalIndicatorResult.setMetaData(getMetaData(node));
      technicalIndicatorResult.setTechnicalAnalysis(getTechnicalAnalysis(node));
    } catch (Throwable throwable) {
      System.out.println("Error when deserializing:");
      System.out.println(node.toString());
      throwable.printStackTrace();
    }
    return technicalIndicatorResult;
  }

  private Map<Date, Map<TechnicalIndicator, Double>> getTechnicalAnalysis(JsonNode node) {
    Map<Date, Map<TechnicalIndicator, Double>> technicalAnalysis = new HashMap<>();
    node.fields().forEachRemaining(nodeEntry -> {
      if (!nodeEntry.getKey().equals(META_DATA_RESPONSE_KEY)) {
        try {
          JsonNode value = nodeEntry.getValue();
          value.fields().forEachRemaining(dateToValue -> {
            try {
              Map<TechnicalIndicator, Double> indicators = new HashMap<>();
              dateToValue.getValue().fields().forEachRemaining(indicatorValue -> {
                try {
                  indicators.put(TechnicalIndicator.valueOf(indicatorValue.getKey()),
                      Double.valueOf(indicatorValue.getValue().textValue()));
                }
                catch (Exception e) {
                  e.printStackTrace();
                }
              });
              technicalAnalysis.put(parseDate(dateToValue.getKey()), indicators);
            } catch (Exception e) {
              //
            }
          });
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });


    return technicalAnalysis;
  }

  private Date parseDate(String dateStr) throws ParseException {
    return dateStr.length() > 10 ?
        DATE_TIME_PARSER.parse(dateStr) :
        DATE_PARSER.parse(dateStr);
  }

  private static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd");
  private static final SimpleDateFormat DATE_TIME_PARSER = new SimpleDateFormat("yyyy-MM-dd HH:mm");


}
