package com.epam.esm.module3.service;

import com.epam.esm.module3.model.entity.Tag;

public interface TagService extends AbstractService<Tag>{
    Tag getMostUsedTagFromHighestCostUser();
}
