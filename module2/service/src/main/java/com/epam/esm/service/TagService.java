package com.epam.esm.service;

import com.epam.esm.model.Tag;
import java.util.List;

public interface TagService {
    List<Tag> getAll();
    Tag getByID(int id);
    Tag save(Tag tag);
    void delete(int id);
}
