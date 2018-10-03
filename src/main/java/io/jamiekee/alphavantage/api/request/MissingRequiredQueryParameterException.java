package io.jamiekee.alphavantage.api.request;

public class MissingRequiredQueryParameterException extends Exception {

  public MissingRequiredQueryParameterException(String queryParameterKey) {
    super(queryParameterKey + " is a required parameter and the request could not be sent.");
  }

  public MissingRequiredQueryParameterException(
      String requiredParameterKey, String dependantParameterKey) {
    super(requiredParameterKey + " is a required parameter " +
          "when using the " + dependantParameterKey + " query parameter " +
          "and the request could not be sent.");
  }



}
