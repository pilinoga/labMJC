package com.epam.esm.module3.model.dao;

import com.epam.esm.module3.model.entity.Certificate;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

/**
 * Interface CertificateDAO is designed for basic work with database
 * for certificate entity.
 */

public interface CertificateDAO extends DAO<Certificate> {

    /**
     * Method to find certificate by parameters
     *
     * @param params parameters for finding
     * @return list of certificates
     */
    List<Certificate> findByFilter(MultiValueMap<String, String> params);

    /**
     * Method to find certificate by name
     *
     * @param certificate tag for finding
     * @return optional of tag
     */
    Optional<Certificate> findByName(Certificate certificate);

    /**
     * Method to update a certificate.
     *
     * @param certificate contains fields for updating
     * @return updated certificate
     */
    Certificate update(Certificate certificate);
}
