package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.CertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.SortException;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.CertificateTag;
import com.epam.esm.model.Tag;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateDAO certificateDAO;
    private final CertificateTagDAO certificateTagDAO ;
    private final TagDAO tagDAO;
    private static final String ASC_SORT="ASC";

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO,
                                  CertificateTagDAO certificateTagDAO,
                                  TagDAO tagDAO) {
        this.certificateDAO = certificateDAO;
        this.certificateTagDAO = certificateTagDAO;
        this.tagDAO = tagDAO;
    }

    @Override
    public List<Certificate> getAll() {
        List<CertificateTag> certificateTags = certificateTagDAO.findAll();
        List<Certificate> certificates = certificateDAO.findAll();
        return this.getCertificatesWithTags(certificateTags,certificates);
    }

    @Override
    @Transactional
    public Certificate save(Certificate certificate){
        long certificateId = certificateDAO.save(certificate);
        Set<Tag> tags = certificate.getTags();
        List<Tag> all = tagDAO.findAll();
        tags.forEach(tagFromUser -> {
            if (all.stream().anyMatch(tag -> tag.getName().equals(tagFromUser.getName()))) {
                Tag tagFromDB = tagDAO.findTag(tagFromUser);
                certificateTagDAO.saveTags(certificateId, tagFromDB.getId());
            } else {
                long tagId = tagDAO.save(tagFromUser);
                certificateTagDAO.saveTags(certificateId, tagId);
            }
        });
        return this.getCertificate(certificateId);

    }

    @Override
    @Transactional
    public Certificate update(Certificate certificate, int id) {
        certificateDAO.update(certificate, id);
        Set<Tag> tags = certificate.getTags();
        List<Tag> all = tagDAO.findAll();
        tags.forEach(tagFromUser -> {
            if (all.stream().noneMatch(tag -> tag.getName().equals(tagFromUser.getName()))) {
                long tagId = tagDAO.save(tagFromUser);
                certificateTagDAO.saveTags(id,tagId);
            }
        });
        tags.forEach(tagFromUser -> {
            if (all.stream().anyMatch(tag -> tag.getName().equals(tagFromUser.getName()))) {
                long tagId = tagDAO.findTag(tagFromUser).getId();
                certificateTagDAO.saveTags( id, tagId);
            }
        });
        return this.getCertificate(id);
    }

    @Override
    public Certificate getByID(int id) {
        return this.getCertificate(id);
    }

    @Override
    public void delete(int id) {
        certificateDAO.delete(id);
    }

    @Override
    public Certificate  patchUpdate(int id, Map<String, Object> fields){
        certificateDAO.patchUpdate(id,fields);
        return this.getCertificate(id);
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
                    List<Certificate> certificates = getCertificates(tag, this.getByNameOrDescription(name));
                    if (sort.equalsIgnoreCase(ASC_SORT)) {
                        return this.sort(certificates,Comparator.comparing(Certificate::getName));
                    } else {
                        return this.sort(certificates,Comparator.comparing(Certificate::getName).reversed());
                    }
                }
                return getCertificates(tag, this.getByNameOrDescription(name));
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
            List<Certificate> certificates = this.getByNameOrDescription(name);
            if (sort != null) {
                if (sort.equalsIgnoreCase(ASC_SORT)) {
                    return this.sort(certificates,Comparator.comparing(Certificate::getName));
                } else {
                    return this.sort(certificates,Comparator.comparing(Certificate::getName).reversed());
                }
            }
            return certificates;
        }
        return sort.equalsIgnoreCase(ASC_SORT)?
                this.sort(this.getAll(), Comparator.comparing(Certificate::getName)) :
                this.sort(this.getAll(), Comparator.comparing(Certificate::getName).reversed());
        }

    private List<Certificate> getByNameOrDescription(String param){
        List<Certificate> certificates = certificateDAO.findByNameOrDescription(param);
        List<CertificateTag> list = certificateTagDAO.findAll();
        certificates.forEach(certificate ->
                list.stream().filter(tag ->
                        certificate.getId().equals(tag.getCertificateId())).forEach(tag -> {
                    Long idTag = tag.getTagId();
                    Tag tagFromDB = tagDAO.findByID(Math.toIntExact(idTag));
                    certificate.addTag(tagFromDB);
                }));
        return certificates;
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
        return this.getCertificates(tag, this.getAll());
    }

    private List<Certificate> getCertificatesWithTags(List<CertificateTag> list, List<Certificate> certificates) {
        certificates.forEach(certificate ->
                list.stream().filter(tag ->
                        certificate.getId().equals(tag.getCertificateId())).forEach(tag -> {
                    Long idTag = tag.getTagId();
                    Tag tagFromDB = tagDAO.findByID(Math.toIntExact(idTag));
                    certificate.addTag(tagFromDB);
                }));
        return certificates;
    }

    private Certificate getCertificate(long certificateId) {
        Certificate saved = certificateDAO.findByID(certificateId);
        List<CertificateTag> list = certificateTagDAO.findAll();
        list.stream().filter(tag ->
                saved.getId().equals(tag.getCertificateId())).forEach(tag -> {
            Long idTag = tag.getTagId();
            Tag tagFromDB = tagDAO.findByID(Math.toIntExact(idTag));
            saved.addTag(tagFromDB);
        });
        return saved;
    }

}
