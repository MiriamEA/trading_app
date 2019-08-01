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

@Transactional
@Service
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
     *
     * @param orderDto market order
     * @return SecurityOrder from security_order table
     * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
     * @throws IllegalArgumentException                    for invalid input
     */
    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {
        validateOrder(orderDto);

        String ticker = orderDto.getTicker();
        int orderSize = orderDto.getSize();
        int accountId = orderDto.getAccountId();
        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccountId(accountId);
        securityOrder.setSize(orderSize);
        securityOrder.setTicker(ticker);
        Account account = accountDao.findByAccountId(accountId);

        if (orderSize > 0) {
            return executeBuying(securityOrder, account);
        } else {
            return executeSelling(securityOrder, account);
        }
    }

    /**
     * Checks that an order is not null, has a nonzero size and a nonempty ticker
     *
     * @param order must not be null
     * @throws IllegalArgumentException if size is 0, or if ticker is empty
     */
    private void validateOrder(MarketOrderDto order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }
        int orderSize = order.getSize();
        if (orderSize == 0) {
            throw new IllegalArgumentException("Order size cannot be 0.");
        }
        order.setTicker(order.getTicker().trim());
        String ticker = order.getTicker();
        if (StringUtil.isEmpty(Collections.singletonList(ticker))) {
            throw new IllegalArgumentException("Ticker cannot be empty.");
        }
    }

    /**
     * Executes a buying order
     *
     * @param securityOrder order to execute
     * @param account       account buying position
     * @return security order updated with status
     * @throws IllegalArgumentException if account has not enough money
     */
    private SecurityOrder executeBuying(SecurityOrder securityOrder, Account account) {
        double price = quoteDao.findById(securityOrder.getTicker()).getAskPrice();
        securityOrder.setPrice(price);
        double totalPrice = price * securityOrder.getSize();
        if (totalPrice > account.getAmount()) {
            securityOrder.setStatus(OrderStatus.CANCELLED);
            securityOrder.setNotes("Insufficient fund.");
            securityOrderDao.save(securityOrder);
            throw new IllegalArgumentException("Insufficient fund. Required: " + totalPrice + ", available: " + account.getAmount());
        } else {
            securityOrder.setStatus(OrderStatus.FILLED);
            accountDao.updateAmountById(account.getAmount() - totalPrice, account.getId());
            securityOrderDao.save(securityOrder);
        }

        return securityOrder;
    }

    /**
     * Executes a selling order.
     *
     * @param securityOrder order to sell
     * @param account       account making the sell
     * @return security order with updated status
     * @throws IllegalArgumentException if account has not enough position
     */
    private SecurityOrder executeSelling(SecurityOrder securityOrder, Account account) {
        double price = quoteDao.findById(securityOrder.getTicker()).getBidPrice();
        securityOrder.setPrice(price);
        double totalPrice = price * abs(securityOrder.getSize());
        Position position = positionDao.findByAccountIdAndTicker(account.getId(), securityOrder.getTicker());
        if (position.getPosition() < abs(securityOrder.getSize())) {
            securityOrder.setStatus(OrderStatus.CANCELLED);
            securityOrder.setNotes("Insufficient position.");
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