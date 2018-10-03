package io.jamiekee.alphavantage.api.configuration;

import io.jamiekee.alphavantage.api.batchquote.BatchQuoteResult;
import io.jamiekee.alphavantage.api.batchquote.InvalidSymbolLengthException;
import io.jamiekee.alphavantage.api.currencyexchange.CurrencyExchange;
import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.request.IntradayInterval;
import io.jamiekee.alphavantage.api.request.MissingRequiredQueryParameterException;
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
  )
      throws IOException, MissingRequiredQueryParameterException;

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
   * Request the BatchQuote API for a quote on a selection of specific Symbols.
   * @param symbols The Symbols to get a quote.
   * @return The quotes for the symbols requested.
   */
  BatchQuoteResult getBatchQuote(String... symbols)
      throws MissingRequiredQueryParameterException,
      InvalidSymbolLengthException, IOException;

  /**
   * Request a currency exchange rate from one currency to another.
   * @param fromCurrency The from currency in the exchange rate.
   * @param toCurrency The to currency in the exchange rate.
   * @return The quote for the currency exchange.
   */
  CurrencyExchange getCurrencyExchange(String fromCurrency, String toCurrency)
      throws MissingRequiredQueryParameterException,
      InvalidSymbolLengthException, IOException;

}
