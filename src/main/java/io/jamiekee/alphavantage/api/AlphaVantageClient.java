package io.jamiekee.alphavantage.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

import io.jamiekee.alphavantage.api.configuration.AlphaVantageClientConfiguration;
import io.jamiekee.alphavantage.api.configuration.DataType;
import io.jamiekee.alphavantage.api.interfaces.IAlphaVantageClient;
import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesFunction;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesRequest;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesResult;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesResultDeserializer;

public class AlphaVantageClient implements IAlphaVantageClient {

  public AlphaVantageClient(AlphaVantageClientConfiguration configuration) {
    this.configuration = configuration;
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addDeserializer(TimeSeriesResult.class, new TimeSeriesResultDeserializer());
    objectMapper.registerModule(simpleModule);
  }

  @Override
  public TimeSeriesResult getTimeSeries(TimeSeriesFunction timeSeriesFunction, String symbol)
      throws IOException {
    return getTimeSeries(new TimeSeriesRequest(timeSeriesFunction, symbol));
  }

  @Override
  public TimeSeriesResult getTimeSeries(TimeSeriesFunction timeSeriesFunction, String symbol, OutputSize outputSize)
      throws IOException {
    return getTimeSeries(new TimeSeriesRequest(timeSeriesFunction, symbol, outputSize));
  }

  private TimeSeriesResult getTimeSeries(TimeSeriesRequest request)
      throws IOException {
    String pathVariables = request.toHttpPathVariables();
    pathVariables += "&datatype=" + DataType.JSON;
    pathVariables += "&apikey=" + configuration.getApiKey();
    return objectMapper.readValue(Request.sendRequest(pathVariables), TimeSeriesResult.class);
  }

  private AlphaVantageClientConfiguration configuration;
  private ObjectMapper objectMapper = new ObjectMapper();
}
