package com.epam.esm.controllers;

import com.epam.esm.exception.certificate.NoSuchCertificateException;
import com.epam.esm.model.Certificate;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {
    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public List<Certificate> getAllCertificates(){
        return certificateService.getAll();
    }

    @GetMapping("/{id}")
    public Certificate getCertificateById(@PathVariable int id){
        return certificateService.getByID(id);
    }

    @GetMapping("/search")
    public List<Certificate> getSearch(@RequestParam(required = false) String tag,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) String sort) {
        List<Certificate> certificates = certificateService.search(tag, name, sort);
        if(certificates.isEmpty()){
            throw new NoSuchCertificateException();
        }
        return  certificates;
    }

    @PostMapping()
    public ResponseEntity<Certificate> saveCertificate(@RequestBody Certificate certificate) {
        certificateService.save(certificate);
        return new ResponseEntity<>(certificate,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCertificate(@PathVariable int id){
        certificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Certificate> updateCertificate(@RequestBody Certificate certificate,
                                                         @PathVariable int id) {
        certificateService.update(certificate,id);
        return new ResponseEntity<>(certificate,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> patchUpdateCertificate(@RequestBody Map<String, Object> updates,
                                                    @PathVariable int id){
        certificateService.patchUpdate(id,updates);
        return new ResponseEntity<>(updates,HttpStatus.OK);
    }


}
