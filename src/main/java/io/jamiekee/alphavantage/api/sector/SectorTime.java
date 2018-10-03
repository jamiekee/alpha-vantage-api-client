package io.jamiekee.alphavantage.api.sector;

public enum SectorTime {

  REAL_TIME("Real-Time Performance"),
  ONE_DAY("1 Day Performance"),
  FIVE_DAY("5 Day Performance"),
  ONE_MONTH("1 Month Performance"),
  THREE_MONTH("3 Month Performance"),
  YEAR_TO_DATE("Year-to-Date (YTD) Performance"),
  ONE_YEAR("1 Year Performance"),
  THREE_YEAR("3 Year Performance"),
  FIVE_YEAR("5 Year Performance"),
  TEN_YEAR("10 Year Performance"),
  UNKNOWN("Failed to Parse");

  SectorTime(String fieldName) {
    this.fieldName = fieldName;
  }

  /**
   * Get the SectorTime from the String contained in the JSON.
   * @param fieldName The JSON field name.
   * @return The respective SectorTime.
   */
  static SectorTime fromFieldName(String fieldName) {
    for (SectorTime sectorTime : SectorTime.values()) {
      if (sectorTime.getFieldName().equals(fieldName)) {
        return sectorTime;
      }
    }
    return SectorTime.UNKNOWN;
  }

  private String getFieldName() {
    return fieldName;
  }

  String fieldName;

}
