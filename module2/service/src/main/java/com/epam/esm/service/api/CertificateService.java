package com.epam.esm.service.api;

import com.epam.esm.model.Certificate;
import java.util.List;
import java.util.Map;

public interface CertificateService {
    List<Certificate> getAll();
    Certificate getByID(int id);
    void save(Certificate certificate);
    void delete(int id);
    void update(Certificate certificate, int id);
    void patchUpdate(int id, Map<String, Object> fields);
    List<Certificate> search(String tag, String name, String sort);
}
