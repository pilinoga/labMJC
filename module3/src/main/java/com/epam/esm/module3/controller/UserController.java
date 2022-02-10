package com.epam.esm.module3.controller;

import com.epam.esm.module3.controller.dto.OrderDto;
import com.epam.esm.module3.controller.dto.UserDto;
import com.epam.esm.module3.controller.dto.converter.DtoConverter;
import com.epam.esm.module3.controller.hateoas.Hateoas;
import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.model.entity.User;
import com.epam.esm.module3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    private final Hateoas<UserDto> userHateoas;
    private final Hateoas<OrderDto> orderHateoas;
    private final DtoConverter<User,UserDto> userConverter;
    private final DtoConverter<Order,OrderDto> orderConverter;

    @Autowired
    public UserController(UserService service,
                          Hateoas<UserDto> userHateoas,
                          Hateoas<OrderDto> orderHateoas,
                          DtoConverter<User, UserDto> userConverter,
                          DtoConverter<Order, OrderDto> orderConverter) {
        this.service = service;
        this.userHateoas = userHateoas;
        this.orderHateoas = orderHateoas;
        this.userConverter = userConverter;
        this.orderConverter = orderConverter;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                  @RequestParam(value = "size", defaultValue = "10", required = false) int size){
        List<User> users = service.getAll(page, size);
        return users.stream()
                .map(userConverter::convert)
                .peek(userHateoas::addLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserByID(@PathVariable Long id) {
        User user = service.getByID(id);
        UserDto dto = userConverter.convert(user);
        userHateoas.addLinks(dto);
        return dto;
    }

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getUserOrders(@PathVariable Long id) {
        List<Order> orders = service.getByID(id).getOrders();
        return orders.stream()
                .map(orderConverter::convert)
                .peek(orderHateoas::addLinks)
                .collect(Collectors.toList());

    }

    @GetMapping("/{userId}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getUserOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        Order order = service.getUserOrderById(userId, orderId);
        OrderDto dto = orderConverter.convert(order);
        orderHateoas.addLinks(dto);
        return dto;
    }

    @PostMapping("/{userId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto saveOrder(@RequestBody OrderDto dto, @PathVariable Long userId) {
        Order toSave = orderConverter.convert(dto);
        Order order = service.saveOrder(toSave, userId);
        OrderDto orderDto = orderConverter.convert(order);
        orderHateoas.addLinks(orderDto);
        return orderDto;
    }
}