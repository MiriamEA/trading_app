package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Import(TestConfig.class)
public class MarketDataDaoIntTest {

    @Autowired
    private MarketDataDao marketDataDao;

    @Test
    public void findIexQuoteByTicker() {
        String ticker = "MCD";
        IexQuote iexQuote = marketDataDao.findIexQuoteByTicker(ticker);
        assertNotNull(iexQuote);
        assertEquals(ticker, iexQuote.getSymbol());

        try {
            IexQuote iexQuote1 = marketDataDao.findIexQuoteByTicker("MCD2");
            fail();
        } catch (ResourceNotFoundException e) {

        }
    }

    @Test
    public void findIexQuoteByTickers() {
        List<String> tickers = Arrays.asList("MCD", "AAPL", "ACDVF");
        List<IexQuote> iexQuotes = marketDataDao.findIexQuoteByTickers(tickers);
        assertNotNull(iexQuotes);
        assertEquals(3, iexQuotes.size());
        List<String> quoteTickers = new LinkedList<>();
        for (IexQuote q : iexQuotes) {
            quoteTickers.add(q.getSymbol());
        }
        assertTrue(quoteTickers.contains("AAPL"));
        assertTrue(quoteTickers.contains("MCD"));
        assertTrue(quoteTickers.contains("ACDVF"));

        List<String> tickers1 = Arrays.asList("AAPL", "MCD2");
        try {
            List<IexQuote> result = marketDataDao.findIexQuoteByTickers(tickers1);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }
}