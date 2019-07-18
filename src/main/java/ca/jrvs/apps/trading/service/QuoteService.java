package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Transactional
@Service
public class QuoteService {

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    /**
     * Add a new ticker to the quote table. Skip existing ticker.
     *
     * @param ticker ticker/symbol
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public void initQuote(String ticker) {
        initQuotes(Collections.singletonList(ticker));
    }

    /**
     * Parses a string to a double. If the string is null the defaultValue will be returned.
     * @param s String to parse
     * @param defaultValue default value in case the String is null
     * @return double from string or default value
     */
    private static double parseDouble(String s, double defaultValue) {
        double result = defaultValue;
        if (s != null) {
            result = Double.parseDouble(s);
        }
        return result;
    }

    /**
     * Helper method. Map a IexQuote to a Quote entity.
     * Note: `iexQuote.getLatestPrice() == null` if the stock market is closed.
     * Make sure set a default value for number field(s).
     *
     * @param iexQuote iexQuote to convert
     * @return quote
     */
    public static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        Quote quote = new Quote();
        quote.setTicker(iexQuote.getSymbol());
        double askPrice = parseDouble(iexQuote.getIexAskPrice(), Double.MAX_VALUE);
        quote.setAskPrice(askPrice);
        int askSize = parseInt(iexQuote.getIexAskSize(), 0);
        quote.setAskSize(askSize);
        double bidPrice = parseDouble(iexQuote.getIexBidPrice(), 0);
        quote.setBidPrice(bidPrice);
        int bidSize = parseInt(iexQuote.getIexBidSize(), 0);
        quote.setBidSize(bidSize);
        double lastPrice = parseDouble(iexQuote.getLatestPrice(), Double.MAX_VALUE);
        quote.setLastPrice(lastPrice);
        return quote;
    }

    /**
     * Parses a string to an integer. If the string is null the defaultValue will be returned.
     *
     * @param s            String to parse
     * @param defaultValue default value in cas the String is null
     * @return integer from string or default value
     */
    private static int parseInt(String s, int defaultValue) {
        int result = defaultValue;
        if (s != null) {
            result = Integer.parseInt(s);
        }
        return result;
    }

    /**
     * Add a list of new tickers to the quote table. Skip existing ticker(s).
     *
     * @param tickers a list ofi tickers/symbols
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public void initQuotes(List<String> tickers) {
        if (tickers == null || tickers.isEmpty()) {
            throw new IllegalArgumentException("At least on ticker is required.");
        }

        List<IexQuote> iexQuotes = marketDataDao.findIexQuoteByTickers(tickers);
        for (IexQuote iexQuote : iexQuotes) {
            if (!quoteDao.getAllIds().contains(iexQuote.getSymbol())) {
                Quote quote = buildQuoteFromIexQuote(iexQuote);
                quoteDao.save(quote);
            }
        }
    }

    /**
     * Update quote table against IEX source
     *
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public void updateMarketData() {
        List<String> allIds = quoteDao.getAllIds();
        List<IexQuote> allIexQuotes = marketDataDao.findIexQuoteByTickers(allIds);
        for (IexQuote iexQuote : allIexQuotes) {
            Quote quote = buildQuoteFromIexQuote(iexQuote);
            quoteDao.updateQuote(quote);
        }
    }
}