package io.jamiekee.alphavantage.api.interfaces;

import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.timeseries.IntradayInterval;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesFunction;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesResult;

import java.io.IOException;

public interface IAlphaVantageClient {

  /**
   * Get the Intraday stock data for a single stock.
   * @param intradayInterval The interval between stock quotes.
   * @param symbol The stock to get the data for.
   * @return The Intraday API response.
   */
  TimeSeriesResult getTimeSeries(
      IntradayInterval intradayInterval,
      String symbol
  ) throws IOException;

  /**
   * Get the Intraday stock data for a single stock.
   * @param intradayInterval The interval between stock quotes.
   * @param symbol The stock to get the data for.
   * @param outputSize The output size of either Compact or Full.
   * @return The Intraday API response.
   */
  TimeSeriesResult getTimeSeries(
      IntradayInterval intradayInterval,
      String symbol,
      OutputSize outputSize
  ) throws IOException;

  /**
   * Request the TimeSeries Alpha Vantage API for a specific function and symbol.
   * @param timeSeriesFunction The function for the TimeSeries request.
   * @param symbol The stock to get the data for.
   * @return The TimeSeries API response.
   */
  TimeSeriesResult getTimeSeries(
      TimeSeriesFunction timeSeriesFunction,
      String symbol
  ) throws IOException;

  /**
   * Request the TimeSeries Alpha Vantage API for a specific function, symbol and
   * output size.
   * @param timeSeriesFunction The function for the TimeSeries request.
   * @param symbol The stock to get the data for.
   * @param outputSize The output size of either Compact or Full.
   * @return The TimeSeries API response.
   */
  TimeSeriesResult getTimeSeries(
      TimeSeriesFunction timeSeriesFunction,
      String symbol,
      OutputSize outputSize
  ) throws IOException;

}
