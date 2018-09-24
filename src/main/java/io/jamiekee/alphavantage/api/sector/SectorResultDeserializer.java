package io.jamiekee.alphavantage.api.sector;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.jamiekee.alphavantage.api.response.MetaData;
import io.jamiekee.alphavantage.api.utils.JsonParser;
import io.jamiekee.alphavantage.api.utils.Regex;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
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
      Map<SectorTime, List<SectorQuote>> performances = new HashMap<>();
      node.fields().forEachRemaining(field -> {
        String sanitizedFieldKey = Regex.getMatch(PERFORMANCE_KEY_REGEX, field.getKey());
        if (sanitizedFieldKey != null) {
          SectorTime sectorTime = SectorTime.fromFieldName(sanitizedFieldKey);
          System.out.println(sectorTime);

          field.getValue().fields().forEachRemaining(quote -> {
            Sector sector = Sector.fromFieldName(quote.getKey());
            System.out.println(sector);
            try {
              double percent =
                  new DecimalFormat("0.0#%").parse(quote.getValue().asText()).doubleValue();
              System.out.println(quote.getValue().asText());
              System.out.println(percent);
            }
            catch (ParseException e) {
              e.printStackTrace();
            }
          });
        }

      });
    } catch (Throwable t) {
      t.printStackTrace();
    }
    return sectorResult;
  }

  private static final String PERFORMANCE_KEY_REGEX = "Rank.*: (.*+)";
}
