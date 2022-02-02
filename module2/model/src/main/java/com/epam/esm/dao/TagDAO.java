package com.epam.esm.dao;

import com.epam.esm.model.Tag;

/**
 * Interface TagDAO is designed for basic work with database tables.
 */

public interface TagDAO extends DAO<Tag> {

    /**
     * Method for finding tag.
     *
     * @param tagFromUser used to find an object
     * @return object
     */
    Tag findTag(Tag tagFromUser);

}
