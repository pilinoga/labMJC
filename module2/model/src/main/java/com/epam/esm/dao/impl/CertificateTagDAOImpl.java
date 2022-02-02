package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateTagDAO;
import com.epam.esm.model.CertificateTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CertificateTagDAOImpl implements CertificateTagDAO {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_ALL_SQL = "SELECT * FROM certificate_tag";
    private static final String SAVE_SQL = "INSERT INTO certificate_tag (certificate_id,tag_id) VALUES (?,?)";

    @Autowired
    public CertificateTagDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<CertificateTag> findAll(){
        return jdbcTemplate.query(GET_ALL_SQL, new BeanPropertyRowMapper<>(CertificateTag.class));
    }
    @Override
    public void saveTags(long certificateId, long tagId) {
        jdbcTemplate.update(SAVE_SQL, certificateId, tagId);
    }
}
