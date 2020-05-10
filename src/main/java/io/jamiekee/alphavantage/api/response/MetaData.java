package io.jamiekee.alphavantage.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.Interval;
import lombok.Data;

/**
 * Part of the Response from the API is a meta data section.
 * This java class represents the meta data in the response.
 */
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
  private Interval interval;
  @JsonProperty("Notes")
  private String notes;
  @JsonProperty("From Symbol")
  private String fromCurrency;
  @JsonProperty("To Symbol")
  private String toCurrency;

  public static final String META_DATA_RESPONSE_KEY = "Meta Data";
}
