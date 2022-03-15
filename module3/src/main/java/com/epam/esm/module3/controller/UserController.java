package com.epam.esm.module3.controller;

import com.epam.esm.module3.controller.dto.OrderDto;
import com.epam.esm.module3.controller.dto.UserDto;
import com.epam.esm.module3.controller.dto.converter.DtoConverter;
import com.epam.esm.module3.controller.exception.OrderValidationException;
import com.epam.esm.module3.controller.exception.UserValidationException;
import com.epam.esm.module3.controller.hateoas.Hateoas;
import com.epam.esm.module3.controller.security.jwt.JwtProvider;
import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.model.entity.User;
import com.epam.esm.module3.service.OrderService;
import com.epam.esm.module3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class UserController represent api which allows to perform operations on user.
 */

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    private final OrderService orderService;
    private final Hateoas<UserDto> userHateoas;
    private final Hateoas<OrderDto> orderHateoas;
    private final DtoConverter<User,UserDto> userConverter;
    private final DtoConverter<Order,OrderDto> orderConverter;
    private final JwtProvider provider;

    @Autowired
    public UserController(UserService service,
                          Hateoas<UserDto> userHateoas,
                          Hateoas<OrderDto> orderHateoas,
                          DtoConverter<User, UserDto> userConverter,
                          DtoConverter<Order, OrderDto> orderConverter,
                          OrderService orderService, JwtProvider provider) {
        this.service = service;
        this.userHateoas = userHateoas;
        this.orderHateoas = orderHateoas;
        this.userConverter = userConverter;
        this.orderConverter = orderConverter;
        this.orderService = orderService;
        this.provider = provider;
    }

    /**
     * Method to get all users from data source.
     *
     * @param page page for getting
     * @param size size of page
     * @return list of userDto
     */

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers(@RequestParam(value = "page", defaultValue = "0", required = false) @Min(0) int page,
                                  @RequestParam(value = "size", defaultValue = "10", required = false) @Min(1) int size){
        List<User> users = service.getAll(page, size);
        return users.stream()
                .map(userConverter::convert)
                .peek(userHateoas::addLinks)
                .collect(Collectors.toList());
    }

    /**
     * Method to get user by ID.
     *
     * @param id ID of user
     * @return userDto
     */

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserByID(@PathVariable Long id) {
        User user = service.getByID(id);
        UserDto dto = userConverter.convert(user);
        userHateoas.addLinks(dto);
        return dto;
    }

    /**
     * Method to get list of orders by userID.
     *
     * @param id ID of user
     * @return list of orderDto
     */

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getUserOrders(@PathVariable Long id) {
        List<Order> orders = service.getByID(id).getOrders();
        return orders.stream()
                .map(orderConverter::convert)
                .peek(orderHateoas::addLinks)
                .collect(Collectors.toList());

    }

    /**
     * Method to get order by userID and orderID.
     *
     * @param userId ID of user
     * @param orderId ID of order
     * @return orderDto
     */

    @GetMapping("/{userId}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getUserOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        Order order = service.getUserOrderById(userId, orderId);
        OrderDto dto = orderConverter.convert(order);
        orderHateoas.addLinks(dto);
        return dto;
    }

    /**
     * Method to create an order
     *
     * @param dto order to create
     * @param userId id of user
     * @return created order
     */

    @PostMapping("/{userId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto saveOrder(@Valid @RequestBody OrderDto dto,
                              BindingResult bindingResult,
                              @PathVariable Long userId,
                              @RequestHeader("Authorization") String auth) {
        if(bindingResult.hasErrors()){
            throw new OrderValidationException();
        }
        if(!userId.equals(provider.getID(auth))){
            throw new UserValidationException();
        }
        Order toSave = orderConverter.convert(dto);
        Order order = orderService.saveOrder(toSave, userId);
        OrderDto orderDto = orderConverter.convert(order);
        orderHateoas.addLinks(orderDto);
        return orderDto;
    }
}
