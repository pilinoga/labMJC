package com.epam.esm.module3.service.impl;


import com.epam.esm.module3.model.dao.UserDAO;
import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.model.entity.User;
import com.epam.esm.module3.service.UserService;
import com.epam.esm.module3.service.exception.NoSuchOrderException;
import com.epam.esm.module3.service.exception.NoSuchUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userDAO.findAll(pageable);
    }

    @Override
    public User getByID(Long id) {
        Optional<User> byID = userDAO.findByID(id);
        if(byID.isEmpty()){
            throw new NoSuchUserException();
        }
        return byID.get();
    }

    @Override
    public Order getUserOrderById(Long userId, Long orderId){
        User user = this.getByID(userId);
        List<Order> orders = user.getOrders();
        Optional<Order> order = orders.stream()
                .filter(o -> o.getId().equals(orderId)).findFirst();
        if(order.isEmpty()){
            throw new NoSuchOrderException();
        }
        return order.get();
    }

    @Override
    public User update(User user, Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User save(User user) {
        throw new UnsupportedOperationException();
    }
}
