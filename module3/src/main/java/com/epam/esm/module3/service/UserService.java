package com.epam.esm.module3.service;

import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.model.entity.User;

/**
 ** Interface UserService is designed for basic work with DAO layer
 * for user entity.
 */

public interface UserService extends AbstractService<User> {

    /**
     * Method to get order by user id and order id
     *
     * @param userId  id of user
     * @param orderId id of order
     * @return order
     */
    Order getUserOrderById(Long userId, Long orderId);

    /**
     * Method to get user by login
     *
     * @param login login for getting user
     * @return user
     */
    User getByLogin(String login);
}
