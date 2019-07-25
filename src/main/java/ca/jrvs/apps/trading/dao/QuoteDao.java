package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class QuoteDao extends JdbcCrudDao<Quote, String> {

    private final static String TABLE_NAME = "quote";
    private final static String ID_NAME = "ticker";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Class getIdClass() {
        return String.class;
    }

    @Override
    public String getIdName() {
        return ID_NAME;
    }

    @Override
    public Class getEntityClass() {
        return Quote.class;
    }

    /**
     * Updates the data for a quote
     *
     * @param quote the new values for the quote
     */
    public void updateQuote(Quote quote) {
        String ticker = quote.getTicker();
        String sql = "UPDATE " + TABLE_NAME + " SET bid_size = ?, last_price = ?, bid_price = ?, ask_size = ?, " + "ask_price = ? where " + ID_NAME + " = ?";
        logger.debug(sql + ", " + quote.getBidSize() + ", " + quote.getLastPrice() + ", " + quote.getBidPrice() + ", " + quote.getAskSize() + ", " + quote.getAskPrice() + ", " + ticker);
        jdbcTemplate.update(sql, quote.getBidSize(), quote.getLastPrice(), quote.getBidPrice(), quote.getAskSize(), quote.getAskPrice(), ticker);
    }
}
