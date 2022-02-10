package com.epam.esm.module3.controller.dto.converter.impl;

import com.epam.esm.module3.controller.dto.TagDto;
import com.epam.esm.module3.controller.dto.converter.DtoConverter;
import com.epam.esm.module3.model.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagConverter implements DtoConverter<Tag, TagDto> {
    @Override
    public Tag convert(TagDto dto) {
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        return tag;
    }

    @Override
    public TagDto convert(Tag entity) {
        TagDto dto = new TagDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
