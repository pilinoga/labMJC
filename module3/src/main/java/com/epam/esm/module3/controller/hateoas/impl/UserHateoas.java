package com.epam.esm.module3.controller.hateoas.impl;

import com.epam.esm.module3.controller.UserController;
import com.epam.esm.module3.controller.dto.UserDto;
import com.epam.esm.module3.controller.hateoas.Hateoas;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserHateoas implements Hateoas<UserDto> {
    private final Class<UserController> CONTROLLER = UserController.class;

    @Override
    public void addLinks(UserDto userDto) {
        userDto.add(linkTo(methodOn(CONTROLLER).getUserByID(userDto.getId())).withSelfRel());
    }
}
