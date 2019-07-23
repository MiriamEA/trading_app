package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;

public abstract class JdbcCrudDao<E extends Entity, ID> implements CrudRespository<E, ID> {

    public final Logger logger = LoggerFactory.getLogger(getEntityClass());

    @Override
    public E save(E entity) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        SimpleJdbcInsert simpleJdbcInsert = getSimpleJdbcInsert();
        if (simpleJdbcInsert.getGeneratedKeyNames().length == 1) {
            ID newId = (ID) simpleJdbcInsert.executeAndReturnKey(parameterSource);
            logger.info("New id: " + newId);
            entity.setId(newId);
        } else {
            simpleJdbcInsert.execute(parameterSource);
        }
        return entity;
    }

    abstract public SimpleJdbcInsert getSimpleJdbcInsert();

    @Override
    public boolean existsById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        E entity = findById(id);
        return entity != null;
    }

    @Override
    public E findById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        String idName = getIdName();
        Class clazz = getEntityClass();
        return findById(idName, id, clazz, true);
    }

    abstract public String getIdName();

    abstract public Class getEntityClass();

    //Helper method
    public E findById(String idName, ID id, Class clazz, boolean forUpdate) {
        E entity = null;
        try {
            String sql = "select * from " + getTableName() + " Where " + idName + " = ?";
            if (forUpdate) {
                sql = sql + " for update";
            }
            entity = (E) getJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(clazz), id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Cannot find " + clazz + " id: " + id, e);
        }
        if (entity == null) {
            throw new ResourceNotFoundException("Resource not found.");
        }
        return entity;
    }

    abstract public String getTableName();

    /**
     * Gets all values from the id column
     *
     * @return List containing all ids in a table
     */
    public List<ID> getAllIds() {
        List<ID> allIds = getJdbcTemplate().queryForList("select " + getIdName() + " from " + getTableName(), getIdClass());
        return allIds;
    }

    @Override
    public void deleteById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        deleteById(getIdName(), id);
    }

    /**
     * Deletes an entity with the given id in column idName
     *
     * @param idName name of id colum
     * @param id     ust not be null
     */
    public void deleteById(String idName, ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        String sql = "delete from " + getTableName() + " where " + idName + " =?";
        getJdbcTemplate().update(sql, id);
    }

    abstract public JdbcTemplate getJdbcTemplate();

    public abstract Class getIdClass();

    /**
     * Gets all rows in a table
     *
     * @return list containing all objects in table
     */
    public List<E> getEverything() {
        String sql = "select * from " + getTableName();
        List<E> list = getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(getEntityClass()));
        return list;
    }

    /**
     * Updates a column with a number value by id
     * @param value new value to set
     * @param columnName column to update
     * @param id id of row to update
     * @throws java.sql.SQLException if sql execution fails
     */
    public void updateNumberColumnById(Number value, String columnName, ID id) {
        String sql = "UPDATE " + getTableName() + " SET " + columnName + " =? where " + getIdName() + " = ?";
        logger.info(sql + ", " + columnName + ", " + id);
        getJdbcTemplate().update(sql, value, id);
    }
}
