package com.epam.esm.module3.controller.dto.converter.impl;

import com.epam.esm.module3.controller.dto.CertificateDto;
import com.epam.esm.module3.controller.dto.TagDto;
import com.epam.esm.module3.controller.dto.converter.DtoConverter;
import com.epam.esm.module3.model.entity.Certificate;
import com.epam.esm.module3.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CertificateConverter implements DtoConverter<Certificate, CertificateDto> {

    @Autowired
    private DtoConverter<Tag, TagDto> converter;

    @Override
    public Certificate convert(CertificateDto dto) {
        Certificate certificate = new Certificate();
        certificate.setId(dto.getId());
        certificate.setName(dto.getName());
        certificate.setDescription(dto.getDescription());
        certificate.setPrice(dto.getPrice());
        certificate.setDuration(dto.getDuration());
        certificate.setCreateDate(dto.getCreateDate());
        certificate.setLastUpdateDate(dto.getLastUpdateDate());
        certificate.setTags(dto.getTags().stream()
                .map(converter::convert)
                .collect(Collectors.toSet()));
        return certificate;
    }

    @Override
    public CertificateDto convert(Certificate entity) {
        CertificateDto dto = new CertificateDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        dto.setCreateDate(entity.getCreateDate());
        dto.setLastUpdateDate(entity.getLastUpdateDate());
        dto.setTags(entity.getTags().stream()
                .map(converter::convert)
                .collect(Collectors.toSet()));
        return dto;
    }
}
