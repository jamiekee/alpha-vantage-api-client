package io.jamiekee.alphavantage.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorResponse {
  @JsonProperty("Error Message")
  private String error;
  private String pathVariables;
}
