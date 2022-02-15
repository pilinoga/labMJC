package com.epam.esm.module3.service.impl;

import com.epam.esm.module3.model.dao.impl.OrderDAOImpl;
import com.epam.esm.module3.model.dao.impl.TagDAOImpl;
import com.epam.esm.module3.model.entity.Certificate;
import com.epam.esm.module3.model.entity.Order;
import com.epam.esm.module3.model.entity.Tag;
import com.epam.esm.module3.model.entity.User;
import com.epam.esm.module3.service.exception.NoSuchTagException;
import com.epam.esm.module3.service.exception.UniqueNameTagException;
import org.apache.el.parser.AstEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    private List<Tag> tags = new ArrayList<>();
    private Tag tag = new Tag();

    @Mock
    private TagDAOImpl tagDAO = Mockito.mock(TagDAOImpl.class);
    @Mock
    private OrderDAOImpl orderDAO = Mockito.mock(OrderDAOImpl.class);

    @InjectMocks
    private TagServiceImpl service;

    @BeforeEach
    public void setup() {
        tag = new Tag(5L,"TEST");
        tags.add(new Tag(1L,"test"));
        tags.add(new Tag(2L, "test2"));
        tags.add(new Tag(3L, "test3"));
        tags.add(new Tag(4L, "test4"));
    }

    @Test
    void updateShouldThrow_UnsupportedOperationException(){
        assertThrows(UnsupportedOperationException.class,()->{
            service.update(new Tag(),1L);
        });
    }

    @Test
    void shouldSaveTag(){
        when(tagDAO.save(tag)).thenReturn(tag);
        service.save(tag);
        verify(tagDAO,times(1)).save(tag);
    }

    @Test
    void shouldThrow_UniqueNameTagException(){
        doThrow(new UniqueNameTagException()).when(tagDAO).save(tag);
        assertThrows(UniqueNameTagException.class,()->{
            service.save(tag);
        });
    }

    @Test
    void shouldReturnTagById(){
        when(tagDAO.findByID(tag.getId())).thenReturn(Optional.of(tag));
        Tag expected = tag;
        Tag actual = service.getByID(tag.getId());
        assertEquals(expected,actual);
    }

    @Test
    void shouldThrow_NoSuchTagException(){
        doThrow(new NoSuchTagException()).when(tagDAO).findByID(tag.getId());
        assertThrows(NoSuchTagException.class,()->{
            service.getByID(tag.getId());
        });
    }

    @Test
    void shouldDeleteTag(){
        doNothing().when(tagDAO).delete(anyLong());
        when(tagDAO.findByID(anyLong())).thenReturn(Optional.of(tag));
        service.delete(anyLong());
        verify(tagDAO,times(1)).delete(anyLong());
    }

    @Test
    void shouldThrow_NoSuchTagExceptionInDelete(){
        doThrow(new NoSuchTagException()).when(tagDAO).findByID(anyLong());
        assertThrows(NoSuchTagException.class,()->{
            service.delete(anyLong());
        });
    }

    @Test
    void shouldReturnTags(){
        Pageable pageable = PageRequest.of(0, 10);
        when(tagDAO.findAll(pageable)).thenReturn(tags);
        int expected = tags.size();
        int actual = service.getAll(0,10).size();
        assertEquals(expected,actual);
        verify(tagDAO,times(1)).findAll(pageable);
    }

    @Test
    void shouldReturnMostUsedTag(){
        User user = new User();
        List<Order> orders = new ArrayList<>();
        Certificate certificate1= new Certificate();
        Certificate certificate2= new Certificate();
        certificate1.setTags(new HashSet<>(tags));
        Set<Tag> tags2 = new HashSet<>();
        tags2.add(new Tag(1L,"test"));
        certificate2.setTags(tags2);
        Order order1 = new Order();
        order1.setCertificate(certificate1);
        Order order2 = new Order();
        order2.setCertificate(certificate2);
        Order order3 = new Order();
        order3.setCertificate(certificate2);
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        user.setOrders(orders);
        Order orderFromBD = new Order();
        orderFromBD.setUser(user);

        when(orderDAO.findUserWithHighestOrdersCost()).thenReturn(orderFromBD.getUser());
        Tag actual = service.getMostUsedTagFromUserWithHighOrdersCost();
        Tag expected = new Tag(1L,"test");
        assertEquals(expected,actual);

    }

}
