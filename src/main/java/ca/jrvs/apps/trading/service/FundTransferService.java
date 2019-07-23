package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.model.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FundTransferService {

    private AccountDao accountDao;

    @Autowired
    public FundTransferService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * Deposit a fund to the account which is associated with the id
     *
     * @param id must not be null
     * @param fund     found amount (can't be 0)
     * @return updated Account object
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if id is not found in db
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public Account deposit(Integer id, Double fund) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        if (fund <= 0) {
            throw new IllegalArgumentException("Invalid fund");
        }
        Account account = accountDao.findById(id);
        account.setAmount(account.getAmount() + fund);
        accountDao.updateAmountById(account.getAmount(), account.getId());
        return account;
    }

    /**
     * Withdraw a fund from the account which is associated with the id
     *
     * @param id must not be null
     * @param fund     amount can't be 0
     * @return updated Account object
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if id is not found in db
     * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
     * @throws IllegalArgumentException                           for invalid input
     */
    public Account withdraw(Integer id, Double fund) {
        if (fund <= 0) {
            throw new IllegalArgumentException("Invalid fund");
        }
        Account account = accountDao.findById(id);
        double currentAmount = account.getAmount();
        if (fund > currentAmount) {
            throw new IllegalArgumentException("Insufficient funds in account. Current amount is " + currentAmount);
        }
        account.setAmount(currentAmount - fund);
        accountDao.updateAmountById(account.getAmount(), account.getId());
        return account;
    }
}