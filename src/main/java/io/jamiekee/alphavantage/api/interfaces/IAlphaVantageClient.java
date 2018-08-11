package io.jamiekee.alphavantage.api.interfaces;

import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.timeseries.IntradayInterval;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesFunction;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesResult;

import java.io.IOException;

public interface IAlphaVantageClient {

  TimeSeriesResult getTimeSeries(
      IntradayInterval intradayInterval,
      String symbol
  ) throws IOException;

  TimeSeriesResult getTimeSeries(
      IntradayInterval intradayInterval,
      String symbol,
      OutputSize outputSize
  ) throws IOException;

  TimeSeriesResult getTimeSeries(
      TimeSeriesFunction timeSeriesFunction,
      String symbol
  ) throws IOException;

  TimeSeriesResult getTimeSeries(
      TimeSeriesFunction timeSeriesFunction,
      IntradayInterval intradayInterval,
      String symbol
  ) throws IOException;

  TimeSeriesResult getTimeSeries(
      TimeSeriesFunction timeSeriesFunction,
      String symbol,
      OutputSize outputSize
  ) throws IOException;

}
