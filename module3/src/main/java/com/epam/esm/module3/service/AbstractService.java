package com.epam.esm.module3.service;

import com.epam.esm.module3.model.entity.AbstractEntity;
import java.util.List;

/**
 ** Interface AbstractService is designed for basic work with DAO layer.
 *
 * @param <T> type of entity.
 */

public interface AbstractService<T extends AbstractEntity> {

    /**
     *  Method to get a list of entity.
     *
     * @param page number of page
     * @param size number of entity
     * @return list of entity
     */
    List<T> getAll(int page, int size);

    /**
     * Method to save an entity.
     *
     * @param t object for saving
     * @return t saved entity
     */
    T save(T t);

    /**
     * Method to delete an entity.
     *
     * @param id used to delete entity
     */
    void delete(Long id);

    /**
     * Method to get an entity by id.
     *
     * @param id  used to get an entity
     * @return entity
     */
    T getByID(Long id);

    /**
     * Method to update an entity.
     *
     * @param t entity for updating
     * @param id entity id
     * @return updated entity
     */
    T update(T t, Long id);
}
