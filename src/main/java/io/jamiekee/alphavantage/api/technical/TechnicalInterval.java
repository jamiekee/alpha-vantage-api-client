package io.jamiekee.alphavantage.api.technical;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TechnicalInterval {

  @JsonProperty("1min") ONE_MINUTE("1min"),
  @JsonProperty("5min") FIVE_MINUTES("5min"),
  @JsonProperty("15min") FIFTEEN_MINUTES("15min"),
  @JsonProperty("30min") THIRTY_MINUTES("30min"),
  @JsonProperty("60min") SIXTY_MINUTES("60min"),
  @JsonProperty("daily") DAILY("daily"),
  @JsonProperty("weekly") WEEKLY("weekly"),
  @JsonProperty("monthly") MONTHLY("monthly");

  TechnicalInterval(String queryParameterKey) {
    this.queryParameterKey = queryParameterKey;
  }

  public String getQueryParameterKey() {
    return queryParameterKey;
  }

  private String queryParameterKey;
}
