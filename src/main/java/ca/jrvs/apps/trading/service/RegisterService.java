package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegisterService {

    private TraderDao traderDao;
    private AccountDao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;

    @Autowired
    public RegisterService(TraderDao traderDao, AccountDao accountDao, PositionDao positionDao, SecurityOrderDao securityOrderDao) {
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    /**
     * Create a new trader and initialize a new account with 0 amount.
     *
     * @param trader trader info
     * @return traderAccountView for the new trader
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException                    for invalid input
     */
    public TraderAccountView createTraderAndAccount(Trader trader) {
        validateTrader(trader);

        trader = traderDao.save(trader);
        Account account = new Account(trader.getId());
        account = accountDao.save(account);
        return new TraderAccountView(account, trader);
    }

    /**
     * Checks whether all fields of a trader at not null and whether email is valid
     *
     * @param trader trader to check
     * @throws IllegalArgumentException if trader is null, a field of trader is null, email is invalid
     */
    private void validateTrader(Trader trader) {
        if (trader == null) {
            throw new IllegalArgumentException("Trader cannot be null.");
        }
        boolean firstNameIsSet = trader.getFirstName() != null;
        boolean lastNameIsSet = trader.getLastName() != null;
        boolean countryIsSet = trader.getCountry() != null;
        boolean dobIsSet = trader.getDob() != null;
        boolean emailIsSet = trader.getEmail() != null;
        boolean traderNotEmpty = firstNameIsSet && lastNameIsSet && countryIsSet && dobIsSet && emailIsSet;
        if (!traderNotEmpty) {
            throw new IllegalArgumentException("All attributes of a trader must be set.");
        }

        boolean isValidEmail = EmailValidator.getInstance().isValid(trader.getEmail());
        if (!isValidEmail) {
            throw new IllegalArgumentException("Email is invalid.");
        }
    }

    /**
     * A trader can be deleted iff no open position and no cash balance.
     *
     * @param traderId must not be {@literal null}
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if data cannot be found in db
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public void deleteTraderById(Integer traderId) {
        if (traderId == null) {
            throw new IllegalArgumentException("TraderId cannot be null.");
        }
        Account account = accountDao.findByTraderId(traderId);
        int accountId = account.getId();
        if (account.getAmount() != 0) {
            throw new IllegalArgumentException("Trader " + traderId + " still has money and cannot be deleted.");
        }
        List<Position> positions = positionDao.findAllByAccountId(accountId);
        List<Position> openPositions = positions.stream().filter(p -> p.getPosition() > 0).collect(Collectors.toList());
        if (!openPositions.isEmpty()) {
            throw new IllegalArgumentException("Trader " + traderId + " cannot be deleted because of open positions");
        }

        securityOrderDao.deleteByAccountId(accountId);
        accountDao.deleteById(accountId);
        traderDao.deleteById(traderId);
    }
}