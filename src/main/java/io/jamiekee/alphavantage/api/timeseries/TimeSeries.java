package io.jamiekee.alphavantage.api.timeseries;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Optional;

/**
 * Representation of the data that is returned by the TimeSeries endpoints
 * in the API.
 * The values that are returned for the adjusted TimeSeries endpoints are
 * {@code Optional} typed.
 */
@Data
public class TimeSeries {

  /*
   Override the getters to return optionals.
  */
  public Optional<Double> getAdjustedClose() {
    return Optional.ofNullable(adjustedClose);
  }

  public Optional<Double> getDividendAmount() {
    return Optional.ofNullable(dividendAmount);
  }

  public Optional<Double> getSplitCoefficient() {
    return Optional.ofNullable(splitCoefficient);
  }

  private double open;
  private double high;
  private double low;
  private double close;
  private double volume;
  @JsonProperty("adjusted close")
  private Double adjustedClose = null;
  @JsonProperty("dividend amount")
  private Double dividendAmount = null;
  @JsonProperty("split coefficient")
  private Double splitCoefficient = null;
}
