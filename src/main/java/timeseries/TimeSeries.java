package timeseries;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TimeSeries {
  private double open;
  private double high;
  private double low;
  private double close;
  private double volume;
  @JsonProperty("adjusted close")
  private double adjustedClose;
  @JsonProperty("dividend amount")
  private double dividendAmount;
  @JsonProperty("split coefficient")
  private double splitCoefficient;
}
