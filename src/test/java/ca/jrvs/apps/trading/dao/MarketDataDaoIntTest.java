package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MarketDataDaoIntTest {
    private MarketDataDao marketDataDao;

    @Before
    public void setup() {
        HttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager();
        MarketDataConfig config = new MarketDataConfig();
        marketDataDao = new MarketDataDao(connectionManager, config);
    }

    @Test
    public void findIexQuoteByTicker() {
        String ticker = "AAPL";
        IexQuote quote = marketDataDao.findIexQuoteByTicker(ticker);
        assertEquals(ticker, quote.getSymbol());
    }

    @Test
    public void findIexQuoteByTickerList() {
        List<String> tickerList = new LinkedList<>();
        tickerList.addAll(Arrays.asList("AAPL", "FB"));
        List<IexQuote> iexQuotes = marketDataDao.findIexQuoteByTickers(tickerList);
        assertEquals(tickerList.size(), iexQuotes.size());
        assertTrue(tickerList.contains(iexQuotes.get(0).getSymbol()));
        assertTrue(tickerList.contains(iexQuotes.get(1).getSymbol()));
    }
}