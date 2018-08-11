package io.jamiekee.alphavantage.api.timeseries;

public enum TimeSeriesFunction {
  DAILY("TIME_SERIES_DAILY"),
  DAILY_ADJUSTED("TIME_SERIES_DAILY_ADJUSTED"),
  WEEKLY("TIME_SERIES_WEEKLY"),
  WEEKLY_ADJUSTED("TIME_SERIES_WEEKLY_ADJUSTED"),
  MONTHLY("TIME_SERIES_MONTHLY"),
  MONTHLY_ADJUSTED("TIME_SERIES_MONTHLY_ADJUSTED");
//  INTRADAY("TIME_SERIES_INTRADAY");
//  BATCH_QUOTES("", "");

  TimeSeriesFunction(String pathVariableKey) {
    this.pathVariableKey = pathVariableKey;
  }

  public String getPathVariableKey() {
    return pathVariableKey;
  }

  private String pathVariableKey;
}
