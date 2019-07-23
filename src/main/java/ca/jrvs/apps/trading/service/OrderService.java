package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.OrderStatus;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import ca.jrvs.apps.trading.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static java.lang.Math.abs;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao, QuoteDao quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    /**
     * Execute a market order
     * - validate the order (e.g. size, and ticker)
     * - Create a securityOrder (for security_order table)
     * - Handle buy or sell order
     * - buy order : check account balance
     * - sell order: check position for the ticker/symbol
     * - (please don't forget to update securityOrder.status)
     * - Save and return securityOrder
     * NOTE: you will need to some helper methods (protected or private)
     *
     * @param orderDto market order
     * @return SecurityOrder from security_order table
     * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
     * @throws IllegalArgumentException                    for invalid input
     */
    public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {
        validateOrder(orderDto);

        String ticker = orderDto.getTicker();
        int orderSize = orderDto.getSize();
        int accountId = orderDto.getAccountId();
        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccountId(accountId);
        securityOrder.setSize(orderSize);
        securityOrder.setTicker(ticker);
        double price = quoteDao.findById(ticker).getAskPrice();
        securityOrder.setPrice(price);
        Account account = accountDao.findByAccountId(accountId);
        double totalPrice = price * abs(orderSize);

        if (orderSize > 0) {
            return executeBuying(totalPrice, securityOrder, account);
        } else {
            return executeSelling(totalPrice, securityOrder, account);
        }
    }

    /**
     * Checks that an order has a nonzero size and a nonempty ticker
     *
     * @param order must not be null
     * @throws IllegalArgumentException if size is 0, or if ticker is empty
     */
    private void validateOrder(MarketOrderDto order) {
        int orderSize = order.getSize();
        if (orderSize == 0) {
            throw new IllegalArgumentException("Order size cannot be 0.");
        }
        String ticker = order.getTicker();
        if (StringUtil.isEmpty(Collections.singletonList(ticker))) {
            throw new IllegalArgumentException("Ticker cannot be empty.");
        }
    }

    /**
     * Executes a buying order
     *
     * @param totalPrice    price for buying
     * @param securityOrder order to execute
     * @param account       account buying position
     * @return security order updated with status
     * @throws IllegalArgumentException if account has not enough money
     */
    private SecurityOrder executeBuying(double totalPrice, SecurityOrder securityOrder, Account account) {
        if (totalPrice > account.getAmount()) {
            securityOrder.setStatus(OrderStatus.CANCELED);
            securityOrderDao.save(securityOrder);
            throw new IllegalArgumentException("Insufficient fund. Reqired: " + totalPrice + ", available: " + account.getAmount());
        } else {
            securityOrder.setStatus(OrderStatus.FILLED);
            accountDao.updateAmountById(account.getAmount() - totalPrice, account.getId());
            securityOrderDao.save(securityOrder);
        }
        return securityOrder;
    }

    /**
     * Executes a selling order.
     * @param totalPrice price for selling
     * @param securityOrder order to sell
     * @param account account making the sell
     * @return security order with updated status
     * @throws IllegalArgumentException if account has not enough position
     */
    private SecurityOrder executeSelling(double totalPrice, SecurityOrder securityOrder, Account account) {
        Position position = positionDao.findByAccountIdAndTicker(account.getId(), securityOrder.getTicker());
        if (position.getPosition() < abs(securityOrder.getSize())) {
            securityOrder.setStatus(OrderStatus.CANCELED);
            securityOrderDao.save(securityOrder);
            throw new IllegalArgumentException("Not enough position, only " + position.getPosition() + " available.");
        } else {
            securityOrder.setStatus(OrderStatus.FILLED);
            accountDao.updateAmountById(account.getAmount() + totalPrice, account.getId());
            securityOrderDao.save(securityOrder);
        }
        return securityOrder;
    }
}