package io.jamiekee.alphavantage.api.timeseries;

import io.jamiekee.alphavantage.api.request.APIRequest;
import io.jamiekee.alphavantage.api.Interval;
import io.jamiekee.alphavantage.api.request.MissingRequiredQueryParameterException;
import io.jamiekee.alphavantage.api.request.OutputSize;
import lombok.Builder;

/**
 * A wrapper class for the available query parameters for the TimeSeries
 * endpoints of the API.
 */
@Builder
public class TimeSeriesRequest implements APIRequest {

  /**
   * Convert all the selected query parameters to a query parameter string
   * to be used the in the API request.
   * @return A Query parameter string.
   */
  public String toQueryParameters()
      throws MissingRequiredQueryParameterException {
    if (timeSeriesFunction == null)
      throw new MissingRequiredQueryParameterException("TimeSeriesFunction");
    if (symbol == null)
      throw new MissingRequiredQueryParameterException("Symbol");
    if (timeSeriesFunction == TimeSeriesFunction.INTRADAY
        && interval == null)
      throw new MissingRequiredQueryParameterException(
          "IntradayInterval", "TIME_SERIES_INTRADAY"
      );

    StringBuilder builder = new StringBuilder();
    builder
        .append("function=")
        .append(timeSeriesFunction.getQueryParameterKey());
    builder
        .append("&symbol=")
        .append(symbol);
    if (outputSize != null) {
      builder
          .append("&outputsize=")
          .append(outputSize);
    }
    if (interval != null) {
      builder
          .append("&interval=")
          .append(interval.getQueryParameterKey());
    }
    return builder.toString();
  }

  private Interval interval;
  private TimeSeriesFunction timeSeriesFunction;
  private String symbol;
  private OutputSize outputSize;
}
