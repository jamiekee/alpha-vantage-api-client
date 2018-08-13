package io.jamiekee.alphavantage.api.batchquote;

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

public class BatchQuoteResultDeserializer extends JsonDeserializer<BatchQuoteResult> {

  @Override
  public BatchQuoteResult deserialize(
      com.fasterxml.jackson.core.JsonParser jsonParser, DeserializationContext deserializationContext
  )
      throws IOException, JsonProcessingException {
    BatchQuoteResult batchQuoteResult = new BatchQuoteResult();
    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    try {
      MetaData metaData = getMetaData(node);

      List<BatchQuote> batchQuotes = new ArrayList<>();
      node.fields().forEachRemaining(nodeEntry -> {
        // ignore meta data, we want the time series data
        if (!nodeEntry.getKey().equals("Meta Data")) {
          nodeEntry.getValue().forEach(batchQuote -> {
            try {
              batchQuotes.add(
                  JsonParser.toObject(
                      JsonParser.toJson(sanitizeNodeKeys(batchQuote)),
                      BatchQuote.class
                  )
              );
            }
            catch (IOException e) {
              e.printStackTrace();
            }
          });
        }
      });
      batchQuoteResult.setBatchQuotes(batchQuotes);
      batchQuoteResult.setMetaData(metaData);
    } catch (Throwable t) {
      t.printStackTrace();
    }

    return batchQuoteResult;
  }
}
