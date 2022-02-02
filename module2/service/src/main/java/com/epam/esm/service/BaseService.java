package com.epam.esm.service;

import java.util.List;

public interface BaseService<T> {

    /**
     *  Method for getting a list of objects.
     *
     * @return list of objects
     */
    List<T> getAll();

    /**
     * Method for getting a object by id.
     *
     * @param id  used to get a object
     * @return object
     */
    T getByID(int id);

    /**
     * Method for deleting a object.
     *
     * @param id used to delete
     */
    void delete(int id);

    /**
     * Method for saving a object.
     *
     * @param t object for saving
     */
    T save(T t);
}
