package io.jamiekee.alphavantage.api.interfaces;

import java.io.IOException;

import io.jamiekee.alphavantage.api.timeseries.TimeSeriesFunction;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesResult;
import io.jamiekee.alphavantage.api.request.OutputSize;

public interface IAlphaVantageClient {

  TimeSeriesResult getTimeSeries(TimeSeriesFunction timeSeriesFunction, String symbol) throws IOException;

  TimeSeriesResult getTimeSeries(TimeSeriesFunction timeSeriesFunction, String symbol, OutputSize outputSize) throws IOException;

}
