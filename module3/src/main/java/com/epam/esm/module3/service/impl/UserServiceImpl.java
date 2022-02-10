package com.epam.esm.module3.service.impl;

import com.epam.esm.module3.model.dao.CertificateDAO;
import com.epam.esm.module3.model.dao.OrderDAO;
import com.epam.esm.module3.model.dao.UserDAO;
import com.epam.esm.module3.model.entity.Certificate;
import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.model.entity.User;
import com.epam.esm.module3.service.UserService;
import com.epam.esm.module3.service.exception.NoSuchCertificateException;
import com.epam.esm.module3.service.exception.NoSuchOrderException;
import com.epam.esm.module3.service.exception.NoSuchUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final CertificateDAO certificateDAO;
    private final OrderDAO orderDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, CertificateDAO certificateDAO, OrderDAO orderDAO) {
        this.userDAO = userDAO;
        this.certificateDAO = certificateDAO;
        this.orderDAO = orderDAO;
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
    public Order saveOrder(Order order, Long id){
        User user = this.getByID(id);
        Long certificateID = order.getCertificate().getId();
        Optional<Certificate> certificate = certificateDAO.findByID(certificateID);
        if(certificate.isEmpty()){
            throw new NoSuchCertificateException();
        }
        order.setUser(user);
        order.setCertificate(certificate.get());
        Double price = certificate.get().getPrice();
        order.setPrice(price);
        return orderDAO.save(order);
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
