import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import configuration.AlphaVantageClientConfiguration;
import configuration.DataType;
import interfaces.IAlphaVantageClient;
import java.io.IOException;
import timeseries.TimeSeriesRequest;
import timeseries.TimeSeriesResult;
import timeseries.TimeSeriesResultDeserializer;

public class AlphaVantageClient implements IAlphaVantageClient {

  public AlphaVantageClient(AlphaVantageClientConfiguration configuration) {
    this.configuration = configuration;
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addDeserializer(TimeSeriesResult.class, new TimeSeriesResultDeserializer());
    objectMapper.registerModule(simpleModule);
  }

  @Override
  public TimeSeriesResult getTimeSeries(TimeSeriesRequest request)
      throws IOException {
    String pathVariables = request.toHttpPathVariables();
    pathVariables += "&datatype=" + DataType.JSON;
    pathVariables += "&apikey=" + configuration.getApiKey();
    return objectMapper.readValue(Request.sendRequest(pathVariables), TimeSeriesResult.class);
  }

  private AlphaVantageClientConfiguration configuration;
  private ObjectMapper objectMapper = new ObjectMapper();
}
