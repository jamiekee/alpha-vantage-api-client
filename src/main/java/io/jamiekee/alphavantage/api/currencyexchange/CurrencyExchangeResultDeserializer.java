package io.jamiekee.alphavantage.api.currencyexchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.jamiekee.alphavantage.api.utils.JsonParser;

import java.io.IOException;
import java.util.Map;

import static io.jamiekee.alphavantage.api.utils.AlphaVantageResultDeserializerHelper.sanitizeNodeKeys;

public class CurrencyExchangeResultDeserializer extends JsonDeserializer<CurrencyExchangeResult> {
  @Override
  public CurrencyExchangeResult deserialize(
      com.fasterxml.jackson.core.JsonParser parser, DeserializationContext deserializationContext
  )
      throws IOException, JsonProcessingException {
    CurrencyExchangeResult currencyExchangeResult = new CurrencyExchangeResult();
    ObjectCodec oc = parser.getCodec();
    JsonNode node = oc.readTree(parser);
    try {
      Map<String, Object> sanitizedNodeKeys =
          sanitizeNodeKeys(node.fields().next().getValue());
      CurrencyExchangeQuote quote =
          JsonParser.toObject(
              JsonParser.toJson(sanitizedNodeKeys),
              CurrencyExchangeQuote.class
          );
      currencyExchangeResult.setQuote(quote);
    } catch (Throwable t) {
      t.printStackTrace();
    }
    return currencyExchangeResult;
  }
}
