package timeseries;

import java.util.Date;
import java.util.Map;
import lombok.Data;
import response.MetaData;

@Data
public class TimeSeriesResult {
  private MetaData metaData;
  private Map<Date, TimeSeries> timeSeries;
}
