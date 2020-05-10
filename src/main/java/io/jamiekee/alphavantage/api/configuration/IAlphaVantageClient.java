package io.jamiekee.alphavantage.api.configuration;

import io.jamiekee.alphavantage.api.currencyexchange.CurrencyExchange;
import io.jamiekee.alphavantage.api.currencyexchange.CurrencyExchangeResult;
import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.Interval;
import io.jamiekee.alphavantage.api.request.MissingRequiredQueryParameterException;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesFunction;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesResult;

import java.io.IOException;

public interface IAlphaVantageClient {

  /**
   * Get the Intraday stock data for a single stock.
   * @param interval The interval between stock quotes.
   * @param symbol The stock to get the data for.
   * @return The Intraday API response.
   */
  TimeSeriesResult getTimeSeries(
      Interval interval,
      String symbol
  )
      throws IOException, MissingRequiredQueryParameterException;

  /**
   * Get the Intraday stock data for a single stock.
   * @param interval The interval between stock quotes.
   * @param symbol The stock to get the data for.
   * @param outputSize The output size of either Compact or Full.
   * @return The Intraday API response.
   */
  TimeSeriesResult getTimeSeries(
      Interval interval,
      String symbol,
      OutputSize outputSize
  )
      throws IOException, MissingRequiredQueryParameterException;

  /**
   * Request the TimeSeries Alpha Vantage API for a specific indicator and symbol.
   * @param timeSeriesFunction The indicator for the TimeSeries request.
   * @param symbol The stock to get the data for.
   * @return The TimeSeries API response.
   */
  TimeSeriesResult getTimeSeries(
      TimeSeriesFunction timeSeriesFunction,
      String symbol
  )
      throws IOException, MissingRequiredQueryParameterException;

  /**
   * Request the TimeSeries Alpha Vantage API for a specific indicator, symbol and
   * output size.
   * @param timeSeriesFunction The indicator for the TimeSeries request.
   * @param symbol The stock to get the data for.
   * @param outputSize The output size of either Compact or Full.
   * @return The TimeSeries API response.
   */
  TimeSeriesResult getTimeSeries(
      TimeSeriesFunction timeSeriesFunction,
      String symbol,
      OutputSize outputSize
  )
      throws IOException, MissingRequiredQueryParameterException;

  /**
   * Request a currency exchange rate from one currency to another.
   * @param fromCurrency The from currency in the exchange rate.
   * @param toCurrency The to currency in the exchange rate.
   * @return The quote for the currency exchange.
   */
  CurrencyExchangeResult getCurrencyExchange(String fromCurrency, String toCurrency)
      throws MissingRequiredQueryParameterException, IOException;

}
