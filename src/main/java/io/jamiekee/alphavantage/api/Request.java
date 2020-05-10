package io.jamiekee.alphavantage.api;

import io.jamiekee.alphavantage.api.configuration.DataType;
import io.jamiekee.alphavantage.api.response.ErrorResponse;
import io.jamiekee.alphavantage.api.utils.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;

public class Request {


  /**
   * Append the API Key and the DataType to the query parameters and send the
   * API request to Alpha Vantage.
   * @param queryParameters The query parameter string from the Request.
   * @param apiKey The API Key to append to the request.
   * @param resultObject The expected result object from the API.
   * @return The Result of the API request.
   */
  public static <T> T send(String queryParameters, String apiKey, Class<T> resultObject)
          throws IOException {
    queryParameters += "&datatype=" + DataType.JSON;
    queryParameters += "&apikey=" + apiKey;
    return JsonParser.toObject(sendRequest(queryParameters), resultObject);
  }

  /**
   * This method performs the HTTP call to the Alpha Vantage API.
   * @param queryParameters Perform the API call with the given query parameters.
   * @return The API response in JSON.
   */
  private static String sendRequest(String queryParameters) {
    String url = ALPHA_VANTAGE_URL + queryParameters;
    System.out.println("Sending API Request to: " + url);
    ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(url, String.class);

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
