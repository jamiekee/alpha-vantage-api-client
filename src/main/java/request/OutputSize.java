package request;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OutputSize {
  @JsonProperty("Compact") COMPACT,
  @JsonProperty("Full") FULL;
}
