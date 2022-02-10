package com.epam.esm.module3.controller;

import com.epam.esm.module3.controller.dto.CertificateDto;
import com.epam.esm.module3.controller.dto.converter.DtoConverter;
import com.epam.esm.module3.controller.exception.CertificateValidationException;
import com.epam.esm.module3.controller.hateoas.Hateoas;
import com.epam.esm.module3.model.entity.Certificate;
import com.epam.esm.module3.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {
    private CertificateService service;
    private final Hateoas<CertificateDto> hateoas;
    private final DtoConverter<Certificate,CertificateDto> converter;

    @Autowired
    public CertificateController(CertificateService service,
                                 Hateoas<CertificateDto> hateoas,
                                 DtoConverter<Certificate, CertificateDto> converter) {
        this.service = service;
        this.hateoas = hateoas;
        this.converter = converter;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CertificateDto> getFilter(@RequestParam MultiValueMap<String, String> params) {
        List<Certificate> certificates = service.getByFilter(params);
        return certificates.stream()
                .map(converter::convert)
                .peek(hateoas::addLinks)
                .collect(Collectors.toList());
        //todo pagination
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto getCertificateById( @PathVariable Long id){
        Certificate certificate = service.getByID(id);
        CertificateDto dto = converter.convert(certificate);
        hateoas.addLinks(dto);
        return dto;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDto saveCertificate(@Valid @RequestBody CertificateDto dto,
                                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new CertificateValidationException();
        }
        Certificate certificate = converter.convert(dto);
        Certificate saved = service.save(certificate);
        CertificateDto certificateDto = converter.convert(saved);
        hateoas.addLinks(certificateDto);
        return certificateDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable Long id){
        service.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto updateCertificate(@Valid @RequestBody CertificateDto dto,
                                            @PathVariable Long id,
                                            BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new CertificateValidationException();
        }
        Certificate certificate = converter.convert(dto);
        Certificate updated = service.update(certificate, id);
        CertificateDto certificateDto = converter.convert(updated);
        hateoas.addLinks(certificateDto);
        return certificateDto;
    }

}
