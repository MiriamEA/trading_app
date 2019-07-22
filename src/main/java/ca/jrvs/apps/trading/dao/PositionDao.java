package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;

import javax.sql.DataSource;

public class PositionDao {


    private final static String TABLE_NAME = "position";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public PositionDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
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
        if (accountId == null) {
            throw new IllegalArgumentException("Acount id cannot be null.");
        }
        String sql = "select * from " + TABLE_NAME + " where account_id =?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Position.class), accountId);
    }
}
