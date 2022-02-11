package com.epam.esm.module3.model.dao;

import com.epam.esm.module3.model.entity.AbstractEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 ** Interface DAO is designed for basic work with database.
 *
 * @param <T> type of entity.
 */

public interface DAO <T extends AbstractEntity> {

    /**
     * Method to find an entity by id.
     *
     * @param id  used to find an entity
     * @return optional of entity
     */
    Optional<T> findByID(Long id);

    /**
     *  Method to find a list of entity.
     *
     * @param pageable page for finding
     * @return list of entity
     */
    List<T> findAll(Pageable pageable);

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

}
