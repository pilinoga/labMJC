package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.exception.SortException;
import com.epam.esm.model.Certificate;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CertificateServiceImpl implements CertificateService {
    private CertificateDAO certificateDAO;
    private final String ASC_SORT="ASC";

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    @Override
    public void save(Certificate certificate) {
        certificateDAO.save(certificate);
    }

    @Override
    public List<Certificate> getAll() {
        return certificateDAO.getAll();
    }

    @Override
    public void delete(int id) {
        certificateDAO.delete(id);
    }

    @Override
    public void update(Certificate certificate, int id){
        certificateDAO.update(certificate, id);
    }

    @Override
    public void patchUpdate(int id, Map<String, Object> fields){
        certificateDAO.patchUpdate(id,fields);
    }

    @Override
    public Certificate getByID(int id) {
        return certificateDAO.getByID(id);
    }

    @Override
    public List<Certificate> search(String tag, String name, String sort) {
        if(sort!= null &&
                !sort.equalsIgnoreCase("asc") &&
                !sort.equalsIgnoreCase("desc")){
            throw new SortException();
        }
        if (tag != null) {
            if (name != null) {
                if (sort != null) {
                    List<Certificate> certificates = getCertificates(tag, certificateDAO.findByNameOrDescription(name));
                    if (sort.equalsIgnoreCase(ASC_SORT)) {
                        return this.sort(certificates,Comparator.comparing(Certificate::getName));
                    } else {
                        return this.sort(certificates,Comparator.comparing(Certificate::getName).reversed());
                    }
                }
                return getCertificates(tag, certificateDAO.findByNameOrDescription(name));
            }
            if (sort != null) {
                if (sort.equalsIgnoreCase(ASC_SORT)) {
                    return this.getByTagSortASC(tag);
                } else {
                    return this.getByTagSortDESC(tag);
                }
            }
            return this.getByTag(tag);
        }
        if (name != null) {
            List<Certificate> byNameOrDescription = certificateDAO.findByNameOrDescription(name);
            if (sort != null) {
                if (sort.equalsIgnoreCase(ASC_SORT)) {
                    return this.sort(byNameOrDescription,Comparator.comparing(Certificate::getName));
                } else {
                    return this.sort(byNameOrDescription,Comparator.comparing(Certificate::getName).reversed());
                }
            }
            return byNameOrDescription;
        }
        return sort.equalsIgnoreCase(ASC_SORT)?
                this.sort(certificateDAO.getAll(), Comparator.comparing(Certificate::getName)) :
                this.sort(certificateDAO.getAll(), Comparator.comparing(Certificate::getName).reversed());
        }

    private List<Certificate> getCertificates(String tag, List<Certificate> list) {
        List<Certificate> certificates = new ArrayList<>();
        list.forEach(c -> c.getTags().stream().filter(t ->
                t.getName().equals(tag)).map(t -> c).forEach(certificates::add));
        return certificates;
    }

    private List<Certificate> sort(List<Certificate> all, Comparator<Certificate> comparing) {
        return all.stream()
                .sorted(comparing).collect(Collectors.toList());
    }

    private List<Certificate> getByTagSortDESC(String tag) {
        return sort(this.getByTag(tag), Comparator.comparing(Certificate::getName).reversed());
    }

    private List<Certificate> getByTagSortASC(String tag) {
        return sort(this.getByTag(tag), Comparator.comparing(Certificate::getName));
    }


    private List<Certificate> getByTag(String tag){
        List<Certificate> certificates = getCertificates(tag, certificateDAO.getAll());
        return certificates;
    }
}
