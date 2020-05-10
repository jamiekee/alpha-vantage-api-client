package io.jamiekee.alphavantage.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The interval query parameter for the TimeSeries API.
 */
public enum Interval {
  @JsonProperty("1min") ONE_MINUTE("1min"),
  @JsonProperty("5min") FIVE_MINUTES("5min"),
  @JsonProperty("15min") FIFTEEN_MINUTES("15min"),
  @JsonProperty("30min") THIRTY_MINUTES("30min"),
  @JsonProperty("60min") SIXTY_MINUTES("60min"),
  @JsonProperty("weekly") WEEKLY("weekly");

  Interval(String queryParameterKey) {
    this.queryParameterKey = queryParameterKey;
  }

  public String getQueryParameterKey() {
    return queryParameterKey;
  }

  private String queryParameterKey;
}
