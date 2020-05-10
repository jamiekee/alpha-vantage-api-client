package io.jamiekee.alphavantage.api.technical;

import io.jamiekee.alphavantage.api.response.MetaData;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class TechnicalIndicatorResult {

  private MetaData metaData;
  private Map<Date, Map<TechnicalIndicator, Double>> technicalAnalysis;

}
