package com.epam.esm.dao;

import com.epam.esm.model.Tag;
import java.util.List;

public interface TagDAO {
    List<Tag> getAll();
    Tag getByID(int id);
    Long save(Tag tag);
    void delete(int id);
    Tag getTag(Tag tagFromUser);

}
