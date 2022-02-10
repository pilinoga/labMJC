package com.epam.esm.module3.controller;

import com.epam.esm.module3.controller.dto.TagDto;
import com.epam.esm.module3.controller.dto.converter.DtoConverter;
import com.epam.esm.module3.controller.exception.TagValidationException;
import com.epam.esm.module3.controller.hateoas.Hateoas;
import com.epam.esm.module3.model.entity.Tag;
import com.epam.esm.module3.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService service;
    private final Hateoas<TagDto> hateoas;
    private final DtoConverter<Tag,TagDto> converter;

    @Autowired
    public TagController(TagService service,
                         Hateoas<TagDto> hateoas,
                         DtoConverter<Tag,TagDto> converter) {
        this.service = service;
        this.hateoas=hateoas;
        this.converter=converter;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getAllTags(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                @RequestParam(value = "size", defaultValue = "10", required = false) int size){
        List<Tag> tags = service.getAll(page, size);
        return tags.stream()
                .map(converter::convert)
                .peek(hateoas::addLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getTagByID(@PathVariable Long id) {
        Tag tag = service.getByID(id);
        TagDto dto = converter.convert(tag);
        hateoas.addLinks(dto);
        return dto;
    }

    @GetMapping("/top")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getTopTag() {
        Tag tag = service.getMostUsedTagFromHighestCostUser();
        TagDto dto = converter.convert(tag);
        hateoas.addLinks(dto);
        return dto;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto saveTag(@Valid @RequestBody TagDto dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new TagValidationException();
        }
        Tag tag = converter.convert(dto);
        service.save(tag);
        TagDto tagDto = converter.convert(tag);
        hateoas.addLinks(tagDto);
        return tagDto;

    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id){
        service.delete(id);
    }
}
