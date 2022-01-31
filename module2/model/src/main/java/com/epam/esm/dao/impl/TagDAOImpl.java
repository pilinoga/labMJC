package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.tag.NoSuchTagException;
import com.epam.esm.exception.tag.TagAlreadyExistException;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class TagDAOImpl implements TagDAO {
    private final JdbcTemplate jdbcTemplate;
    private final String FIND_BY_ID_SQL = "SELECT * FROM tag WHERE id=?";
    private final String GET_ALL_SQL= "SELECT * FROM tag";
    private final String SAVE_SQL = "INSERT INTO tag (name) VALUES (?) ";
    private final String DELETE_SQL = "DELETE FROM tag WHERE id=?";
    private static final String FIND_BY_NAME_SQL = "SELECT * FROM tag WHERE name =?";
    private final String ID_COLUMN= "id";

    @Autowired
    public TagDAOImpl(JdbcTemplate jdbcTemplate ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static String getFindByNameSql() {
        return FIND_BY_NAME_SQL;
    }

    @Override
    public List<Tag> getAll(){
        return jdbcTemplate.query(GET_ALL_SQL,
                new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag getByID(int id) {
        try{
            return jdbcTemplate.queryForObject(FIND_BY_ID_SQL,
                    new BeanPropertyRowMapper<>(Tag.class),id);
        }
        catch (DataAccessException e){
            throw new NoSuchTagException();
        }
    }

    @Override
    public Long save(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SAVE_SQL, new String[]{ID_COLUMN});
                ps.setString(1, tag.getName());
                return ps;
            }, keyHolder);
            return keyHolder.getKey().longValue();
        }catch (DuplicateKeyException exception){
            throw new TagAlreadyExistException();
        }
    }

    @Override
    public void delete(int id) {
            this.getByID(id);
            jdbcTemplate.update(DELETE_SQL,id);
    }


}
