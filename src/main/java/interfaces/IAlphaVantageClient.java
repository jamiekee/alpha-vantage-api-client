package interfaces;

import java.io.IOException;
import timeseries.TimeSeriesRequest;
import timeseries.TimeSeriesResult;

public interface IAlphaVantageClient {

  TimeSeriesResult getTimeSeries(TimeSeriesRequest request) throws IOException;

}
