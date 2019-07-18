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
        updateColumn(quote.getAskPrice(), "ask_price", ticker);
        updateColumn(quote.getAskSize(), "ask_size", ticker);
        updateColumn(quote.getBidPrice(), "bid_price", ticker);
        updateColumn(quote.getBidSize(), "bid_size", ticker);
        updateColumn(quote.getLastPrice(), "last_price", ticker);
    }

    /**
     * Updates a column in one row in the quote table
     * @param value the new value
     * @param columnName name of the column to update
     * @param ticker id of row to update
     */
    private void updateColumn(Number value, String columnName, String ticker) {
        String sql = "UPDATE " + TABLE_NAME + " SET " + columnName + " =? where " + ID_NAME + " = '" + ticker + "'";
        logger.info("SQL statment: " + sql + " ," + value);
        jdbcTemplate.update(sql, value);
    }
}
