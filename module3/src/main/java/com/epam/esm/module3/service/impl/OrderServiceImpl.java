package com.epam.esm.module3.service.impl;

import com.epam.esm.module3.model.dao.CertificateDAO;
import com.epam.esm.module3.model.dao.OrderDAO;
import com.epam.esm.module3.model.dao.UserDAO;
import com.epam.esm.module3.model.entity.Certificate;
import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.model.entity.User;
import com.epam.esm.module3.service.OrderService;
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
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final CertificateDAO certificateDAO;
    private final UserDAO userDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, CertificateDAO certificateDAO, UserDAO userDAO) {
        this.orderDAO = orderDAO;
        this.certificateDAO = certificateDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<Order> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderDAO.findAll(pageable);
    }

    @Override
    public Order getByID(Long id) {
        Optional<Order> byID = orderDAO.findByID(id);
        if(byID.isEmpty()){
            throw new NoSuchOrderException();
        }
        return byID.get();

    }

    @Override
    public Order saveOrder(Order order, Long id){
        Optional<User> user = userDAO.findByID(id);
        if(user.isEmpty()){
            throw new NoSuchUserException();
        }
        Long certificateID = order.getCertificate().getId();
        Optional<Certificate> certificate = certificateDAO.findByID(certificateID);
        if(certificate.isEmpty()){
            throw new NoSuchCertificateException();
        }
        order.setUser(user.get());
        order.setCertificate(certificate.get());
        Double price = certificate.get().getPrice();
        order.setPrice(price);
        return orderDAO.save(order);
    }

    @Override
    public Order save(Order order) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order update(Order order, Long id) {
        throw new UnsupportedOperationException();
    }

}
