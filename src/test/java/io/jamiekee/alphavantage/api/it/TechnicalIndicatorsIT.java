package io.jamiekee.alphavantage.api.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jamiekee.alphavantage.api.AlphaVantageClient;
import io.jamiekee.alphavantage.api.configuration.AlphaVantageClientConfiguration;
import io.jamiekee.alphavantage.api.request.MissingRequiredQueryParameterException;
import io.jamiekee.alphavantage.api.technicalindicator.TechnicalIndicator;
import io.jamiekee.alphavantage.api.technicalindicator.TechnicalIndicatorResult;
import io.jamiekee.alphavantage.api.technicalindicator.TechnicalInterval;
import io.jamiekee.alphavantage.api.technicalindicator.TechnicalSeriesType;
import io.jamiekee.alphavantage.api.timeseries.SuperSecretConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
public class TechnicalIndicatorsIT {

    private final AlphaVantageClientConfiguration alphaVantageClientConfiguration =
            new AlphaVantageClientConfiguration(SuperSecretConstants.API_KEY);
    private AlphaVantageClient alphaVantageClient;

    @BeforeAll
    public void setup() {
        alphaVantageClient = new AlphaVantageClient(alphaVantageClientConfiguration);
    }

    @Test
    void technicalIndicatorResult()
        throws IOException, MissingRequiredQueryParameterException, InterruptedException {
        TechnicalIndicatorResult technicalIndicatorResult = null;
        for (TechnicalIndicator value : TechnicalIndicator.values()) {
            System.out.println("Testing Technical Indicator: " + value);
            if (!value.canRequest()) {
                continue;
            }
            if (value.equals(TechnicalIndicator.VWAP)) {
                technicalIndicatorResult = alphaVantageClient.getTechnicalIndicatorClient().getTechnicalIndicator(
                    value,
                    "IBM",
                    TechnicalInterval.FIFTEEN_MINUTES);
            }
            else {
                technicalIndicatorResult = alphaVantageClient
                    .getTechnicalIndicatorClient().getTechnicalIndicator(
                            value, "IBM", TechnicalInterval.WEEKLY, 10,
                        TechnicalSeriesType.OPEN);
            }
            Thread.sleep(20000);
        }


        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(technicalIndicatorResult));
    }

    @Test
    void technicalIndicatorResultSpecificProblemTest()
        throws MissingRequiredQueryParameterException, IOException {
        TechnicalIndicatorResult technicalIndicatorResult = alphaVantageClient
            .getTechnicalIndicatorClient().getTechnicalIndicator(
                    TechnicalIndicator.T3, "IBM", TechnicalInterval.WEEKLY,
                        10, TechnicalSeriesType.OPEN);

        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(technicalIndicatorResult));
    }
}
