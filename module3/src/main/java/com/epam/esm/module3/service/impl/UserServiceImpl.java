package com.epam.esm.module3.service.impl;


import com.epam.esm.module3.model.dao.RoleDAO;
import com.epam.esm.module3.model.dao.UserDAO;
import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.model.entity.Role;
import com.epam.esm.module3.model.entity.User;
import com.epam.esm.module3.service.UserService;
import com.epam.esm.module3.service.exception.NoSuchOrderException;
import com.epam.esm.module3.service.exception.NoSuchUserException;
import com.epam.esm.module3.service.exception.UniqueLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final BCryptPasswordEncoder encoder;
    public static final String ROLE_USER = "ROLE_USER";

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, BCryptPasswordEncoder encoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.encoder = encoder;
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
    public User getByLogin(String login) {
        return userDAO.findByLogin(login).orElseThrow(NoSuchUserException::new);
    }

    @Override
    @Transactional
    public User save(User user) {
        if(userDAO.findByLogin(user.getLogin()).isPresent()){
            throw new UniqueLoginException();
        }
        Role role = roleDAO.findByName(ROLE_USER);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(encoder.encode(user.getPassword()));
        return userDAO.save(user);
    }

    @Override
    public User update(User user, Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
