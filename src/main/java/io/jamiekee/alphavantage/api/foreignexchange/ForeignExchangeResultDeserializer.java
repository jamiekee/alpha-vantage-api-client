package io.jamiekee.alphavantage.api.foreignexchange;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.jamiekee.alphavantage.api.response.MetaData;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static io.jamiekee.alphavantage.api.utils.AlphaVantageResultDeserializerHelper.getDateObjectMap;
import static io.jamiekee.alphavantage.api.utils.AlphaVantageResultDeserializerHelper.getMetaData;

public class ForeignExchangeResultDeserializer extends JsonDeserializer<ForeignExchangeResult> {
  @Override
  public ForeignExchangeResult deserialize(
      com.fasterxml.jackson.core.JsonParser parser, DeserializationContext deserializationContext
  )
      throws IOException {
    ForeignExchangeResult foreignExchangeResult = new ForeignExchangeResult();
    ObjectCodec oc = parser.getCodec();
    JsonNode node = oc.readTree(parser);
    try {
      MetaData metaData = getMetaData(node);
      Map<Date, ForeignExchange> foreignExchangeQuotes =
          getDateObjectMap(node, ForeignExchange.class);

      foreignExchangeResult.setForeignExchangeQuotes(foreignExchangeQuotes);
      foreignExchangeResult.setMetaData(metaData);
    } catch (Throwable t) {
      t.printStackTrace();
    }

    return foreignExchangeResult;
  }
}
