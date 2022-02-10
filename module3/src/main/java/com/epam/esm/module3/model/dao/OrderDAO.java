package com.epam.esm.module3.model.dao;

import com.epam.esm.module3.model.entity.Order;

public interface OrderDAO extends DAO<Order>{
    Order findUserWithHighestOrdersCost();
}
