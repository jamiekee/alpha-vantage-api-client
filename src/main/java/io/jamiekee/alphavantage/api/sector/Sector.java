package io.jamiekee.alphavantage.api.sector;

public enum Sector {

  TELECOMMUNICATION_SERVICES("Telecommunication Services"),
  CONSUMER_STAPLES("Consumer Staples"),
  FINANCIALS("Financials"),
  INDUSTRIALS("Industrials"),
  UTILITIES("Utilities"),
  HEALTH_CARE("Health Care"),
  REAL_ESTATE("Real Estate"),
  MATERIALS("Materials"),
  ENERGY("Energy"),
  CONSUMER_DISCRETIONARY("Consumer Discretionary"),
  INFORMATION_TECHNOLOGY("Information Technology"),
  UNKNOWN("Failed to Parse");

  Sector(String fieldName) {
    this.fieldName = fieldName;
  }

  /**
   * Get the Sector from the String contained in the JSON.
   * @param fieldName The JSON field name.
   * @return The respective Sector.
   */
  static Sector fromFieldName(String fieldName) {
    for (Sector sector : Sector.values()) {
      if (sector.getFieldName().equals(fieldName)) {
        return sector;
      }
    }
    return Sector.UNKNOWN;
  }

  private String getFieldName() {
    return fieldName;
  }

  String fieldName;

}
