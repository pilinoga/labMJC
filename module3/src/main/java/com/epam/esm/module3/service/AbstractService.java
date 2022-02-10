package com.epam.esm.module3.service;

import com.epam.esm.module3.model.entity.AbstractEntity;
import java.util.List;

public interface AbstractService<T extends AbstractEntity> {
    List<T> getAll(int page, int size);
    T save(T t);
    void delete(Long id);
    T getByID(Long id);
    T update(T t, Long id);
}
