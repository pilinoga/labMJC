package com.epam.esm.module3.controller.dto.converter.impl;

import com.epam.esm.module3.controller.dto.OrderDto;
import com.epam.esm.module3.controller.dto.converter.DtoConverter;
import com.epam.esm.module3.model.entity.Certificate;
import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter implements DtoConverter<Order, OrderDto> {

    @Override
    public Order convert(OrderDto dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setPrice(dto.getPrice());
        order.setPurchaseDate(dto.getPurchaseDate());

        User user = new User();
        user.setId(dto.getUserId());
        order.setUser(user);

        Certificate certificate = new Certificate();
        certificate.setId(dto.getCertificateId());
        order.setCertificate(certificate);
       return order;
    }

    @Override
    public OrderDto convert(Order entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setPurchaseDate(entity.getPurchaseDate());
        dto.setCertificateId(entity.getCertificate().getId());
        return dto;
    }
}
