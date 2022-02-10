package com.epam.esm.module3.controller.hateoas;

import org.springframework.hateoas.RepresentationModel;

public interface Hateoas <T extends RepresentationModel<T>> {

    void addLinks(T t);
}
