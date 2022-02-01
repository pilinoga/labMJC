package com.epam.esm.dao;

import com.epam.esm.model.CertificateTag;
import java.util.List;

/**
 * Interface CertificateTagDAO is designed for basic work with database tables.
 */

public interface CertificateTagDAO {

    /**
     *  Method for getting a list of objects.
     *
     * @return list of objects
     */
    List<CertificateTag> getAll();

    /**
     * Method for saving tag from certificate
     *
     * @param certificateId used by insert query
     * @param tagId used by insert query
     */
    void saveTags(Long certificateId, Long tagId);
}
