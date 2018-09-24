package io.jamiekee.alphavantage.api.sector;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.jamiekee.alphavantage.api.utils.Regex;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static io.jamiekee.alphavantage.api.utils.AlphaVantageResultDeserializerHelper.getMetaData;

public class SectorResultDeserializer extends JsonDeserializer<SectorResult> {

  @Override
  public SectorResult deserialize(
      com.fasterxml.jackson.core.JsonParser parser, DeserializationContext context)
      throws IOException {
    SectorResult sectorResult = new SectorResult();
    ObjectCodec oc = parser.getCodec();
    JsonNode node = oc.readTree(parser);
    try {
      sectorResult.setMetaData(getMetaData(node));
      sectorResult.setTimedSectorPerformances(getTimedPerformances(node));
    } catch (Throwable t) {
      t.printStackTrace();
    }
    return sectorResult;
  }

  /**
   * For each of the SectorTime's in the given JSON node extract the sector's
   * performances.
   * @param node The JSON Node that contains all the SectorTimes and performances.
   * @return A Map which maps the SectorTime to each of the Sector's performances
   * in that period.
   */
  private Map<SectorTime, Map<Sector, Double>> getTimedPerformances(JsonNode node) {
    Map<SectorTime, Map<Sector, Double>> timedPerformances = new HashMap<>();
    node.fields().forEachRemaining(field -> {
      if (!field.getKey().equals("Meta Data")) {
        SectorTime sectorTime = getSectorTime(field.getKey());
        timedPerformances.put(sectorTime, getSectorPerformances(field.getValue()));
      }
    });
    return timedPerformances;
  }

  /**
   * Parse each of the individual sector performances for a given SectorTime.
   * @param jsonValue the JSON Node that contains the sector performances for a
   *                  given SectorTime.
   * @return A map that contains the sector and their respective performance
   * as a percentage.
   */
  private Map<Sector, Double> getSectorPerformances(JsonNode jsonValue) {
    Map<Sector, Double> performances = new HashMap<>();
    jsonValue.fields().forEachRemaining(quote -> {
      try {
        Sector sector = Sector.fromFieldName(quote.getKey());
        Double percentage = new DecimalFormat()
            .parse(quote.getValue().asText())
            .doubleValue();
        performances.put(sector, percentage);
      }
      catch (ParseException e) {
        e.printStackTrace();
      }
    });
    return performances;
  }

  /**
   * Extract the SectorTime from the field's key.
   * @param key The value of the JSON key.
   * @return The parsed SectorTime from the key.
   */
  private SectorTime getSectorTime(String key) {
    String sanitizedFieldKey = Regex.getMatch(PERFORMANCE_KEY_REGEX, key);
    return SectorTime.fromFieldName(sanitizedFieldKey);
  }

  private static final String PERFORMANCE_KEY_REGEX = "Rank.*: (.*+)";
}
