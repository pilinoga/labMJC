package com.epam.esm.module3.controller.hateoas.impl;

import com.epam.esm.module3.controller.TagController;
import com.epam.esm.module3.controller.dto.TagDto;
import com.epam.esm.module3.controller.hateoas.Hateoas;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagHateoas implements Hateoas<TagDto> {
    private final Class<TagController> CONTROLLER = TagController.class;

    @Override
    public void addLinks(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(CONTROLLER).getTagByID(tagDto.getId())).withSelfRel());
    }
}
