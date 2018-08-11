package io.jamiekee.alphavantage.api.timeseries;

import io.jamiekee.alphavantage.api.response.MetaData;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * A representation of the API response for the TimeSeries endpoints.
 */
@Data
public class TimeSeriesResult {
  private MetaData metaData;
  private Map<Date, TimeSeries> timeSeries;
}
