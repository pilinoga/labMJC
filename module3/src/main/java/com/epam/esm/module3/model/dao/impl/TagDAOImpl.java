package com.epam.esm.module3.model.dao.impl;

import com.epam.esm.module3.model.dao.TagDAO;
import com.epam.esm.module3.model.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TagDAOImpl implements TagDAO {
    private static final String NAME_COLUMN = "name";
    private static final String ID_COLUMN = "id";
    private static final String FIND_ALL = "select t from Tag t";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Tag> findByID(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> cr = cb.createQuery(Tag.class);
        Root<Tag> root = cr.from(Tag.class);
        TypedQuery<Tag> query = entityManager.createQuery(
                cr.select(root).where(cb.equal(root.get(ID_COLUMN),Long.toString(id))));
        return query.getResultStream().findFirst();
    }

    @Override
    public List<Tag> findAll(Pageable pageable) {
        return entityManager.createQuery(FIND_ALL,Tag.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public List<Tag> findTags() {
       return entityManager.createQuery(FIND_ALL,Tag.class).getResultList();
    }

    @Override
    public Tag save(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void delete(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Tag> delete = cb.
                createCriteriaDelete(Tag.class);
        Root<Tag> root = delete.from(Tag.class);
        delete.where(cb.equal(root.get(ID_COLUMN), id));
        entityManager.createQuery(delete).executeUpdate();
    }

    @Override
    public Optional<Tag> findByName(Tag tag) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> cr = cb.createQuery(Tag.class);
        Root<Tag> root = cr.from(Tag.class);
        TypedQuery<Tag> query = entityManager.createQuery(
                cr.select(root).where(cb.like(root.get(NAME_COLUMN),tag.getName())));
        return query.getResultStream().findFirst();
    }

}
