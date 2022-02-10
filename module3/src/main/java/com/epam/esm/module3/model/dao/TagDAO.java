package com.epam.esm.module3.model.dao;

import com.epam.esm.module3.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDAO extends DAO<Tag> {
    Optional<Tag> findByName(Tag tag);
    List<Tag> findTags();

}
