package io.jamiekee.alphavantage.api.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jamiekee.alphavantage.api.AlphaVantageClient;
import io.jamiekee.alphavantage.api.Interval;
import io.jamiekee.alphavantage.api.configuration.AlphaVantageClientConfiguration;
import io.jamiekee.alphavantage.api.currencyexchange.CurrencyExchangeResult;
import io.jamiekee.alphavantage.api.foreignexchange.ForeignExchangeFunction;
import io.jamiekee.alphavantage.api.foreignexchange.ForeignExchangeResult;
import io.jamiekee.alphavantage.api.request.MissingRequiredQueryParameterException;
import io.jamiekee.alphavantage.api.sector.SectorResult;
import io.jamiekee.alphavantage.api.timeseries.SuperSecretConstants;
import io.jamiekee.alphavantage.api.timeseries.TimeSeriesResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AlphaVantageClientIT {

    private final AlphaVantageClientConfiguration alphaVantageClientConfiguration =
            new AlphaVantageClientConfiguration(SuperSecretConstants.API_KEY);
    private AlphaVantageClient alphaVantageClient;

    @BeforeAll
    public void setup() {
        alphaVantageClient = new AlphaVantageClient(alphaVantageClientConfiguration);
    }

    @AfterEach
    public void pause() {
        // NEED TO DO THIS SO NOT TO HIT THE API LIMIT
        // SORRY :(
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void timeSeries() throws MissingRequiredQueryParameterException, IOException {
        TimeSeriesResult timeSeriesResult = alphaVantageClient.getTimeSeries(Interval.SIXTY_MINUTES, "GOOGL");

        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(timeSeriesResult));

        timeSeriesResult.getTimeSeries()
                .forEach((key, value) -> System.out.println(
                        value.getDividendAmount()));
    }

    @Test
    void currencyExchange() throws MissingRequiredQueryParameterException, IOException {
        CurrencyExchangeResult currencyExchange = alphaVantageClient.getCurrencyExchange("USD", "GBP");

        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(currencyExchange));

        System.out.println(currencyExchange.getQuote().getExchangeRate());
    }

    @Test
    void foreignExchange() throws MissingRequiredQueryParameterException, IOException {
        ForeignExchangeResult foreignExchange = alphaVantageClient.getForeignExchange(
                ForeignExchangeFunction.FX_DAILY,
                "USD",
                "GBP");

        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(foreignExchange));

        System.out.println(foreignExchange.getForeignExchangeQuotes().values());
    }

    @Test
    void sectorPerformances() throws IOException {
        SectorResult sectorPerformances = alphaVantageClient.getSectorPerformances();

        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(sectorPerformances));
    }

}