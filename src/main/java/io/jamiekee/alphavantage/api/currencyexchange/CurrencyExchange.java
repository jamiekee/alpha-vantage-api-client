package io.jamiekee.alphavantage.api.currencyexchange;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyExchange {

  @JsonProperty("From_Currency Code")
  private String fromCurrency;
  @JsonProperty("From_Currency Name")
  private String fromCurrencyName;
  @JsonProperty("To_Currency Code")
  private String toCurrency;
  @JsonProperty("To_Currency Name")
  private String toCurrencyName;
  @JsonProperty("Exchange Rate")
  private double exchangeRate;
  @JsonProperty("Last Refreshed")
  private String lastRefreshed;
  @JsonProperty("Time Zone")
  private String timezone;
}
