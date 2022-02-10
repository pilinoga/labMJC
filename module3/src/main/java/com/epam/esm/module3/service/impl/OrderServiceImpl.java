package com.epam.esm.module3.service.impl;

import com.epam.esm.module3.model.dao.OrderDAO;
import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.service.OrderService;
import com.epam.esm.module3.service.exception.NoSuchOrderException;
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

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
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
