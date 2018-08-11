package io.jamiekee.alphavantage.api;

import io.jamiekee.alphavantage.api.configuration.AlphaVantageClientConfiguration;
import io.jamiekee.alphavantage.api.configuration.DataType;
import io.jamiekee.alphavantage.api.interfaces.IAlphaVantageClient;
import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.timeseries.IntradayInterval;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesFunction;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesRequest;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesResult;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesResultDeserializer;
import io.jamiekee.alphavantage.api.utils.JsonParser;

import java.io.IOException;

public class AlphaVantageClient implements IAlphaVantageClient {

  public AlphaVantageClient(AlphaVantageClientConfiguration configuration) {
    this.configuration = configuration;
    JsonParser.addDeserializer(TimeSeriesResult.class, new TimeSeriesResultDeserializer());
  }

  @Override
  public TimeSeriesResult getTimeSeries(
      IntradayInterval intradayInterval, String symbol
  )
      throws IOException {
    return getTimeSeries(intradayInterval, symbol, OutputSize.COMPACT);
  }

  @Override
  public TimeSeriesResult getTimeSeries(
      IntradayInterval intradayInterval, String symbol, OutputSize outputSize
  )
      throws IOException {
    String pathVariables = TimeSeriesRequest.builder()
        .timeSeriesFunction(TimeSeriesFunction.INTRADAY)
        .intradayInterval(intradayInterval)
        .symbol(symbol)
        .outputSize(outputSize)
        .build()
        .toHttpPathVariables();
    return getTimeSeriesResult(pathVariables);
  }

  @Override
  public TimeSeriesResult getTimeSeries(TimeSeriesFunction timeSeriesFunction, String symbol)
      throws IOException {
    return getTimeSeries(timeSeriesFunction, symbol, OutputSize.COMPACT);
  }

  @Override
  public TimeSeriesResult getTimeSeries(
      TimeSeriesFunction timeSeriesFunction, IntradayInterval intradayInterval,
      String symbol
  )
      throws IOException {
    return getTimeSeries(intradayInterval, symbol);
  }

  @Override
  public TimeSeriesResult getTimeSeries(TimeSeriesFunction timeSeriesFunction, String symbol, OutputSize outputSize)
      throws IOException {
    String pathVariables = TimeSeriesRequest.builder()
        .timeSeriesFunction(timeSeriesFunction)
        .symbol(symbol)
        .outputSize(outputSize)
        .build()
        .toHttpPathVariables();
    return getTimeSeriesResult(pathVariables);
  }

  private TimeSeriesResult getTimeSeriesResult(String pathVariables)
      throws IOException {
    pathVariables += "&datatype=" + DataType.JSON;
    pathVariables += "&apikey=" + configuration.getApiKey();
    return JsonParser
        .toObject(Request.sendRequest(pathVariables), TimeSeriesResult.class);
  }

  private AlphaVantageClientConfiguration configuration;
}
