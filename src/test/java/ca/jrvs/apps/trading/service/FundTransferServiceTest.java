package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.model.domain.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FundTransferServiceTest {

    @InjectMocks
    private FundTransferService fundTransferService;

    @Mock
    private AccountDao accountDao;
    private Account mockAccount;

    @Before
    public void setup() {
        mockAccount = new Account();
        mockAccount.setAmount(1000.0);
        mockAccount.setId(1);
        mockAccount.setTraderId(1);
    }

    @Test
    public void deposit() {
        when(accountDao.findByAccountId(1)).thenReturn(mockAccount);

        Account account = fundTransferService.deposit(1, 10.5);
        assertNotNull(account);
        assertEquals(1010.5, account.getAmount(), 0);
        assertEquals(1, account.getId(), 0);

        try {
            Account account1 = fundTransferService.deposit(null, 10.4);
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void withdraw() {
        when(accountDao.findByAccountId(1)).thenReturn(mockAccount);

        Account account = fundTransferService.withdraw(1, 99.5);
        assertNotNull(account);
        assertEquals(1, account.getId(), 0);
        assertEquals(900.5, account.getAmount(), 0);

        try {
            Account account1 = fundTransferService.withdraw(1, 1000.4);
            fail();
        } catch (Exception e) {

        }
    }
}