package io.jamiekee.alphavantage.api.timeseries;

import io.jamiekee.alphavantage.api.request.OutputSize;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TimeSeriesRequestTest {

  @Test
  void allRequestFieldsArePresentInQueryParameters()
      throws MissingRequiredQueryParameterException {
    TimeSeriesRequest request = TimeSeriesRequest.builder()
        .timeSeriesFunction(TimeSeriesFunction.INTRADAY)
        .intradayInterval(IntradayInterval.ONE_MINUTE)
        .outputSize(OutputSize.COMPACT)
        .symbol("TEST")
        .build();

    String queryParameters = request.toQueryParameters();

    assertEquals(
        queryParameters,
        "function=TIME_SERIES_INTRADAY&symbol=TEST&outputsize=COMPACT&interval=1min"
    );
  }

  @Test
  void optionalQueryParametersAreNotIncludedInQueryParameters()
      throws MissingRequiredQueryParameterException {
    TimeSeriesRequest request = TimeSeriesRequest.builder()
        .timeSeriesFunction(TimeSeriesFunction.DAILY)
        .symbol("TEST")
        .build();

    String queryParameters = request.toQueryParameters();

    assertEquals(
        queryParameters,
        "function=TIME_SERIES_DAILY&symbol=TEST"
    );
  }

  @Test
  void functionMissingThrowsMissingRequiredQueryParameterException()
      throws MissingRequiredQueryParameterException {
    TimeSeriesRequest request = TimeSeriesRequest.builder()
        .symbol("TEST")
        .build();

    MissingRequiredQueryParameterException exception = assertThrows(
        MissingRequiredQueryParameterException.class, request::toQueryParameters
    );

    assertEquals(
        exception.getMessage(),
        "TimeSeriesFunction is a required parameter and the request could not be sent."
    );
  }

  @Test
  void symbolMissingThrowsMissingRequiredQueryParameterException()
      throws MissingRequiredQueryParameterException {
    TimeSeriesRequest request = TimeSeriesRequest.builder()
        .timeSeriesFunction(TimeSeriesFunction.DAILY)
        .build();

    MissingRequiredQueryParameterException exception = assertThrows(
        MissingRequiredQueryParameterException.class, request::toQueryParameters
    );

    assertEquals(
        exception.getMessage(),
        "Symbol is a required parameter and the request could not be sent."
    );
  }

  @Test
  void intervalMissingOnIntradayRequestThrowsMissingRequiredQueryParameterException()
      throws MissingRequiredQueryParameterException {
    TimeSeriesRequest request = TimeSeriesRequest.builder()
        .timeSeriesFunction(TimeSeriesFunction.INTRADAY)
        .symbol("TEST")
        .build();

    MissingRequiredQueryParameterException exception = assertThrows(
        MissingRequiredQueryParameterException.class, request::toQueryParameters
    );

    assertEquals(
        exception.getMessage(),
        "IntradayInterval is a required parameter " +
        "when using the TIME_SERIES_INTRADAY query parameter " +
        "and the request could not be sent."
    );
  }

}