package io.jamiekee.alphavantage.api;

import io.jamiekee.alphavantage.api.configuration.AlphaVantageClientConfiguration;
import io.jamiekee.alphavantage.api.configuration.IAlphaVantageClient;
import io.jamiekee.alphavantage.api.currencyexchange.CurrencyExchangeRequest;
import io.jamiekee.alphavantage.api.currencyexchange.CurrencyExchangeResult;
import io.jamiekee.alphavantage.api.currencyexchange.CurrencyExchangeResultDeserializer;
import io.jamiekee.alphavantage.api.foreignexchange.ForeignExchangeFunction;
import io.jamiekee.alphavantage.api.foreignexchange.ForeignExchangeRequest;
import io.jamiekee.alphavantage.api.foreignexchange.ForeignExchangeResult;
import io.jamiekee.alphavantage.api.foreignexchange.ForeignExchangeResultDeserializer;
import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.sector.SectorResult;
import io.jamiekee.alphavantage.api.sector.SectorResultDeserializer;
import io.jamiekee.alphavantage.api.request.MissingRequiredQueryParameterException;
import io.jamiekee.alphavantage.api.technical.*;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesFunction;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesRequest;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesResult;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesResultDeserializer;
import io.jamiekee.alphavantage.api.utils.JsonParser;

import java.io.IOException;

import static io.jamiekee.alphavantage.api.Request.send;

public class AlphaVantageClient implements IAlphaVantageClient {

  public AlphaVantageClient(AlphaVantageClientConfiguration configuration) {
    this.configuration = configuration;
    this.technicalIndicatorClient = new TechnicalIndicatorClient(configuration);
    JsonParser.addDeserializer(TimeSeriesResult.class, new TimeSeriesResultDeserializer());
    JsonParser.addDeserializer(CurrencyExchangeResult.class, new CurrencyExchangeResultDeserializer());
    JsonParser.addDeserializer(ForeignExchangeResult.class, new ForeignExchangeResultDeserializer());
    JsonParser.addDeserializer(SectorResult.class, new SectorResultDeserializer());
  }

  @Override
  public TimeSeriesResult getTimeSeries(Interval interval, String symbol)
      throws IOException, MissingRequiredQueryParameterException {
    return getTimeSeries(interval, symbol, OutputSize.COMPACT);
  }

  @Override
  public TimeSeriesResult getTimeSeries(Interval interval,
                                        String symbol, OutputSize outputSize)
      throws IOException, MissingRequiredQueryParameterException {
    String queryParameters = TimeSeriesRequest.builder()
        .timeSeriesFunction(TimeSeriesFunction.INTRADAY)
        .interval(interval)
        .symbol(symbol)
        .outputSize(outputSize)
        .build()
        .toQueryParameters();
    return send(queryParameters, configuration.getApiKey(), TimeSeriesResult.class);
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
    return send(queryParameters, configuration.getApiKey(), TimeSeriesResult.class);
  }

  @Override
  public CurrencyExchangeResult getCurrencyExchange(String fromCurrency, String toCurrency)
      throws MissingRequiredQueryParameterException, IOException {
    String queryParameters = CurrencyExchangeRequest.builder()
        .fromCurrency(fromCurrency)
        .toCurrency(toCurrency)
        .build()
        .toQueryParameters();
    return send(queryParameters, configuration.getApiKey(), CurrencyExchangeResult.class);
  }

  public ForeignExchangeResult getForeignExchange(ForeignExchangeFunction function,
                                                  String fromCurrency, String toCurrency)
      throws MissingRequiredQueryParameterException, IOException {
    String queryParameters = ForeignExchangeRequest.builder()
        .function(function)
        .fromCurrency(fromCurrency)
        .toCurrency(toCurrency)
        .build()
        .toQueryParameters();
    return send(queryParameters, configuration.getApiKey(), ForeignExchangeResult.class);
  }

  public SectorResult getSectorPerformances()
      throws IOException {
    String queryParameters = "function=SECTOR";
    return send(queryParameters, configuration.getApiKey(), SectorResult.class);
  }

  public TechnicalIndicatorClient getTechnicalIndicatorClient() {
    return technicalIndicatorClient;
  }


  private final AlphaVantageClientConfiguration configuration;
  private final TechnicalIndicatorClient technicalIndicatorClient;

}
