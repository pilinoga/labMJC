package com.epam.esm.service;

import com.epam.esm.model.Certificate;
import java.util.List;
import java.util.Map;

/**
 * Interface CertificateService is designed for basic work with Certificate objects.
 */

public interface CertificateService extends Service<Certificate> {

    /**
     * Method for updating an object.
     *
     * @param certificate contains fields for updating
     * @param id used to insert an object
     */
    Certificate  update(Certificate certificate, int id);

    /**
     * Method for patch updating a certificate.
     *
     * @param id used to update certificate
     * @param fields contains fields for updating
     */
    Certificate  patchUpdate(int id, Map<String, Object> fields);

    /**
     * Method for getting certificates by parameters.
     *
     * @param tag name of tag
     * @param name part of name of certificate
     * @param sort type of sort
     * @return list of certificates
     */
    List<Certificate> search(String tag, String name, String sort);
}
