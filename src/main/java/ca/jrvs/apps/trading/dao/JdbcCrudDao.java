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

public abstract class JdbcCrudDao<E extends Entity, ID> implements CrudRespository<E, ID> {

    private final Logger logger = LoggerFactory.getLogger(getEntityClass());

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
    public E findById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        String idName = getIdName();
        Class clazz = getEntityClass();
        return findById(idName, id, clazz);
    }

    abstract public String getIdName();

    abstract public Class getEntityClass();

    //Helper method
    public E findById(String idName, ID id, Class clazz) {
        E entity = null;
        try {
            entity = (E) getJdbcTemplate().queryForObject("select * from " + getTableName() + " Where " + idName + " = ?", BeanPropertyRowMapper.newInstance(clazz), id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Cannot find id: " + id, e);
            throw new ResourceNotFoundException("Cannot find id:" + id);
        }
        return entity;
    }

    abstract public JdbcTemplate getJdbcTemplate();

    abstract public String getTableName();

    @Override
    public boolean existsById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        E entity = findById(id);
        return entity != null;
    }

    @Override
    public void deleteById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        getJdbcTemplate().update("delete from " + getTableName() + " where " + getIdName() + " = ?", id);
    }
}
