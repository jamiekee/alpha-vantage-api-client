package io.jamiekee.alphavantage.api.currencyexchange;

import io.jamiekee.alphavantage.api.request.APIRequest;
import io.jamiekee.alphavantage.api.request.MissingRequiredQueryParameterException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyExchangeRequest implements APIRequest {

  @Override
  public String toQueryParameters()
      throws MissingRequiredQueryParameterException {
    if (fromCurrency == null)
      throw new MissingRequiredQueryParameterException("FromCurrency");
    if (toCurrency == null)
      throw new MissingRequiredQueryParameterException("ToCurrency");

    return new StringBuilder()
        .append("function=")
        .append(function)
        .append("&from_currency=")
        .append(fromCurrency)
        .append("&to_currency=")
        .append(toCurrency)
        .toString();
  }

  // TODO :: WHY ARE THESE STRINGS
  private String fromCurrency;
  private String toCurrency;

  private static final String function = "CURRENCY_EXCHANGE_RATE";
}
