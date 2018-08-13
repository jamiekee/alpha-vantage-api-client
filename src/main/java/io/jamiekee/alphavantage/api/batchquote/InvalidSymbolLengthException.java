package io.jamiekee.alphavantage.api.batchquote;

public class InvalidSymbolLengthException extends Exception {

  public InvalidSymbolLengthException(int size) {
    super(size + " is an invalid length of symbols. Must be between 1-100. " +
          "Request could not be sent.");
  }

}
