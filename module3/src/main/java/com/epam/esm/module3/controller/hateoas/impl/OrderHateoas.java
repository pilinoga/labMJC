package com.epam.esm.module3.controller.hateoas.impl;

import com.epam.esm.module3.controller.OrderController;
import com.epam.esm.module3.controller.dto.OrderDto;
import com.epam.esm.module3.controller.hateoas.Hateoas;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderHateoas implements Hateoas<OrderDto> {
    private static final Class<OrderController> CONTROLLER = OrderController.class;

    @Override
    public void addLinks(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(CONTROLLER).getOrderByID(orderDto.getId())).withSelfRel());
    }
}
