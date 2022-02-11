package com.epam.esm.module3.controller.dto.converter;

import com.epam.esm.module3.model.entity.AbstractEntity;

/**
 *  DtoConverter is designed for converting objects from E type to D type;
 *
 * @param <E> entity
 * @param <D> dto
 */

public interface DtoConverter <E extends AbstractEntity, D>{

    /**
     * Method to convert dto to entity
     *
     * @param dto object for converting
     * @return entity
     */
    E convert(D dto);

    /**
     * Method to convert entity to dto
     *
     * @param entity object for converting
     * @return dto
     */
    D convert(E entity);

}
