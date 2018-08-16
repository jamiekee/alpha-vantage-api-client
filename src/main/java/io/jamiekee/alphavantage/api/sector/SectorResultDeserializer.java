package io.jamiekee.alphavantage.api.sector;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.jamiekee.alphavantage.api.response.MetaData;
import io.jamiekee.alphavantage.api.utils.JsonParser;

import java.io.IOException;
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
      Map<String, Object> mapMetaData = node.get("Meta Data");
      sectorResult.setMetaData(JsonParser.toObject(
          node.get("Meta Data"), MetaData.class
      ));
      node.fields().forEachRemaining(field -> {
        System.out.println(field.getKey());
      });
    } catch (Throwable t) {
      t.printStackTrace();
    }
    return sectorResult;
  }


}
