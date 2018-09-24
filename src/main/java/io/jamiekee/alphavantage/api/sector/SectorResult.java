package io.jamiekee.alphavantage.api.sector;

import io.jamiekee.alphavantage.api.response.MetaData;
import lombok.Data;

import java.util.Map;

@Data
public class SectorResult {
  private MetaData metaData;
  private Map<SectorTime, Map<Sector, Double>> timedSectorPerformances;
}
