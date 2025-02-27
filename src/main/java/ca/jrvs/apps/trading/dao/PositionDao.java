package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Repository
public class PositionDao {

    private final static String TABLE_NAME = "position";
    private final Logger logger = LoggerFactory.getLogger(PositionDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PositionDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Finds all positions for a given account
     *
     * @param accountId id of account
     * @return list containing all positions for given account
     * @throws IllegalArgumentException  if input is null
     * @throws ResourceNotFoundException if accountId does not exist in db
     * @throws java.sql.SQLException     if sql execution fails
     */
    public List<Position> findAllByAccountId(Integer accountId) {
        validateAccountId(accountId);
        String sql = "select * from " + TABLE_NAME + " where account_id =?";
        logger.debug(sql + ", " + accountId);
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Position.class), accountId);
    }

    /**
     * Checks if an accountId is null
     *
     * @param accountId id to check
     * @throws IllegalArgumentException if accountId is null
     */
    private void validateAccountId(Integer accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("Acount id cannot be null.");
        }
    }

    /**
     * Finds the position for a given account and a given ticker
     *
     * @param accountId id of account
     * @param ticker    ticker
     * @return Position
     * @throws IllegalArgumentException  if accountId is null, or ticker is empty
     * @throws java.sql.SQLException     if sql execution fails
     * @throws ResourceNotFoundException if accountId or ticker do not exist in db
     */
    public Position findByAccountIdAndTicker(Integer accountId, String ticker) {
        validateAccountId(accountId);
        if (StringUtil.isEmpty(Collections.singletonList(ticker))) {
            throw new IllegalArgumentException("Ticker cannot be empty.");
        }
        String sql = "select * from " + TABLE_NAME + " where account_id =? and ticker =?";
        logger.debug(sql + ", " + accountId + ", " + ticker);
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Position.class), accountId, ticker);
    }
}
