package io.jamiekee.alphavantage.api.foreignexchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.jamiekee.alphavantage.api.response.MetaData;
import io.jamiekee.alphavantage.api.utils.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.jamiekee.alphavantage.api.utils.AlphaVantageResultDeserializerHelper.getMetaData;
import static io.jamiekee.alphavantage.api.utils.AlphaVantageResultDeserializerHelper.sanitizeNodeKeys;

public class ForeignExchangeResultDeserializer extends JsonDeserializer<ForeignExchangeResult> {
  @Override
  public ForeignExchangeResult deserialize(
      com.fasterxml.jackson.core.JsonParser parser, DeserializationContext deserializationContext
  )
      throws IOException, JsonProcessingException {
    ForeignExchangeResult foreignExchangeResult = new ForeignExchangeResult();
    ObjectCodec oc = parser.getCodec();
    JsonNode node = oc.readTree(parser);
    try {
      MetaData metaData = getMetaData(node);

      List<ForeignExchangeQuote> foreignExchangeQuotes = new ArrayList<>();
      node.fields().forEachRemaining(nodeEntry -> {
        // ignore meta data, we want the time series data
        if (!nodeEntry.getKey().equals("Meta Data")) {
          nodeEntry.getValue().forEach(batchQuote -> {
            try {
              foreignExchangeQuotes.add(
                  JsonParser.toObject(
                      JsonParser.toJson(sanitizeNodeKeys(batchQuote)),
                      ForeignExchangeQuote.class
                  )
              );
            }
            catch (IOException e) {
              e.printStackTrace();
            }
          });
        }
      });
      foreignExchangeResult.setForeignExchangeQuotes(foreignExchangeQuotes);
      foreignExchangeResult.setMetaData(metaData);
    } catch (Throwable t) {
      t.printStackTrace();
    }

    return foreignExchangeResult;
  }
}
