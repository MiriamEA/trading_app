package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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

    @GetMapping(path = "/iex/ticker/{ticker}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IexQuote getQuote(@PathVariable String ticker) {
        try {
            IexQuote iexQuote = marketDataDao.findIexQuoteByTicker(ticker);
            return iexQuote;
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @GetMapping(path = "dailyList")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> getDailyList() {
        try {
            List<Quote> list = quoteDao.getEverything();
            return list;
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PostMapping(path = "tickerId/{tickerId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createQuote(String ticker) {
        try {
            quoteService.initQuote(ticker);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

}