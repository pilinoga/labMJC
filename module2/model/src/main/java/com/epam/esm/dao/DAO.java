package com.epam.esm.dao;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();
    T getByID(int id);
    long save(T t);
    void delete(int id);
}
