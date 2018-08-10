package timeseries;

import interfaces.APIRequest;
import request.OutputSize;

public class TimeSeriesRequest implements APIRequest {

  public TimeSeriesRequest(TimeSeriesFunction timeSeriesFunction, String symbol) {
    this.timeSeriesFunction = timeSeriesFunction;
    this.symbol = symbol;
  }

  public TimeSeriesRequest(TimeSeriesFunction timeSeriesFunction, String symbol, OutputSize outputSize) {
    this.timeSeriesFunction = timeSeriesFunction;
    this.symbol = symbol;
    this.outputSize = outputSize;
  }

  public String toHttpPathVariables() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("timeSeriesFunction=")
        .append(timeSeriesFunction.getPathVariableKey());
    builder
        .append("&symbol=")
        .append(symbol);
    if (outputSize != null) {
      builder
          .append("&outputsize=")
          .append(outputSize);
    }
    return builder.toString();
  }

  private TimeSeriesFunction timeSeriesFunction; // required
  private String symbol; // required
  private OutputSize outputSize; // optional
}
