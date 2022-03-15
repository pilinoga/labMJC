package com.epam.esm.module3.model.dao.impl;

import com.epam.esm.module3.model.dao.UserDAO;
import com.epam.esm.module3.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {
    private static final String ID_COLUMN = "id";
    private static final String FIND_ALL = "select u from User u";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByID(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        TypedQuery<User> query = entityManager.createQuery(
                cr.select(root).where(cb.equal(root.get(ID_COLUMN),Long.toString(id))));
        return query.getResultStream().findFirst();
    }

    @Override
    public List<User> findAll(Pageable pageable) {
        return entityManager.createQuery(FIND_ALL,User.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        TypedQuery<User> query = entityManager.createQuery(
                cr.select(root).where(cb.equal(root.get("login"),login)));
        return query.getResultStream().findFirst();
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
