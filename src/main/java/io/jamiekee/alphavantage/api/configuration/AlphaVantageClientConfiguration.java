package io.jamiekee.alphavantage.api.configuration;

/**
 * Required configuration properties in order for the
 * API Client to function.
 */
public class AlphaVantageClientConfiguration {
  public AlphaVantageClientConfiguration(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getApiKey() {
    return apiKey;
  }

  private String apiKey;
}
