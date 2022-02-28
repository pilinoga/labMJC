package com.epam.esm.module3.model.dao;

import com.epam.esm.module3.model.entity.User;

import java.util.Optional;

/**
 * Interface UserDAO is designed for basic work with database
 * for user entity.
 */

public interface UserDAO extends DAO<User> {

    Optional<User> findByLogin(String login);
}
