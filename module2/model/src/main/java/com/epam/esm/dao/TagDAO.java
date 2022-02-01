package com.epam.esm.dao;

import com.epam.esm.model.Tag;

public interface TagDAO extends DAO<Tag> {
    Tag getTag(Tag tagFromUser);

}
