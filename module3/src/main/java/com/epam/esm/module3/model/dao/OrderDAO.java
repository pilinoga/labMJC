package com.epam.esm.module3.model.dao;

import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.model.entity.User;

/**
 * Interface OrderDAO is designed for basic work with database
 * for order entity.
 */

public interface OrderDAO extends DAO<Order>{

    /**
     * Method to find user with highest orders cost
     *
     * @return order
     */
    User findUserWithHighestOrdersCost();
}
