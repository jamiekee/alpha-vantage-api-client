package io.jamiekee.alphavantage.api;

import io.jamiekee.alphavantage.api.response.ErrorResponse;
import io.jamiekee.alphavantage.api.utils.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;

class Request {

  /**
   * This method performs the HTTP call to the Alpha Vantage API.
   * @param queryParameters Perform the API call with the given query parameters.
   * @return The API response in JSON.
   */
  static String sendRequest(String queryParameters) {
    ResponseEntity<String> responseEntity = new RestTemplate()
        .getForEntity(ALPHA_VANTAGE_URL + queryParameters, String.class);
    try {
      // Check if the response was an error response first.
      ErrorResponse errorResponse =
          JsonParser.toObject(responseEntity.getBody(), ErrorResponse.class);
      errorResponse.setQueryParameters(queryParameters);
      throw new HttpServerErrorException(
          responseEntity.getStatusCode(),
          errorResponse.toString(),
          responseEntity.getHeaders(),
          responseEntity.getBody().getBytes(),
          Charset.defaultCharset()
      );
    }
    catch (IOException e) {
      // ignore, this means the request was successful.
    }
    return responseEntity.getBody();
  }

  private final static String ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query?";
}
