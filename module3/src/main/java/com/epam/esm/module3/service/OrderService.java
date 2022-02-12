package com.epam.esm.module3.service;

import com.epam.esm.module3.model.entity.Order;

/**
 ** Interface OrderService is designed for basic work with DAO layer
 * for order entity.
 */

public interface OrderService extends AbstractService<Order>{


    /**
     * Method to save an order.
     *
     * @param order object for saving
     * @param id certificate id
     * @return saved order
     */
    Order saveOrder(Order order, Long id);

}
