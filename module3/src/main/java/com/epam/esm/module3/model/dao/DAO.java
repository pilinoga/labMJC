package com.epam.esm.module3.model.dao;

import com.epam.esm.module3.model.entity.AbstractEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DAO <T extends AbstractEntity> {
    Optional<T> findByID(Long id);
    List<T> findAll(Pageable pageable);
    T save(T t);
    void delete(Long id);

}
