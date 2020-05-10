package io.jamiekee.alphavantage.api.technicalindicator;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jamiekee.alphavantage.api.response.MetaData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TechnicalIndicatorMetaData extends MetaData {

  @JsonProperty("Series Type")
  private String seriesType;
  @JsonProperty("Time Period")
  private String timePeriod;
  @JsonProperty("Period")
  private String period;
  @JsonProperty("Indicator")
  private String indicator;
  @JsonProperty("Fast Limit")
  private String fastLimit;
  @JsonProperty("Refreshed")
  private String refreshed;
  @JsonProperty("Type")
  private String type;
  @JsonProperty("Zone")
  private String zone;
  @JsonProperty("Limit")
  private String limit;
  @JsonProperty("Volume Factor (vFactor)")
  private String volumeFactor;

}
