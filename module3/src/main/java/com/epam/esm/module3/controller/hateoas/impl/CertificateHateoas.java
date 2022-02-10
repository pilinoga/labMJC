package com.epam.esm.module3.controller.hateoas.impl;

import com.epam.esm.module3.controller.CertificateController;
import com.epam.esm.module3.controller.TagController;
import com.epam.esm.module3.controller.dto.CertificateDto;
import com.epam.esm.module3.controller.hateoas.Hateoas;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateHateoas implements Hateoas<CertificateDto> {
    private final Class<CertificateController> CONTROLLER = CertificateController.class;
    private final Class<TagController> TAG_CONTROLLER = TagController.class;

    @Override
    public void addLinks(CertificateDto certificateDto) {
        certificateDto.add(linkTo(methodOn(CONTROLLER).
                getCertificateById(certificateDto.getId())).withSelfRel());
        certificateDto.getTags().forEach(t->
                t.add(linkTo(methodOn(TAG_CONTROLLER).getTagByID(t.getId())).withSelfRel()));
    }
}
