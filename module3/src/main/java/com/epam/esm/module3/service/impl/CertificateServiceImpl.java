package com.epam.esm.module3.service.impl;

import com.epam.esm.module3.model.dao.CertificateDAO;
import com.epam.esm.module3.model.dao.TagDAO;
import com.epam.esm.module3.model.entity.Certificate;
import com.epam.esm.module3.model.entity.Tag;
import com.epam.esm.module3.service.CertificateService;
import com.epam.esm.module3.service.exception.NoSuchCertificateException;
import com.epam.esm.module3.service.exception.RequestParameterException;
import com.epam.esm.module3.service.exception.SortTypeException;
import com.epam.esm.module3.service.exception.UniqueNameCertificateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateDAO certificateDAO;
    private final TagDAO tagDAO;
    private static final String NAME_PARAMETER = "name";
    private static final String SORT_PARAMETER = "sort";
    private static final String ASC_SORT = "asc";
    private static final String DESC_SORT = "desc";

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO, TagDAO tagDAO) {
        this.certificateDAO = certificateDAO;
        this.tagDAO = tagDAO;
    }

    @Override
    public List<Certificate> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return certificateDAO.findAll(pageable);
    }

    @Override
    @Transactional
    public Certificate save(Certificate certificate) {
        Optional<Certificate> byName = certificateDAO.findByName(certificate);
        if(byName.isPresent()){
            throw new UniqueNameCertificateException();
        }
        this.addTagsInSave(certificate);
        return certificateDAO.save(certificate);
    }

    @Override
    public void delete(Long id) {
        Optional<Certificate> byID = certificateDAO.findByID(id);
        if(byID.isEmpty()){
            throw new NoSuchCertificateException();
        }
        certificateDAO.delete(id);
    }

    @Override
    public Certificate getByID(Long id) {
        Optional<Certificate> byID = certificateDAO.findByID(id);
        if(byID.isEmpty()){
            throw new NoSuchCertificateException();
        }
        return byID.get();
    }

    @Override
    @Transactional
    public Certificate update(Certificate certificate, Long id) {
        Optional<Certificate> byID = certificateDAO.findByID(id);
        if(byID.isEmpty()){
            throw new NoSuchCertificateException();
        }
        Optional<Certificate> byName = certificateDAO.findByName(certificate);
        if(byName.isPresent()){
            throw new UniqueNameCertificateException();
        }
        Certificate withUpdates = this.setUpdates(certificate, id);
        this.addTagsInUpdate(certificate, withUpdates);
        return certificateDAO.update(withUpdates);
    }

    @Override
    public List<Certificate> getByFilter(MultiValueMap<String, String> params) {
        if(params.entrySet().isEmpty()){
            throw new RequestParameterException();
        }
        if(params.get(NAME_PARAMETER)!=null &&
                params.get(NAME_PARAMETER).size()!=1){
            throw new RequestParameterException();
        }
        if(params.get(SORT_PARAMETER)!=null &&
                params.get(SORT_PARAMETER).size()!=1){
            throw new RequestParameterException();
        }

        if(params.get(SORT_PARAMETER)!=null &&
                params.get(SORT_PARAMETER).size()==1 &&
                !params.get(SORT_PARAMETER).get(0).equalsIgnoreCase(ASC_SORT) &&
                !params.get(SORT_PARAMETER).get(0).equalsIgnoreCase(DESC_SORT)){
            throw new SortTypeException();
            }

        List<Certificate> byFilter = certificateDAO.findByFilter(params);
        if(byFilter.isEmpty()){
            throw new NoSuchCertificateException();
        }
        return byFilter;
    }

    private void addTagsInSave(Certificate certificate) {
        Set<Tag> tags = certificate.getTags();
        certificate.setTags(new HashSet<>());
        List<Tag> all = tagDAO.findTags();
        all.retainAll(tags);
        all.forEach(certificate::addTag);
        tags.removeAll(all);
        tags.forEach(tag -> {
            tagDAO.save(tag);
            certificate.addTag(tag);
        });
    }

    private void addTagsInUpdate(Certificate certificate, Certificate updated) {
        Set<Tag> tags = certificate.getTags();
        Set<Tag> updatedTags = updated.getTags();
        tags.removeAll(updatedTags);
        tags.forEach(tag -> {
            tagDAO.save(tag);
            updated.addTag(tag);
        });
    }

    private Certificate setUpdates(Certificate certificate, Long id){
        Certificate fromDB = certificateDAO.findByID(id).get();
        if(certificate.getName()!=null){
            fromDB.setName(certificate.getName());
        }
        if(certificate.getDescription()!=null){
            fromDB.setDescription(certificate.getDescription());
        }
        if(certificate.getPrice()!=null){
            fromDB.setPrice(certificate.getPrice());
        }
        if(certificate.getDuration()!=null){
            fromDB.setDuration(certificate.getDuration());
        }
        return fromDB;
    }
}
