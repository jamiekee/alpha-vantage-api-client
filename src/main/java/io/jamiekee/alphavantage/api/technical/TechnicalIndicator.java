package io.jamiekee.alphavantage.api.technical;

public enum TechnicalIndicator {

//  SMA(true),
//  EMA(true),
//  WMA(true),
//  DEMA(true),
//  TEMA(true),
//  TRIMA(true),
//  KAMA(true),
//  MAMA(true),
  VWAP(true),
  T3(true),
  MACD(true),
  MACDEXT(true),
  STOCH(true),
  STOCHF(true),
  RSI(true),
  STOCHRSI(true),
  WILLR(true),
  ADX(true),
  ADXR(true),
  APO(true),
  PPO(true),
  MOM(true),
  BOP(true),
  CCI(true),
  CMO(true),
  ROC(true),
  ROCR(true),
  AROON(true),
  AROONOSC(true),
  MFI(true),
  TRIX(true),
  ULTOSC(true),
  DX(true),
  MINUS_DI(true),
  PLUS_DI(true),
  MINUS_DM(true),
  PLUS_DM(true),
  BBANDS(true),
  MIDPOINT(true),
  MIDPRICE(true),
  SAR(true),
  TRANGE(true),
  ATR(true),
  NATR(true),
  AD(true),
  ADOSC(true),
  OBV(true),
  HT_TRENDLINE(true),
  HT_SINE(true),
  HT_TRENDMODE(true),
  HT_DCPERIOD(true),
  HT_DCPHASE(true),
  HT_PHASOR(true),

  FAMA(false);
  
  private boolean canRequest;

  TechnicalIndicator(boolean canRequest) {
    this.canRequest = canRequest;
  }

  public boolean canRequest() {
    return canRequest;
  }

}
