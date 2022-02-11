package com.epam.esm.module3.service;

import com.epam.esm.module3.model.entity.Tag;

/**
 ** Interface TagService is designed for basic work with DAO layer
 * for tag entity.
 */

public interface TagService extends AbstractService<Tag>{

    /**
     * Method to get tag of a user with the highest cost of all orders
     *
     * @return most used tag
     */
    Tag getMostUsedTagFromHighestCostUser();
}
