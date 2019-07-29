package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.*;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private AccountDao accountDao;
    private Account mockAccount;

    @Mock
    private SecurityOrderDao securityOrderDao;

    @Mock
    private QuoteDao quoteDao;
    private Quote mockQuote;

    @Mock
    private PositionDao positionDao;
    private Position mockPosition;

    @Before
    public void setup() {
        mockAccount = new Account();
        mockAccount.setTraderId(10);
        mockAccount.setId(10);
        mockAccount.setAmount(1000.0);

        mockQuote = new Quote();
        mockQuote.setTicker("AAPL");
        mockQuote.setAskPrice(8.0);
        mockQuote.setAskSize(10);
        mockQuote.setBidPrice(8.0);
        mockQuote.setBidSize(10);
        mockQuote.setLastPrice(8.0);

        mockPosition = new Position();
        mockPosition.setAccountId(10);
        mockPosition.setPosition(5);
        mockPosition.setTicker("AAPL");
    }

    @Test
    public void executeMarketOrderBuying() {
        MarketOrderDto order = new MarketOrderDto();
        order.setTicker("AAPL");
        order.setSize(100);
        order.setAccountId(10);
        when(accountDao.findByAccountId(10)).thenReturn(mockAccount);
        when(quoteDao.findById(any())).thenReturn(mockQuote);

        SecurityOrder securityOrder = orderService.executeMarketOrder(order);
        assertEquals(100, securityOrder.getSize(), 0);
        assertEquals(10, securityOrder.getAccountId(), 0);
        assertEquals("AAPL", securityOrder.getTicker());
        assertEquals(OrderStatus.FILLED, securityOrder.getStatus());

        order.setSize(100000);
        try {
            SecurityOrder securityOrder1 = orderService.executeMarketOrder(order);
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void executeMarketOrderSelling() {
        MarketOrderDto order = new MarketOrderDto();
        order.setSize(-10);
        order.setAccountId(10);
        order.setTicker("AAPL");
        when(accountDao.findByAccountId(10)).thenReturn(mockAccount);
        when(quoteDao.findById(any())).thenReturn(mockQuote);
        when(positionDao.findByAccountIdAndTicker(10, "AAPL")).thenReturn(mockPosition);

        try {
            SecurityOrder securityOrder = orderService.executeMarketOrder(order);
            fail();
        } catch (Exception e) {
        }

        order.setSize(-1);
        SecurityOrder securityOrder = orderService.executeMarketOrder(order);
        assertEquals(-1, securityOrder.getSize(), 0);
        assertEquals("AAPL", securityOrder.getTicker());
        assertEquals(OrderStatus.FILLED, securityOrder.getStatus());
        assertEquals(10, securityOrder.getAccountId(), 0);
    }
}