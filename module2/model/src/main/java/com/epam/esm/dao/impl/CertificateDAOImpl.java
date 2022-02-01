package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.exception.certificate.NoSuchCertificateException;
import com.epam.esm.model.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Repository
public class CertificateDAOImpl implements CertificateDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_SQL = "SELECT * FROM certificate";
    private static final String SAVE_SQL= "INSERT INTO certificate (name,description,price,duration,create_date,last_update_date) VALUES (?,?,?,?,?,?)";
    private static final String DELETE_SQL = "DELETE FROM certificate WHERE id=?";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM certificate WHERE id=?";
    private static final String LIKE_SQL = "SELECT * FROM certificate WHERE name LIKE ? OR description LIKE ?";
    private static final String UPDATE_SQL = "UPDATE certificate SET name=?,description=?,price=?,duration=?,last_update_date=? WHERE id=?";
    private static final String ID_COLUMN= "id";
    private static final String NAME_COLUMN= "name";
    private static final String DESCRIPTION_COLUMN= "description";
    private static final String PRICE_COLUMN= "price";
    private static final String DURATION_COLUMN= "duration";

    @Autowired
    public CertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Certificate> findAll(){
        return jdbcTemplate.query(GET_ALL_SQL,
                new BeanPropertyRowMapper<>(Certificate.class));
    }

    @Override
    public long save(Certificate certificate) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SAVE_SQL, new String[]{ID_COLUMN});
                ps.setString(1, certificate.getName());
                ps.setString(2, certificate.getDescription());
                ps.setDouble(3, certificate.getPrice());
                ps.setInt(4, certificate.getDuration());
                ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE));
                ps.setString(6, ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE));
                return ps;
            }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Certificate findByID(long id){
        return this.searchById(id);
    }

    @Override
    public void update(Certificate certificate, int id)  {
        this.searchById(id);
            jdbcTemplate.update(UPDATE_SQL,
                    certificate.getName(),
                    certificate.getDescription(),
                    certificate.getPrice(),
                    certificate.getDuration(),
                    ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE),
                    id);
    }

    @Override
    public void patchUpdate(int id, Map<String, Object> fields){
        Certificate certificate = this.searchById(id);
        fields.putIfAbsent(NAME_COLUMN, certificate.getName());
        fields.putIfAbsent(DESCRIPTION_COLUMN, certificate.getDescription());
        fields.putIfAbsent(PRICE_COLUMN, certificate.getPrice());
        fields.putIfAbsent(DURATION_COLUMN, certificate.getDuration());
        jdbcTemplate.update(UPDATE_SQL,
                fields.get(NAME_COLUMN),
                fields.get(DESCRIPTION_COLUMN),
                fields.get(PRICE_COLUMN),
                fields.get(DURATION_COLUMN),
                ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE),
                id);
    }


    @Override
    public void delete(int id) {
          this.searchById(id);
          jdbcTemplate.update(DELETE_SQL,id);
    }

    @Override
    public List<Certificate> findByNameOrDescription(String param){
        param = param
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
        param = "%" + param + "%";
        List<Certificate> certificates = jdbcTemplate.query(LIKE_SQL,
                new BeanPropertyRowMapper<>(Certificate.class), param, param);
        if(certificates.isEmpty()){
            throw new NoSuchCertificateException();
        }
        return certificates;
    }

    private Certificate searchById(long id){
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_SQL,
                    new BeanPropertyRowMapper<>(Certificate.class), id);
        }catch (DataAccessException e){
            throw new NoSuchCertificateException();
        }
    }
}
