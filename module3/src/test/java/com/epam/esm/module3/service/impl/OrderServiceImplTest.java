package com.epam.esm.module3.service.impl;

import com.epam.esm.module3.model.dao.impl.OrderDAOImpl;
import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.service.exception.NoSuchOrderException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    private static final List<Order> orders = new ArrayList<>();

    @Mock
    private OrderDAOImpl dao = Mockito.mock(OrderDAOImpl.class);

    @InjectMocks
    private OrderServiceImpl service;

    @BeforeAll
    public static void setup() {
        orders.add(new Order());
        orders.add(new Order());
        orders.add(new Order());
        orders.add(new Order());
        orders.add(new Order());
    }

    @Test
    void shouldReturnTagById(){
        Order order = new Order();
        order.setId(1L);

        when(dao.findByID(order.getId())).thenReturn(Optional.of(order));

        Order expected = order;
        Order actual = service.getByID(order.getId());
        assertEquals(expected,actual);
    }

    @Test
    void shouldThrow_NoSuchOrderException(){
        Order order = new Order();
        order.setId(1L);

        doThrow(new NoSuchOrderException()).when(dao).findByID(order.getId());
        assertThrows(NoSuchOrderException.class,()->{
            service.getByID(order.getId());
        });
    }

    @Test
    void shouldReturnOrders(){
        Pageable pageable = PageRequest.of(0, 10);
        when(dao.findAll(pageable)).thenReturn(orders);
        int expected = orders.size();
        int actual = service.getAll(0,10).size();
        assertEquals(expected,actual);
        verify(dao,times(1)).findAll(pageable);
    }

    @Test
    void updateShouldThrow_UnsupportedOperationException(){
        assertThrows(UnsupportedOperationException.class,()->{
            service.update(new Order(),1L);
        });
    }

    @Test
    void saveShouldThrow_UnsupportedOperationException(){
        assertThrows(UnsupportedOperationException.class,()->{
            service.save(new Order());
        });
    }

    @Test
    void deleteShouldThrow_UnsupportedOperationException(){
        assertThrows(UnsupportedOperationException.class,()->{
            service.delete(1L);
        });
    }
}
