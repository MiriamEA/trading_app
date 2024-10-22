package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.After;
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
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Import(TestConfig.class)
public class QuoteDaoIntTest {

    @Autowired
    private QuoteDao quoteDao;

    @Autowired
    private DataSource dataSource;

    private Quote quote;
    private Quote quote1;

    @Before
    public void setup() {

        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new EncodedResource(new FileSystemResource("src/test/resources/schema.sql")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        quote = new Quote();
        quote.setTicker("AAPL");
        quote.setLastPrice(34.234);
        quote.setBidSize(3);
        quote.setBidPrice(2.3);
        quote.setAskSize(53);
        quote.setAskPrice(3.2);

        quote1 = new Quote();
        quote1.setTicker("FRD");
        quote1.setLastPrice(23.2);
        quote1.setBidSize(4);
        quote1.setAskPrice(344.3);
        quote1.setBidPrice(322.73);
        quote1.setAskSize(10);
    }

    @Test
    public void saveFindExists() {
        List<String> ids = quoteDao.getAllIds();
        for (String id : ids) {
            quoteDao.deleteById(id);
        }
    }

    @Test
    public void getAllIds() {
        quoteDao.save(quote1);
        quoteDao.save(quote);
        List<String> ids = quoteDao.getAllIds();
        assertNotNull(ids);
        assertEquals(2, ids.size());
        assertTrue(ids.contains(quote1.getTicker()));
        assertTrue(ids.contains(quote.getTicker()));
    }

    @Test
    public void deleteById() {
        quoteDao.save(quote);
        boolean exists = quoteDao.existsById(quote.getTicker());
        assertTrue(exists);
        quoteDao.deleteById(quote.getTicker());
        boolean exist = quoteDao.existsById(quote.getTicker());
        assertFalse(exist);
    }

    @Test
    public void updateQuote() {
    }

    @After
    public void cleanup() {
        try {
            quoteDao.deleteById(quote.getTicker());
        } catch (ResourceNotFoundException e) {
        }
        try {
            quoteDao.deleteById(quote1.getTicker());
        } catch (ResourceNotFoundException e) {
        }
    }
}