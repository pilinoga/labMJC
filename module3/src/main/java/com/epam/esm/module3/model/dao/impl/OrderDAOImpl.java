package com.epam.esm.module3.model.dao.impl;

import com.epam.esm.module3.model.dao.OrderDAO;
import com.epam.esm.module3.model.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderDAOImpl implements OrderDAO {
    private static final String ID_COLUMN = "id";
    private static final String FIND_ALL= "select o from Order o ";
    private final String date = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Order> findByID(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cr = cb.createQuery(Order.class);
        Root<Order> root = cr.from(Order.class);
        TypedQuery<Order> query = entityManager.createQuery(
                cr.select(root).where(cb.equal(root.get(ID_COLUMN),Long.toString(id))));
        return query.getResultStream().findFirst();
    }

    @Override
    public List<Order> findAll(Pageable pageable) {
        return entityManager.createQuery(FIND_ALL, Order.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public Order save(Order order) {
        order.setPurchaseDate(date);
        entityManager.persist(order);
        return order;
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order findUserWithHighestOrdersCost(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cr = cb.createQuery(Order.class);
        Root<Order> root = cr.from(Order.class);
        return entityManager.createQuery(
        cr.select(root)
                .groupBy(root.get("user"))
                .orderBy(cb.desc(cb.sum(root.get("price")))))
                .setMaxResults(1).getSingleResult();
    }
}
