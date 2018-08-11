package io.jamiekee.alphavantage.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import io.jamiekee.alphavantage.api.request.OutputSize;

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
}
