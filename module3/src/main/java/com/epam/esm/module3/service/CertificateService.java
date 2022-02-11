package com.epam.esm.module3.service;

import com.epam.esm.module3.model.entity.Certificate;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 ** Interface CertificateService is designed for basic work with DAO layer
 * for certificate entity.
 */

public interface CertificateService extends AbstractService<Certificate> {

    /**
     * Method to get certificate by parameters
     *
     * @param params parameters for filtering
     * @return list of certificates
     */
    List<Certificate> getByFilter(MultiValueMap<String, String> params);
}
