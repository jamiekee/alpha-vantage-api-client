package io.jamiekee.alphavantage.api.configuration;

public class AlphaVantageClientConfiguration {
  public AlphaVantageClientConfiguration(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getApiKey() {
    return apiKey;
  }

  private String apiKey;
}
