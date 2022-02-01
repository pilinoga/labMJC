package com.epam.esm.controllers;

import com.epam.esm.exception.tag.TagValidationException;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tags")

public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    public List<Tag> getAllTags(){
        return tagService.getAll();
    }

    @GetMapping("/{id}")
    public Tag getTagByID(@PathVariable int id) {
        return tagService.getByID(id);
    }

    @PostMapping()
    public ResponseEntity<Tag> saveTag(@Valid @RequestBody Tag tag, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new TagValidationException();
        }
        tagService.save(tag);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable int id){
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
