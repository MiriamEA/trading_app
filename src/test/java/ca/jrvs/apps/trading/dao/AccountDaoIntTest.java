package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@Import(TestConfig.class)
public class AccountDaoIntTest {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private DataSource dataSource;

    @Before
    public void setup() {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new EncodedResource(new FileSystemResource("/home/centos/dev/jrvs/bootcamp/trading_app/sql_ddl/schema.sql")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Trader trader = new Trader();
        trader.setDob(new Date(2000, 1, 1));
        trader.setLastName("Doe");
        trader.setFirstName("John");
        trader.setCountry("Canada");
        trader.setEmail("johndoe@gmail.com");
        traderDao.save(trader);

        //accountDao = new AccountDao(dataSource);
        Account account = new Account();
        account.setTraderId(1);
        account.setId(1);
        account.setAmount(100.0);
        accountDao.save(account);
    }

    @Test
    public void findByAccountId() {
        Account account = accountDao.findByAccountId(1);
        assertNotNull(account);
        assertEquals(1, account.getId(), 0);
        assertEquals(1, account.getTraderId(), 0);
        assertEquals(100, account.getAmount(), 0);
    }

    @Test
    public void findByTraderId() {
        Account account = accountDao.findByTraderId(1);
        assertNotNull(account);
        assertEquals(1, account.getId(), 0);
        assertEquals(1, account.getTraderId(), 0);
        assertEquals(100, account.getAmount(), 0);
    }

    @Test
    public void updateAmountById() {
        accountDao.updateAmountById(1.5, 1);
        Account account = accountDao.findByAccountId(1);
        assertEquals(1, account.getId(), 0);
        assertEquals(1.5, account.getAmount(), 0);
    }
}