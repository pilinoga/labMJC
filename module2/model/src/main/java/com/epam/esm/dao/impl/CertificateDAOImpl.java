package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.CertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.certificate.NoSuchCertificateException;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.CertificateTag;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.sql.PreparedStatement;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CertificateDAOImpl implements CertificateDAO {
    private final JdbcTemplate jdbcTemplate;
    private final TagDAO tagDAO;
    private final CertificateTagDAO certificateTagDAO;

    private final String GET_ALL_SQL = "SELECT * FROM certificate";
    private final String SAVE_SQL= "INSERT INTO certificate (name,description,price,duration,create_date,last_update_date) VALUES (?,?,?,?,?,?)";
    private final String DELETE_SQL = "DELETE FROM certificate WHERE id=?";
    private final String FIND_BY_ID_SQL = "SELECT * FROM certificate WHERE id=?";
    private final String LIKE_SQL = "SELECT * FROM certificate WHERE name LIKE ? OR description LIKE ?";
    private final String UPDATE_SQL = "UPDATE certificate SET name=?,description=?,price=?,duration=?,last_update_date=? WHERE id=?";
    private final String ID_COLUMN= "id";
    private final String NAME_COLUMN= "name";
    private final String DESCRIPTION_COLUMN= "description";
    private final String PRICE_COLUMN= "price";
    private final String DURATION_COLUMN= "duration";

    @Autowired
    public CertificateDAOImpl(JdbcTemplate jdbcTemplate,
                              TagDAO tagDAO,
                              CertificateTagDAO certificateTagDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDAO = tagDAO;
        this.certificateTagDAO = certificateTagDAO;
    }

    @Override
    public List<Certificate> getAll(){
        List<CertificateTag> list = certificateTagDAO.getAll();
        List<Certificate> certificates = jdbcTemplate.query(GET_ALL_SQL,
                new BeanPropertyRowMapper<>(Certificate.class));
        return getCertificatesWithTags(list, certificates);
    }

    @Override
    public void save(Certificate certificate) {
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
        Long certificateId = keyHolder.getKey().longValue();
        this.saveTagsFromCertificate(certificate, certificateId);
    }

    @Override
    public void update(Certificate certificate, int id) {
        this.findById(id);
        jdbcTemplate.update(UPDATE_SQL,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE),
                id);
        Set<Tag> tags = certificate.getTags();
        List<Tag> all = tagDAO.getAll();
        this.saveNewTagsFromCertificateInUpdate(id, tags, all);
        this.addTagsToCertificateInUpdate(id, tags, all);
    }

    @Override
    public void patchUpdate(int id, Map<String, Object> fields){
        Certificate certificate = this.findById(id);
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
    public Certificate getByID(int id){
            Certificate certificate = this.findById(id);
            List<CertificateTag> list = certificateTagDAO.getAll();
            list.stream().filter(tag ->
                    certificate.getId().equals(tag.getCertificateId())).forEach(tag -> {
                Long idTag = tag.getTagId();
                Tag tagFromDB = tagDAO.getByID(Math.toIntExact(idTag));
                certificate.addTag(tagFromDB);
            });
            return certificate;
    }

    @Override
    public void delete(int id) {
          this.findById(id);
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
        List<CertificateTag> list = certificateTagDAO.getAll();
        return getCertificatesWithTags(list, certificates);
    }

    private List<Certificate> getCertificatesWithTags(List<CertificateTag> list, List<Certificate> certificates) {
        certificates.forEach(certificate ->
                list.stream().filter(tag ->
                        certificate.getId().equals(tag.getCertificateId())).forEach(tag -> {
                    Long idTag = tag.getTagId();
                    Tag tagFromDB = tagDAO.getByID(Math.toIntExact(idTag));
                    certificate.addTag(tagFromDB);
                }));
        return certificates;
    }


    private void saveTagsFromCertificate(Certificate certificate, Long certificateId) {
        Set<Tag> tags = certificate.getTags();
        List<Tag> all = tagDAO.getAll();
        tags.forEach(tagFromUser -> {
            if (all.stream().anyMatch(tag -> tag.getName().equals(tagFromUser.getName()))) {
                Tag tagFromDB = jdbcTemplate.queryForObject(TagDAOImpl.getFindByNameSql(),
                        new BeanPropertyRowMapper<>(Tag.class), tagFromUser.getName());
                jdbcTemplate.update(CertificateTagDAOImpl.getSaveSql(),
                        certificateId, tagFromDB.getId());
            }else{
                Long tagId = tagDAO.save(tagFromUser);
                jdbcTemplate.update(CertificateTagDAOImpl.getSaveSql(),
                        certificateId, tagId);
            }
        });
    }

    private void addTagsToCertificateInUpdate(int id, Set<Tag> certificateTags, List<Tag> dbTags) {
        certificateTags.forEach(tagFromUser -> {
            if (dbTags.stream().anyMatch(tag -> tag.getName().equals(tagFromUser.getName()))) {
                Tag tagFromDB = jdbcTemplate.queryForObject(TagDAOImpl.getFindByNameSql(),
                        new BeanPropertyRowMapper<>(Tag.class), tagFromUser.getName());
                jdbcTemplate.update(CertificateTagDAOImpl.getSaveSql(),
                        id, tagFromDB.getId());
            }
        });
    }

    private void saveNewTagsFromCertificateInUpdate(int id, Set<Tag> certificateTags, List<Tag> dbTags) {
        certificateTags.forEach(tagFromUser -> {
            if (dbTags.stream().noneMatch(tag -> tag.getName().equals(tagFromUser.getName()))) {
                Long tagId = tagDAO.save(tagFromUser);
                jdbcTemplate.update(CertificateTagDAOImpl.getSaveSql(),
                        id, tagId);
            }
        });
    }

    private Certificate findById(int id){
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_SQL,
                    new BeanPropertyRowMapper<>(Certificate.class), id);
        }catch (DataAccessException e){
            throw new NoSuchCertificateException();
        }
    }
}
