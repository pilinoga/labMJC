package com.epam.esm.module3.service;

import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.model.entity.User;

public interface UserService extends AbstractService<User>{
    Order getUserOrderById(Long userId, Long orderId);
    Order saveOrder(Order order, Long id);
}
