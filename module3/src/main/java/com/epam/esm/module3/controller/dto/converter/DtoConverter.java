package com.epam.esm.module3.controller.dto.converter;

import com.epam.esm.module3.model.entity.AbstractEntity;

public interface DtoConverter <E extends AbstractEntity, D>{

    E convert(D dto);

    D convert(E entity);

}
