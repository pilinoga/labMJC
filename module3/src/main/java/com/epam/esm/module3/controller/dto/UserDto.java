package com.epam.esm.module3.controller.dto;

import org.springframework.hateoas.RepresentationModel;
import java.util.Objects;

public class UserDto extends RepresentationModel<UserDto> {
    private Long id;
    private String name;

    public UserDto() {}

    public UserDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        if (!super.equals(o)) return false;

        UserDto userDto = (UserDto) o;

        if (!Objects.equals(id, userDto.id)) return false;
        return Objects.equals(name, userDto.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
