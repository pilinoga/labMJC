package com.epam.esm.module3.controller.dto.converter.impl;

import com.epam.esm.module3.controller.dto.UserDto;
import com.epam.esm.module3.controller.dto.converter.DtoConverter;
import com.epam.esm.module3.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements DtoConverter<User, UserDto> {
    @Override
    public User convert(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        return user;
    }

    @Override
    public UserDto convert(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLogin(entity.getLogin());
        return dto;
    }
}
