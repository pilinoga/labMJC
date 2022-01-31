package com.epam.esm.dao.api;

import com.epam.esm.model.Certificate;
import java.util.List;
import java.util.Map;

public interface CertificateDAO {
    List<Certificate> getAll();
    void save(Certificate certificate);
    Certificate getByID(int id);
    void delete(int id);
    void update(Certificate certificate, int id);
    void patchUpdate(int id, Map<String, Object> fields);
    List<Certificate>  findByNameOrDescription(String param);

}
