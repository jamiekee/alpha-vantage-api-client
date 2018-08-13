package io.jamiekee.alphavantage.api.batchquote;

import io.jamiekee.alphavantage.api.response.MetaData;
import lombok.Data;

import java.util.List;

@Data
public class BatchQuoteResult {

  private MetaData metaData;
  private List<BatchQuote> batchQuotes;
}
