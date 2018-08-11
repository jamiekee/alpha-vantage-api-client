package io.jamiekee.alphavantage.api.timeseries;

import java.util.Date;
import java.util.Map;
import lombok.Data;
import io.jamiekee.alphavantage.api.response.MetaData;

@Data
public class TimeSeriesResult {
  private MetaData metaData;
  private Map<Date, TimeSeries> timeSeries;
}
