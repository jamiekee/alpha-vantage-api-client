package io.jamiekee.alphavantage.api.timeseries;

import io.jamiekee.alphavantage.api.interfaces.APIRequest;
import io.jamiekee.alphavantage.api.request.OutputSize;
import lombok.Builder;

@Builder
public class TimeSeriesRequest implements APIRequest {

  public String toHttpPathVariables() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("function=")
        .append(timeSeriesFunction.getPathVariableKey());
    builder
        .append("&symbol=")
        .append(symbol);
    if (outputSize != null) {
      builder
          .append("&outputsize=")
          .append(outputSize);
    }
    if (intradayInterval != null) {
      builder
          .append("&interval=")
          .append(intradayInterval.getPathVariableKey());
    }
    return builder.toString();
  }

  private IntradayInterval intradayInterval;
  private TimeSeriesFunction timeSeriesFunction;
  private String symbol;
  private OutputSize outputSize;
}
