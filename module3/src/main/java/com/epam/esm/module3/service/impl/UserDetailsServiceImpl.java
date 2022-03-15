package com.epam.esm.module3.service.impl;

import com.epam.esm.module3.controller.security.jwt.UserDetailsImpl;
import com.epam.esm.module3.model.dao.UserDAO;
import com.epam.esm.module3.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class is implementation of UserDetailsService.
 * Intended for work with spring security.
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDAO dao;

    @Autowired
    public UserDetailsServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = dao.findByLogin(login).get();
        return UserDetailsImpl.create(user);
    }
}
