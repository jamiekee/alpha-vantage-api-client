package io.jamiekee.alphavantage.api.technical;

import io.jamiekee.alphavantage.api.request.APIRequest;
import io.jamiekee.alphavantage.api.request.MissingRequiredQueryParameterException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TechnicalIndicatorRequest implements APIRequest {

  @Override
  public String toQueryParameters()
      throws MissingRequiredQueryParameterException {
    if (indicator == null)
      throw new MissingRequiredQueryParameterException("ForeignExchangeFunction");
    if (symbol == null)
      throw new MissingRequiredQueryParameterException("Symbol");
    if (interval == null)
      throw new MissingRequiredQueryParameterException("Interval");
    if (timePeriod <= 0 && !indicator.equals(TechnicalIndicator.VWAP))
      throw new IllegalArgumentException("TimePeriod must be a positive integer");
    if (seriesType == null && !indicator.equals(TechnicalIndicator.VWAP))
      throw new MissingRequiredQueryParameterException("SeriesType");

    StringBuilder queryBuilder =
        new StringBuilder()
            .append("function=")
            .append(indicator)
            .append("&symbol=")
            .append(symbol)
            .append("&interval=")
            .append(interval.getQueryParameterKey());
    if (timePeriod > 0) {
      queryBuilder
          .append("&time_period=")
          .append(timePeriod);
    }
    if (seriesType != null) {
      queryBuilder
          .append("&series_type=")
          .append(seriesType);
    }

    return queryBuilder.toString();
  }

  private TechnicalIndicator indicator;
  private String symbol;
  private TechnicalInterval interval;
  private int timePeriod;
  private TechnicalSeriesType seriesType;
}
