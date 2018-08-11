package io.jamiekee.alphavantage.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * A wrapper class around the Error message response from the API.
 * This wrapper includes the error message and the query parameters used
 * in the request.
 */
@Data
public class ErrorResponse {
  @JsonProperty("Error Message")
  private String error;
  private String queryParameters;
}
