package io.jamiekee.alphavantage.api;

import java.io.IOException;

import io.jamiekee.alphavantage.api.configuration.AlphaVantageClientConfiguration;
import io.jamiekee.alphavantage.api.configuration.DataType;
import io.jamiekee.alphavantage.api.interfaces.IAlphaVantageClient;
import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesFunction;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesRequest;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesResult;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesResultDeserializer;
import io.jamiekee.alphavantage.api.utils.JsonParser;

public class AlphaVantageClient implements IAlphaVantageClient {

  public AlphaVantageClient(AlphaVantageClientConfiguration configuration) {
    this.configuration = configuration;
    JsonParser.addDeserializer(TimeSeriesResult.class, new TimeSeriesResultDeserializer());
  }

  @Override
  public TimeSeriesResult getTimeSeries(TimeSeriesFunction timeSeriesFunction, String symbol)
      throws IOException {
    return getTimeSeries(timeSeriesFunction, symbol, OutputSize.COMPACT);
  }

  @Override
  public TimeSeriesResult getTimeSeries(TimeSeriesFunction timeSeriesFunction, String symbol, OutputSize outputSize)
      throws IOException {
    TimeSeriesRequest request = new TimeSeriesRequest(timeSeriesFunction, symbol, outputSize);
    String pathVariables = request.toHttpPathVariables();
    pathVariables += "&datatype=" + DataType.JSON;
    pathVariables += "&apikey=" + configuration.getApiKey();
    return JsonParser.toObject(Request.sendRequest(pathVariables), TimeSeriesResult.class);
  }

  private AlphaVantageClientConfiguration configuration;
}
