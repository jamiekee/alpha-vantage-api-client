package io.jamiekee.alphavantage.api.sector;

import lombok.Data;

@Data
public class SectorQuote {
  private Sector sector;
  private double percentage;
}
