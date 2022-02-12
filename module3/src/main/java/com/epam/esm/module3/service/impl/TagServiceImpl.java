package com.epam.esm.module3.service.impl;

import com.epam.esm.module3.model.dao.OrderDAO;
import com.epam.esm.module3.model.dao.TagDAO;
import com.epam.esm.module3.model.entity.Tag;
import com.epam.esm.module3.model.entity.User;
import com.epam.esm.module3.service.TagService;
import com.epam.esm.module3.service.exception.NoSuchTagException;
import com.epam.esm.module3.service.exception.UniqueNameTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagServiceImpl implements TagService {
    private final TagDAO tagDAO;
    private final OrderDAO orderDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO,OrderDAO orderDAO ) {
        this.tagDAO = tagDAO;
        this.orderDAO = orderDAO;
    }

    @Override
    public List<Tag> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tagDAO.findAll(pageable);
    }

    @Override
    public Tag save(Tag tag) {
        Optional<Tag> byName = tagDAO.findByName(tag);
        if(byName.isPresent()){
            throw new UniqueNameTagException();
        }
        return tagDAO.save(tag);
    }

    @Override
    public void delete(Long id) {
        Optional<Tag> byID = tagDAO.findByID(id);
        if(byID.isEmpty()){
            throw new NoSuchTagException();
        }
        tagDAO.delete(id);
    }

    @Override
    public Tag getByID(Long id) {
        Optional<Tag> byID = tagDAO.findByID(id);
        if(byID.isEmpty()){
            throw new NoSuchTagException();
        }
        return byID.get();
    }

    @Override
    public Tag getMostUsedTagFromHighestCostUser(){
        User user = orderDAO.findUserWithHighestOrdersCost().getUser();
        List<Tag> tags = new ArrayList<>();
        user.getOrders().stream()
                .map(o -> o.getCertificate().getTags()).forEach(tags::addAll);
        Map<Tag, Long> map= tags.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Long count = map.values().stream().max(Long::compareTo).get();
        return map.entrySet().stream()
                .filter(e -> e.getValue().equals(count)).findFirst().get().getKey();
    }

    @Override
    public Tag update(Tag tag, Long id) {
        throw new UnsupportedOperationException();
    }
}
