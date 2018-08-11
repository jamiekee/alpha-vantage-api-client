package io.jamiekee.alphavantage.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Query Parameter outputsize for the API.
 */
public enum OutputSize {
  @JsonProperty("Compact") COMPACT,
  @JsonProperty("Full") FULL;
}
