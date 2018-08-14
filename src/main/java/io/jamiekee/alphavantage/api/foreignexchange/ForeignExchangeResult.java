package io.jamiekee.alphavantage.api.foreignexchange;

import io.jamiekee.alphavantage.api.response.MetaData;
import lombok.Data;

import java.util.List;

@Data
public class ForeignExchangeResult {

  private MetaData metaData;
  private List<ForeignExchangeQuote> foreignExchangeQuotes;
}
