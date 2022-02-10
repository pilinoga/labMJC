package com.epam.esm.module3.model.dao.impl;

import com.epam.esm.module3.model.dao.CertificateDAO;
import com.epam.esm.module3.model.entity.Certificate;
import com.epam.esm.module3.model.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class CertificateDAOImpl implements CertificateDAO {
    private static final String FIND_ALL = "select c from Certificate c";
    private static final String DELETE = "delete from Certificate where id =:id";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String JOIN_TAGS = "tags";
    private static final String PERCENT = "%";
    private static final String NAME_PARAMETER = "name";
    private static final String TAG_PARAMETER = "tag";
    private static final String SORT_PARAMETER = "sort";
    private static final String ASC_SORT = "asc";
    private final String date = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Certificate> findByID(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> cr = cb.createQuery(Certificate.class);
        Root<Certificate> root = cr.from(Certificate.class);
        TypedQuery<Certificate> query = entityManager.createQuery(
                cr.select(root).where(cb.equal(root.get(ID_COLUMN),Long.toString(id))));
        return query.getResultStream().findFirst();
    }

    @Override
    public List<Certificate> findAll(Pageable pageable){
         return entityManager.createQuery(FIND_ALL, Certificate.class)
                 .setFirstResult((int) pageable.getOffset())
                 .setMaxResults(pageable.getPageSize())
                 .getResultList();
    }

    @Override
    public Certificate save(Certificate certificate) {
        certificate.setCreateDate(date);
        certificate.setLastUpdateDate(date);
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
    public void delete(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Certificate> delete = cb.
                createCriteriaDelete(Certificate.class);
        Root<Certificate> root = delete.from(Certificate.class);
        delete.where(cb.equal(root.get(ID_COLUMN), id));
        entityManager.createQuery(delete).executeUpdate();
    }

    @Override
    public Certificate update(Certificate certificate) {
        certificate.setLastUpdateDate(date);
        entityManager.merge(certificate);
        return certificate;
    }

    @Override
    public Optional<Certificate> findByName(Certificate certificate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> cr = cb.createQuery(Certificate.class);
        Root<Certificate> root = cr.from(Certificate.class);
        TypedQuery<Certificate> query = entityManager.createQuery(
        cr.select(root).where(cb.like(root.get(NAME_COLUMN),certificate.getName())));
        return query.getResultStream().findFirst();
    }

    @Override
    public List<Certificate> findByFilter(MultiValueMap<String, String> params){
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> cq = qb.createQuery(Certificate.class);
        Root<Certificate> root = cq.from(Certificate.class);
        List<Predicate> predicates = new ArrayList<>();

        if( params.get(NAME_PARAMETER)!=null){
            String name = params.get(NAME_PARAMETER).get(0);
            predicates.add(
                    qb.like(root.get(NAME_COLUMN),PERCENT+ name +PERCENT));
        }
        if(params.get(TAG_PARAMETER)!=null){
            List<String> tags = params.get(TAG_PARAMETER);
            for (String tag:tags){
                Join<Certificate,Tag> joinTags = root.join(JOIN_TAGS);
                predicates.add(
                        qb.equal(joinTags.get(NAME_COLUMN),tag));
            }
        }

        cq.select(root)
                .where(predicates.toArray(new Predicate[]{}));
        List<Certificate> resultList = entityManager.createQuery(cq).getResultList();

        if(params.get(SORT_PARAMETER) != null){
            if(params.get(SORT_PARAMETER).get(0).equalsIgnoreCase(ASC_SORT)){
                return resultList.stream()
                        .sorted(Comparator.comparing(Certificate::getName)).collect(Collectors.toList());
            }else{
                return resultList.stream()
                        .sorted(Comparator.comparing(Certificate::getName).reversed()).collect(Collectors.toList());
            }
        }
        return resultList;
    }
}
