package io.jamiekee.alphavantage.api.request;

import io.jamiekee.alphavantage.api.batchquote.InvalidSymbolLengthException;
import io.jamiekee.alphavantage.api.timeseries.MissingRequiredQueryParameterException;

public interface APIRequest {

  /**
   * Convert all the variables in the request to query parameters for the
   * Alpha Vantage API.
   * @return A list of query parameters.
   */
  String toQueryParameters()
      throws MissingRequiredQueryParameterException,
      InvalidSymbolLengthException;

}
