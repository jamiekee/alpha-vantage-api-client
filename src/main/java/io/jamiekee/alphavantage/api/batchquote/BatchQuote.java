package io.jamiekee.alphavantage.api.batchquote;

import lombok.Data;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BatchQuote {

  public void setTimestamp(String date)
      throws ParseException {
    this.timestamp = ZonedDateTime.parse(date, TIMESTAMP_FORMATTER);
  }

  private String symbol;
  private double open;
  private double high;
  private double low;
  private double price;
  private double volume;
  private ZonedDateTime timestamp;
  private String currency;

  private static final DateTimeFormatter TIMESTAMP_FORMATTER
      = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss zzz");
}
