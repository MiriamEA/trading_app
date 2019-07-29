package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    private MarketDataDao marketDataDao;
    private QuoteDao quoteDao;
    private QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService, QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.marketDataDao = marketDataDao;
        this.quoteDao = quoteDao;
        this.quoteService = quoteService;
    }

    @ApiOperation(value = "Show iexQuote", notes = "Show iexQuote for a given ticker")
    @GetMapping(path = "/iex/ticker/{ticker}")
    @ResponseStatus(HttpStatus.OK)
    public IexQuote getQuote(@PathVariable String ticker) {
        try {
            IexQuote iexQuote = marketDataDao.findIexQuoteByTicker(ticker);
            return iexQuote;
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Show the daily list", notes = "Show daily list for this trading system. (daily list = quote table)")
    @GetMapping(path = "dailyList")
    @ResponseStatus(HttpStatus.OK)
    public List<Quote> getDailyList() {
        try {
            List<Quote> list = quoteDao.getEverything();
            return list;
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Add a new ticker to the daily list (quote table)", notes = "Add a new ticker to the quote table, so traders can trade this security.")
    @PostMapping(path = "tickerId/{tickerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuote(@PathVariable String tickerId) {
        try {
            quoteService.initQuote(tickerId);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Update a quote in the quote table", notes = "Manually update a quote in the quote table.")
    @PutMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public void updateQuote(@RequestBody Quote quote) {
        try {
            quoteDao.updateQuote(quote);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Update all quotes in the quote table", notes = "Manually update all quotes in the quote table using IEX market data.")
    @PutMapping(path = "/IexMarketData")
    @ResponseStatus(HttpStatus.OK)
    public void updateMarketData() {
        try {
            quoteService.updateMarketData();
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}