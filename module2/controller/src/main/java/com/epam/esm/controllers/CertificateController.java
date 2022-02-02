package com.epam.esm.controllers;

import com.epam.esm.exception.certificate.CertificateValidationException;
import com.epam.esm.exception.certificate.NoSuchCertificateException;
import com.epam.esm.model.Certificate;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Class RestController represent api which allows to perform operations on certificates.
 */
@RestController
@RequestMapping("/api/certificates")
public class CertificateController {
    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Method for getting certificate by ID.
     *
     * @param id ID of certificate
     * @return found certificate
     */
    @GetMapping("/{id}")
    public Certificate getCertificateById( @PathVariable int id){
        return certificateService.getByID(id);
    }

    /**
     *  Method for getting certificates by parameters.
     *
     * @param tag name of tag
     * @param name name or part of name of certificate
     * @param sort type of sort
     * @return list of found certificates
     */
    @GetMapping()
    public List<Certificate> getSearch(@RequestParam(required = false) String tag,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) String sort) {
        List<Certificate> certificates = certificateService.search(tag, name, sort);
        if(certificates.isEmpty()){
            throw new NoSuchCertificateException();
        }
        return  certificates;
    }

    /**
     * Method for saving new certificate.
     *
     * @param certificate certificate for saving
     * @param bindingResult holds the result of a validation and binding and contains errors
     * @return HttpStatus CREATED
     */
    @PostMapping()
    public ResponseEntity<Certificate> saveCertificate(@Valid @RequestBody Certificate certificate,
                                                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new CertificateValidationException();
        }
        Certificate saved = certificateService.save(certificate);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }

    /**
     * Method for deleting certificate by ID.
     *
     * @param id ID of tag
     * @return HttpStatus NO_CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCertificate(@PathVariable int id){
        certificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Method for updating certificate by ID.
     *
     * @param id ID of tag
     * @param certificate certificate with fields for updating
     * @param bindingResult holds the result of a validation and binding and contains errors
     * @return HttpStatus OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<Certificate> updateCertificate(@Valid @RequestBody Certificate certificate,
                                                         @PathVariable int id,
                                                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new CertificateValidationException();
        }
        Certificate updated = certificateService.update(certificate, id);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    /**
     * Method for updating certificate by ID.
     *
     * @param id ID of tag
     * @param updates contains fields for updating
     * @return HttpStatus OK
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Certificate> patchUpdateCertificate(@RequestBody Map<String, Object> updates,
                                                    @PathVariable int id){
        Certificate updated = certificateService.patchUpdate(id, updates);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

}
