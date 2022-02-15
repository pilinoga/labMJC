package com.epam.esm.module3.model.dao;

import com.epam.esm.module3.model.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Interface TagDAO is designed for basic work with database
 * for tag entity.
 */

public interface TagDAO extends DAO<Tag> {

    /**
     * Method to find tag by name
     *
     * @param tag tag for finding
     * @return optional of tag
     */
    Optional<Tag> findByName(Tag tag);

    /**
     *  Method to find all tags.
     *
     * @return list of tags;
     */
    List<Tag> findTags();

}
