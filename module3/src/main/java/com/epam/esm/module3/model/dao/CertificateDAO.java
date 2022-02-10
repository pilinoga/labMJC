package com.epam.esm.module3.model.dao;

import com.epam.esm.module3.model.entity.Certificate;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

public interface CertificateDAO extends DAO<Certificate> {
    List<Certificate> findByFilter(MultiValueMap<String, String> params);
    Optional<Certificate> findByName(Certificate certificate);
    Certificate update(Certificate certificate);
}
