package io.jamiekee.alphavantage.api.interfaces;

public interface APIRequest {

  /**
   * Convert all the variables in the request to query parameters for the
   * Alpha Vantage API.
   * @return A list of query parameters.
   */
  String toQueryParameters();

}
