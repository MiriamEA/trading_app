package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AccountDao extends JdbcCrudDao<Account, Integer> {

    private final static String TABLE_NAME = "account";
    private final static String ID_NAME = "id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public AccountDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_NAME);
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
        return Integer.class;
    }

    @Override
    public String getIdName() {
        return ID_NAME;
    }

    @Override
    public Class getEntityClass() {
        return Account.class;
    }

    /**
     * Retrieves an account by its id
     *
     * @param accountId id of account
     * @return account with the given account id
     * @throws java.sql.SQLException     if sql execution failed
     * @throws ResourceNotFoundException if no entity is found in db
     */
    public Account findByAccountId(int accountId) {
        return super.findById(ID_NAME, accountId, Account.class, true);
    }

    /**
     * Retrieves an account by its traderId.
     *
     * @param traderId id of trader
     * @return account associated with given trader id
     * @throws java.sql.SQLException     if sql execution failed.
     * @throws ResourceNotFoundException if no entity is found in db
     */
    public Account findByTraderId(int traderId) {
        return super.findById("trader_id", traderId, Account.class, false);
    }

    /**
     * Updates account information in db
     *
     * @param amount    the new amount to save
     * @param accountId id of account
     * @throws java.sql.SQLException if sql execution failed.
     */
    public void updateAmountById(double amount, int accountId) {
        String sql = "UPDATE " + TABLE_NAME + " SET amount = ? WHERE " + ID_NAME + " = ?";
        logger.debug(sql + ", " + amount + ", " + accountId);
        jdbcTemplate.update(sql, amount, accountId);
    }
}
