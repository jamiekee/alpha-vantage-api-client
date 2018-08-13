package io.jamiekee.alphavantage.api;

import io.jamiekee.alphavantage.api.batchquote.BatchQuoteRequest;
import io.jamiekee.alphavantage.api.batchquote.BatchQuoteResult;
import io.jamiekee.alphavantage.api.batchquote.BatchQuoteResultDeserializer;
import io.jamiekee.alphavantage.api.batchquote.InvalidSymbolLengthException;
import io.jamiekee.alphavantage.api.configuration.AlphaVantageClientConfiguration;
import io.jamiekee.alphavantage.api.configuration.DataType;
import io.jamiekee.alphavantage.api.interfaces.IAlphaVantageClient;
import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.timeseries.IntradayInterval;
import io.jamiekee.alphavantage.api.timeseries.MissingRequiredQueryParameterException;
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
    JsonParser.addDeserializer(BatchQuoteResult.class, new BatchQuoteResultDeserializer());
  }

  @Override
  public TimeSeriesResult getTimeSeries(
      IntradayInterval intradayInterval, String symbol
  )
      throws IOException, MissingRequiredQueryParameterException {
    return getTimeSeries(intradayInterval, symbol, OutputSize.COMPACT);
  }

  @Override
  public TimeSeriesResult getTimeSeries(
      IntradayInterval intradayInterval, String symbol, OutputSize outputSize
  )
      throws IOException, MissingRequiredQueryParameterException {
    String queryParameters = TimeSeriesRequest.builder()
        .timeSeriesFunction(TimeSeriesFunction.INTRADAY)
        .intradayInterval(intradayInterval)
        .symbol(symbol)
        .outputSize(outputSize)
        .build()
        .toQueryParameters();
    return sendAPIRequest(queryParameters, TimeSeriesResult.class);
  }

  @Override
  public TimeSeriesResult getTimeSeries(TimeSeriesFunction timeSeriesFunction, String symbol)
      throws IOException, MissingRequiredQueryParameterException {
    return getTimeSeries(timeSeriesFunction, symbol, OutputSize.COMPACT);
  }

  @Override
  public TimeSeriesResult getTimeSeries(TimeSeriesFunction timeSeriesFunction, String symbol, OutputSize outputSize)
      throws IOException, MissingRequiredQueryParameterException {
    String queryParameters = TimeSeriesRequest.builder()
        .timeSeriesFunction(timeSeriesFunction)
        .symbol(symbol)
        .outputSize(outputSize)
        .build()
        .toQueryParameters();
    return sendAPIRequest(queryParameters, TimeSeriesResult.class);
  }

  @Override
  public BatchQuoteResult getBatchQuote(String... symbols)
      throws MissingRequiredQueryParameterException,
      InvalidSymbolLengthException, IOException {
    String queryParameters = BatchQuoteRequest.builder()
        .symbols(symbols)
        .build()
        .toQueryParameters();
    return sendAPIRequest(queryParameters, BatchQuoteResult.class);
  }

  /**
   * Append the API Key and the DataType to the query parameters and send the
   * API request to Alpha Vantage.
   * @param queryParameters The query parameter string from the Request.
   * @param resultObject The expected result object from the API.
   * @return The Result of the API request.
   */
  private <T> T sendAPIRequest(String queryParameters, Class<T> resultObject)
      throws IOException {
    queryParameters += "&datatype=" + DataType.JSON;
    queryParameters += "&apikey=" + configuration.getApiKey();
    return JsonParser
        .toObject(Request.sendRequest(queryParameters), resultObject);
  }

  private AlphaVantageClientConfiguration configuration;
}
