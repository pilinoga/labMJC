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

/**
 * Class TagController represent api which allows to perform operations on tags.
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Method for getting all tags from data source.
     *
     * @return list of found tags
     */
    @GetMapping()
    public List<Tag> getAllTags(){
        return tagService.getAll();
    }

    /**
     * Method for getting tag by ID.
     *
     * @param id ID of tag
     * @return found tag
     */
    @GetMapping("/{id}")
    public Tag getTagByID(@PathVariable int id) {
        return tagService.getByID(id);
    }

    /**
     * Method for saving new tag.
     *
     * @param tag tag for saving
     * @param bindingResult holds the result of a validation and binding and contains errors
     * @return HttpStatus CREATED
     */
    @PostMapping()
    public ResponseEntity<Tag> saveTag(@Valid @RequestBody Tag tag, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new TagValidationException();
        }
        tagService.save(tag);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);

    }

    /**
     * Method for deleting tag by ID.
     *
     * @param id ID of tag
     * @return HttpStatus NO_CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable int id){
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
