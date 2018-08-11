package io.jamiekee.alphavantage.api.timeseries;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum IntradayInterval {
  @JsonProperty("1min") ONE_MINUTE("1min"),
  @JsonProperty("5min") FIVE_MINUTES("5min"),
  @JsonProperty("15min") FIFTEEN_MINUTES("15min"),
  @JsonProperty("30min") THIRTY_MINUTES("30min"),
  @JsonProperty("60min") SIXTY_MINUTES("60min");

  IntradayInterval(String pathVariableKey) {
    this.pathVariableKey = pathVariableKey;
  }

  public String getPathVariableKey() {
    return pathVariableKey;
  }

  private String pathVariableKey;
}
