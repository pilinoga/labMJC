package com.epam.esm.service;

import com.epam.esm.model.Tag;
import java.util.List;

/**
 * Interface TagService is designed for basic work with Certificate objects.
 */

public interface TagService {

    /**
     *  Method for getting a list of tags.
     *
     * @return list of tags
     */
    List<Tag> getAll();

    /**
     * Method for getting a tag by id.
     *
     * @param id  used to get a tag
     * @return tag
     */
    Tag getByID(int id);

    /**
     * Method for saving a tag.
     *
     * @param tag tag for saving
     * @return saving tag with id
     */
    Tag save(Tag tag);

    /**
     * Method for deleting a tag.
     *
     * @param id used to delete
     */
    void delete(int id);
}
