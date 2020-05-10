package io.jamiekee.alphavantage.api.foreignexchange;

import io.jamiekee.alphavantage.api.request.APIRequest;
import io.jamiekee.alphavantage.api.Interval;
import io.jamiekee.alphavantage.api.request.OutputSize;
import io.jamiekee.alphavantage.api.request.MissingRequiredQueryParameterException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForeignExchangeRequest implements APIRequest {

  @Override
  public String toQueryParameters()
      throws MissingRequiredQueryParameterException {
    if (function == null)
      throw new MissingRequiredQueryParameterException("ForeignExchangeFunction");
    if (fromCurrency == null)
      throw new MissingRequiredQueryParameterException("FromCurrency");
    if (toCurrency == null)
      throw new MissingRequiredQueryParameterException("ToCurrency");
    if (function == ForeignExchangeFunction.FX_INTRADAY
        && interval == null)
      throw new MissingRequiredQueryParameterException(
          "IntradayInterval", "FX_INTRADAY"
      );

    StringBuilder builder = new StringBuilder();
    builder
        .append("function=")
        .append(function)
        .append("&from_symbol=")
        .append(fromCurrency)
        .append("&to_symbol=")
        .append(toCurrency);

    if (outputSize != null) {
      builder
          .append("&outputsize=")
          .append(outputSize);
    }
    if (interval != null) {
      builder
          .append("&interval=")
          .append(interval.getQueryParameterKey());
    }
    return builder.toString();
  }

  private ForeignExchangeFunction function;
  private String fromCurrency;
  private String toCurrency;
  private Interval interval;
  private OutputSize outputSize;

}
