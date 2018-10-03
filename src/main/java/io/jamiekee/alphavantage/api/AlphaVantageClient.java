package io.jamiekee.alphavantage.api;

import io.jamiekee.alphavantage.api.batchquote.BatchQuoteRequest;
import io.jamiekee.alphavantage.api.batchquote.BatchQuoteResult;
import io.jamiekee.alphavantage.api.batchquote.BatchQuoteResultDeserializer;
import io.jamiekee.alphavantage.api.batchquote.InvalidSymbolLengthException;
import io.jamiekee.alphavantage.api.configuration.AlphaVantageClientConfiguration;
import io.jamiekee.alphavantage.api.configuration.DataType;
import io.jamiekee.alphavantage.api.configuration.IAlphaVantageClient;
import io.jamiekee.alphavantage.api.currencyexchange.CurrencyExchange;
import io.jamiekee.alphavantage.api.currencyexchange.CurrencyExchangeRequest;
import io.jamiekee.alphavantage.api.currencyexchange.CurrencyExchangeResult;
import io.jamiekee.alphavantage.api.currencyexchange.CurrencyExchangeResultDeserializer;
import io.jamiekee.alphavantage.api.foreignexchange.ForeignExchangeFunction;
import io.jamiekee.alphavantage.api.foreignexchange.ForeignExchangeRequest;
import io.jamiekee.alphavantage.api.foreignexchange.ForeignExchangeResult;
import io.jamiekee.alphavantage.api.foreignexchange.ForeignExchangeResultDeserializer;
import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.request.IntradayInterval;
import io.jamiekee.alphavantage.api.sector.SectorResult;
import io.jamiekee.alphavantage.api.sector.SectorResultDeserializer;
import io.jamiekee.alphavantage.api.request.MissingRequiredQueryParameterException;
import io.jamiekee.alphavantage.api.technical.TechnicalIndicator;
import io.jamiekee.alphavantage.api.technical.TechnicalIndicatorRequest;
import io.jamiekee.alphavantage.api.technical.TechnicalIndicatorResult;
import io.jamiekee.alphavantage.api.technical.TechnicalInterval;
import io.jamiekee.alphavantage.api.technical.TechnicalSeriesType;
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
    JsonParser.addDeserializer(CurrencyExchangeResult.class, new CurrencyExchangeResultDeserializer());
    JsonParser.addDeserializer(ForeignExchangeResult.class, new ForeignExchangeResultDeserializer());
    JsonParser.addDeserializer(SectorResult.class, new SectorResultDeserializer());
  }

  @Override
  public TimeSeriesResult getTimeSeries(IntradayInterval intradayInterval, String symbol)
      throws IOException, MissingRequiredQueryParameterException {
    return getTimeSeries(intradayInterval, symbol, OutputSize.COMPACT);
  }

  @Override
  public TimeSeriesResult getTimeSeries(IntradayInterval intradayInterval,
                                        String symbol, OutputSize outputSize)
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
  public TimeSeriesResult getTimeSeries(TimeSeriesFunction timeSeriesFunction,
                                        String symbol, OutputSize outputSize)
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

  @Override
  public CurrencyExchange getCurrencyExchange(String fromCurrency, String toCurrency)
      throws MissingRequiredQueryParameterException,
      InvalidSymbolLengthException, IOException {
    String queryParameters = CurrencyExchangeRequest.builder()
        .fromCurrency(fromCurrency)
        .toCurrency(toCurrency)
        .build()
        .toQueryParameters();
    return sendAPIRequest(queryParameters, CurrencyExchangeResult.class)
        .getQuote();
  }

  public ForeignExchangeResult getForeignExchange(ForeignExchangeFunction function,
                                                  String fromCurrency, String toCurrency)
      throws MissingRequiredQueryParameterException,
      InvalidSymbolLengthException, IOException {
    String queryParameters = ForeignExchangeRequest.builder()
        .function(function)
        .fromCurrency(fromCurrency)
        .toCurrency(toCurrency)
        .build()
        .toQueryParameters();
    return sendAPIRequest(queryParameters, ForeignExchangeResult.class);
  }

  public SectorResult getSectorPerformances()
      throws IOException {
    String queryParameters = "indicator=SECTOR";
    return sendAPIRequest(queryParameters, SectorResult.class);
  }

  public TechnicalIndicatorResult getTechnicalIndicator(TechnicalIndicator indicator, String symbol,
                                                        TechnicalInterval interval, int timePeriod,
                                                        TechnicalSeriesType seriesType)
      throws MissingRequiredQueryParameterException,
      InvalidSymbolLengthException, IOException {
    String queryParameters = TechnicalIndicatorRequest.builder()
        .function(indicator)
        .symbol(symbol)
        .interval(interval)
        .timePeriod(timePeriod)
        .seriesType(seriesType)
        .build()
        .toQueryParameters();
    return sendAPIRequest(queryParameters, TechnicalIndicatorResult.class);
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
