package com.epam.esm.service;

import com.epam.esm.model.Certificate;
import java.util.List;
import java.util.Map;

/**
 * Interface CertificateService is designed for basic work with Certificate objects.
 */

public interface CertificateService {

    /**
     *  Method for getting a list of certificates.
     *
     * @return list of certificates
     */
    List<Certificate> getAll();

    /**
     * Method for getting a certificate by id.
     *
     * @param id  used to get a certificate
     * @return certificate
     */
    Certificate getByID(int id);

    /**
     * Method for saving a certificate.
     *
     * @param certificate object for saving
     */
    void save(Certificate certificate);

    /**
     * Method for deleting a certificate.
     *
     * @param id used to delete
     */
    void delete(int id);

    /**
     * Method for updating an object.
     *
     * @param certificate contains fields for updating
     * @param id used to insert an object
     */
    void update(Certificate certificate, int id);

    /**
     * Method for patch updating a certificate.
     *
     * @param id used to update certificate
     * @param fields contains fields for updating
     */
    void patchUpdate(int id, Map<String, Object> fields);

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
