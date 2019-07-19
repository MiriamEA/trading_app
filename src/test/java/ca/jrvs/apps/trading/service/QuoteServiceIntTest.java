package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class QuoteServiceIntTest {

    private QuoteService quoteService;
    private QuoteDao quoteDao;

    @Before
    public void setup() {
        String jdbcUrl = "jdbc:postgresql://localhost/jrvstrading_test";
        String user = "postgres";
        String password = "password";

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        quoteDao = new QuoteDao(dataSource);
        quoteService = new QuoteService(quoteDao, new MarketDataDao(new BasicHttpClientConnectionManager(), new MarketDataConfig()));
    }

    @Test
    public void initQuote() {
        quoteService.initQuote("AA");
        List<String> allIds = quoteDao.getAllIds();
        assertTrue(allIds.contains("AA"));
    }

    @Test
    public void buildQuoteFromIexQuote() {
        IexQuote iexQuote = new IexQuote();
        iexQuote.setSymbol("HD");
        iexQuote.setIexAskPrice("23.2");
        iexQuote.setIexAskSize("34");
        iexQuote.setIexBidPrice("23");
        iexQuote.setIexBidSize("8463");
        Quote quote = quoteService.buildQuoteFromIexQuote(iexQuote);
        assertNotNull(quote);
        assertEquals("HD", quote.getTicker());
        assertEquals(23.2, quote.getAskPrice(), 0);
        assertEquals(34, (int) quote.getAskSize());
    }

    @Test
    public void initQuotes() {
        List<String> tickers = Arrays.asList("AA", "BA");
        quoteService.initQuotes(tickers);
        List<String> allIds = quoteDao.getAllIds();
        assertTrue(allIds.contains("AA"));
        assertTrue(allIds.contains("BA"));
    }

    @Test
    public void updateMarketData() {
    }

    @After
    public void cleanup() {
        List<String> ids = quoteDao.getAllIds();
        for (String id : ids) {
            quoteDao.deleteById(id);
        }
    }
}