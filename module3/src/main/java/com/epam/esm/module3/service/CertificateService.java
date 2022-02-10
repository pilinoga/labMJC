package com.epam.esm.module3.service;

import com.epam.esm.module3.model.entity.Certificate;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface CertificateService extends AbstractService<Certificate> {
    List<Certificate> getByFilter(MultiValueMap<String, String> params);
}
