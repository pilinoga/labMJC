package com.epam.esm.module3.controller;

import com.epam.esm.module3.controller.dto.OrderDto;
import com.epam.esm.module3.controller.dto.converter.DtoConverter;
import com.epam.esm.module3.controller.hateoas.Hateoas;
import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class OrderController represent api which allows to perform operations on orders.
 */

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    private final Hateoas<OrderDto> hateoas;
    private final DtoConverter<Order,OrderDto> converter;

    @Autowired
    public OrderController(OrderService service,
                           Hateoas<OrderDto> hateoas,
                           DtoConverter<Order, OrderDto> converter) {
        this.service = service;
        this.hateoas = hateoas;
        this.converter = converter;
    }

    /**
     * Method to get all orders from data source.
     *
     * @param page page for getting
     * @param size size of page
     * @return list of orderDto
     */

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAllOrders(@RequestParam(value = "page", defaultValue = "0", required = false) @Min(0) int page,
                                       @RequestParam(value = "size", defaultValue = "10", required = false) @Min(1) int size){
        List<Order> orders = service.getAll(page, size);
        return orders.stream()
                .map(converter::convert)
                .peek(hateoas::addLinks)
                .collect(Collectors.toList());
    }

    /**
     * Method to get order by ID.
     *
     * @param id ID of order
     * @return orderDto
     */

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getOrderByID(@PathVariable Long id) {
        Order order = service.getByID(id);
        OrderDto dto = converter.convert(order);
        hateoas.addLinks(dto);
        return dto;
    }
}
