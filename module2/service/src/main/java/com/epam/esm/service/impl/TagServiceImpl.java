package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class TagServiceImpl implements TagService {

    private TagDAO tagDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public List<Tag> getAll() {
        return tagDAO.getAll();
    }

    @Override
    public Tag getByID(int id) {
        return tagDAO.getByID(id);
    }

    @Override
    public Tag save(Tag tag) {
        long id = tagDAO.save(tag);
        tag.setId(id);
        return tag;
    }

    @Override
    public void delete(int id) {
        tagDAO.delete(id);
    }

}
