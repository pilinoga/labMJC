package com.epam.esm.module3.service.impl;

import com.epam.esm.module3.model.dao.impl.CertificateDAOImpl;
import com.epam.esm.module3.model.dao.impl.OrderDAOImpl;
import com.epam.esm.module3.model.dao.impl.UserDAOImpl;
import com.epam.esm.module3.model.entity.Certificate;
import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.model.entity.User;
import com.epam.esm.module3.service.exception.NoSuchOrderException;
import com.epam.esm.module3.service.exception.NoSuchUserException;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static final List<User> users = new ArrayList<>();

    @Mock
    private OrderDAOImpl orderDAO = Mockito.mock(OrderDAOImpl.class);
    @Mock
    private CertificateDAOImpl certificateDAO = Mockito.mock(CertificateDAOImpl.class);
    @Mock
    private UserDAOImpl userDAO = Mockito.mock(UserDAOImpl.class);

    @InjectMocks
    private UserServiceImpl service;

    @BeforeAll
    public static void setup() {
        users.add(new User());
        users.add(new User());
        users.add(new User());
        users.add(new User());
        users.add(new User());
    }


    @Test
    void shouldSaveOrder(){
        User user = new User();
        user.setId(1L);

        Certificate certificate = new Certificate();
        certificate.setId(1L);
        certificate.setPrice(10.5);

        Order order = new Order();
        order.setId(1L);
        order.setCertificate(certificate);

        when(userDAO.findByID(user.getId())).thenReturn(Optional.of(user));
        when(certificateDAO.findByID(certificate.getId())).thenReturn(Optional.of(certificate));
        when(orderDAO.save(order)).thenReturn(order);

        service.saveOrder(order, user.getId());

        assertEquals(order.getPrice(),certificate.getPrice());
        assertEquals(order.getUser(),user);
        assertEquals(order.getCertificate(),certificate);
    }


    @Test
    void shouldReturnUserOrderByOrderId(){
        User user = new User();
        user.setId(1L);
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);
        user.addOrder(order1);
        user.addOrder(order2);

        when(userDAO.findByID(user.getId())).thenReturn(Optional.of(user));

        Order expected = order1;
        Order actual = service.getUserOrderById(1L,1L);
        assertEquals(expected.getId(),actual.getId());
    }

    @Test
    void shouldThrow_NoSuchUserException_When_getUserOrderById(){
        User user = new User();
        user.setId(1L);
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);
        user.addOrder(order1);
        user.addOrder(order2);

        when(userDAO.findByID(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class,()->{
            service.getUserOrderById(anyLong(),2L);
        });
    }

    @Test
    void shouldThrow_NoSuchOrderException_When_getUserOrderById(){
        User user = new User();
        user.setId(1L);
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);
        user.addOrder(order1);
        user.addOrder(order2);

        when(userDAO.findByID(user.getId())).thenReturn(Optional.of(user));

        Long noSuchOrderId = 3L;

        assertThrows(NoSuchOrderException.class,()->{
            service.getUserOrderById(1L,noSuchOrderId);
        });
    }


    @Test
    void shouldReturnOrders(){
        Pageable pageable = PageRequest.of(0, 10);

        when(userDAO.findAll(pageable)).thenReturn(users);

        int expected = users.size();
        int actual = service.getAll(0,10).size();
        assertEquals(expected,actual);
        verify(userDAO,times(1)).findAll(pageable);
    }

    @Test
    void shouldReturnUserById(){
        User user = new User();
        user.setId(1L);

        when(userDAO.findByID(user.getId())).thenReturn(Optional.of(user));

        User expected = user;
        User actual = service.getByID(user.getId());
        assertEquals(expected,actual);
    }

    @Test
    void shouldThrow_NoSuchUserException(){
        User user = new User();
        user.setId(1L);

        doThrow(new NoSuchUserException()).when(userDAO).findByID(user.getId());
        assertThrows(NoSuchUserException.class,()->{
            service.getByID(user.getId());
        });
    }

    @Test
    void updateShouldThrow_UnsupportedOperationException(){
        assertThrows(UnsupportedOperationException.class,()->{
            service.update(new User(),1L);
        });
    }

    @Test
    void saveShouldThrow_UnsupportedOperationException(){
        assertThrows(UnsupportedOperationException.class,()->{
            service.save(new User());
        });
    }

    @Test
    void deleteShouldThrow_UnsupportedOperationException(){
        assertThrows(UnsupportedOperationException.class,()->{
            service.delete(1L);
        });
    }
}
