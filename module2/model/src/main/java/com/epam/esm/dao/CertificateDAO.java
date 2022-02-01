package com.epam.esm.dao;

import com.epam.esm.model.Certificate;
import java.util.List;
import java.util.Map;

public interface CertificateDAO extends DAO<Certificate>{
    void update(Certificate certificate, int id);
    void patchUpdate(int id, Map<String, Object> fields);
    List<Certificate> findByNameOrDescription(String param);

}
