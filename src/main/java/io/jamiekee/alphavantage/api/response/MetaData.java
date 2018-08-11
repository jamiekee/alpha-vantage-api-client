package io.jamiekee.alphavantage.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.timeseries.IntradayInterval;
import lombok.Data;

@Data
public class MetaData {
  @JsonProperty("Information")
  private String information;
  @JsonProperty("Symbol")
  private String symbol;
  @JsonProperty("Output Size")
  private OutputSize outputSize;
  @JsonProperty("Time Zone")
  private String timezone;
  @JsonProperty("Last Refreshed")
  private String lastRefreshed;
  @JsonProperty("Interval")
  private IntradayInterval interval;
}
