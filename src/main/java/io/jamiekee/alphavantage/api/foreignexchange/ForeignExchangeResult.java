package io.jamiekee.alphavantage.api.foreignexchange;

import io.jamiekee.alphavantage.api.response.MetaData;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ForeignExchangeResult {

  private MetaData metaData;
  private Map<Date, ForeignExchange> foreignExchangeQuotes;

}
