package com.epam.esm.dao;

import com.epam.esm.model.Certificate;
import java.util.List;
import java.util.Map;

/**
 * Interface CertificateDAO is designed for basic work with database tables.
 */

public interface CertificateDAO extends DAO<Certificate>{

    /**
     * Method for updating an object.
     *
     * @param certificate contains fields for updating
     * @param id used to insert an object
     */
    void update(Certificate certificate, int id);

    /**
     * Method for patch updating an object.
     *
     * @param id used to insert an object
     * @param fields contains fields for updating
     */
    void patchUpdate(int id, Map<String, Object> fields);

    /**
     * Method for finding an object.
     *
     * @param param used to by query
     * @return list of objects
     */
    List<Certificate> findByNameOrDescription(String param);

}
