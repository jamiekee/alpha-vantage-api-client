package io.jamiekee.alphavantage.api.technical;

import io.jamiekee.alphavantage.api.configuration.AlphaVantageClientConfiguration;
import io.jamiekee.alphavantage.api.request.MissingRequiredQueryParameterException;
import io.jamiekee.alphavantage.api.utils.JsonParser;

import java.io.IOException;

import static io.jamiekee.alphavantage.api.Request.send;

public class TechnicalIndicatorClient {

    public TechnicalIndicatorClient(AlphaVantageClientConfiguration configuration) {
        this.configuration = configuration;
        JsonParser.addDeserializer(TechnicalIndicatorResult.class, new TechnicalIndicatorDeserializer());
    }

    public TechnicalIndicatorResult getTechnicalIndicator(TechnicalIndicator indicator, String symbol,
                                                          TechnicalInterval interval)
            throws MissingRequiredQueryParameterException, IOException {
        return getTechnicalIndicator(indicator, symbol, interval, -1, null);
    }

    public TechnicalIndicatorResult getTechnicalIndicator(TechnicalIndicator indicator, String symbol,
                                                          TechnicalInterval interval, int timePeriod,
                                                          TechnicalSeriesType seriesType)
            throws MissingRequiredQueryParameterException, IOException {
        String queryParameters = TechnicalIndicatorRequest.builder()
                .indicator(indicator)
                .symbol(symbol)
                .interval(interval)
                .timePeriod(timePeriod)
                .seriesType(seriesType)
                .build()
                .toQueryParameters();
        return send(queryParameters,  configuration.getApiKey(), TechnicalIndicatorResult.class);
    }

    private final AlphaVantageClientConfiguration configuration;
}
