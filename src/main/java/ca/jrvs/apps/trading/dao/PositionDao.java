package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.util.StringUtil;
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
     * @throws IllegalArgumentException                    if input is null
     * @throws org.springframework.dao.DataAccessException if account cannot be found in db
     */
    public List<Position> findAllByAccountId(Integer accountId) {
        validateAccountId(accountId);
        String sql = "select * from " + TABLE_NAME + " where account_id =?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Position.class), accountId);
    }

    private void validateAccountId(Integer accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("Acount id cannot be null.");
        }
    }

    public Position findByAccountIdAndTicker(Integer accountId, String ticker) {
        validateAccountId(accountId);
        if (StringUtil.isEmpty(Collections.singletonList(ticker))) {
            throw new IllegalArgumentException("Ticker cannot be empty.");
        }
        String sql = "select * from " + TABLE_NAME + " where account_id =? and ticker =?";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Position.class), accountId, ticker);
    }
}
