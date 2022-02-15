package com.epam.esm.module3.controller.hateoas;

import org.springframework.hateoas.RepresentationModel;

/**
 * The interface Hateoas contain method for adding hateoas to objects
 *
 * @param <T> type of parameter
 */

public interface Hateoas <T extends RepresentationModel<T>> {

    /**
     * method to add links to object
     *
     * @param t object for adding links
     */
    void addLinks(T t);
}
