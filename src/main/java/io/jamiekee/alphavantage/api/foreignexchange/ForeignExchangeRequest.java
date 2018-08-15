package io.jamiekee.alphavantage.api.foreignexchange;

import io.jamiekee.alphavantage.api.batchquote.InvalidSymbolLengthException;
import io.jamiekee.alphavantage.api.request.APIRequest;
import io.jamiekee.alphavantage.api.request.IntradayInterval;
import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.timeseries.MissingRequiredQueryParameterException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForeignExchangeRequest implements APIRequest {

  @Override
  public String toQueryParameters()
      throws MissingRequiredQueryParameterException,
      InvalidSymbolLengthException {
    if (function == null)
      throw new MissingRequiredQueryParameterException("ForeignExchangeFunction");
    if (fromCurrency == null)
      throw new MissingRequiredQueryParameterException("FromCurrency");
    if (toCurrency == null)
      throw new MissingRequiredQueryParameterException("ToCurrency");
    if (function == ForeignExchangeFunction.FX_INTRADAY
        && intradayInterval == null)
      throw new MissingRequiredQueryParameterException(
          "IntradayInterval", "FX_INTRADAY"
      );

    StringBuilder builder = new StringBuilder();
    builder
        .append("function=")
        .append(function);
    builder
        .append("&from_symbol=")
        .append(fromCurrency);
    builder
        .append("&to_symbol=")
        .append(toCurrency);
    if (outputSize != null) {
      builder
          .append("&outputsize=")
          .append(outputSize);
    }
    if (intradayInterval != null) {
      builder
          .append("&interval=")
          .append(intradayInterval.getQueryParameterKey());
    }
    return builder.toString();
  }

  private ForeignExchangeFunction function;
  private String fromCurrency;
  private String toCurrency;
  private IntradayInterval intradayInterval;
  private OutputSize outputSize;

}
