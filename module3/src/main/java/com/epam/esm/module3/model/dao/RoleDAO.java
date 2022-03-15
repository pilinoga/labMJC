package com.epam.esm.module3.model.dao;

import com.epam.esm.module3.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
