package io.jamiekee.alphavantage.api.timeseries;

/**
 * Available parameters for the query parameter function in the TimeSeries
 * endpoints.
 */
public enum TimeSeriesFunction {
  DAILY("TIME_SERIES_DAILY"),
  DAILY_ADJUSTED("TIME_SERIES_DAILY_ADJUSTED"),
  WEEKLY("TIME_SERIES_WEEKLY"),
  WEEKLY_ADJUSTED("TIME_SERIES_WEEKLY_ADJUSTED"),
  MONTHLY("TIME_SERIES_MONTHLY"),
  MONTHLY_ADJUSTED("TIME_SERIES_MONTHLY_ADJUSTED"),
  INTRADAY("TIME_SERIES_INTRADAY");
//  BATCH_QUOTES("", "");

  TimeSeriesFunction(String queryParameterKey) {
    this.queryParameterKey = queryParameterKey;
  }

  public String getQueryParameterKey() {
    return queryParameterKey;
  }

  private String queryParameterKey;
}
