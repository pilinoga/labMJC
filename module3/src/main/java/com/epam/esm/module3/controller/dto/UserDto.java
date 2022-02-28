package com.epam.esm.module3.controller.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Size;
import java.util.Objects;

public class UserDto extends RepresentationModel<UserDto> {
    private Long id;
    @Size(min = 2,max = 10)
    private String name;
    @Size(min = 2,max = 15)
    private String login;
    @Size(min = 2,max = 15)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public UserDto() {}

    public UserDto(Long id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        if (!super.equals(o)) return false;

        UserDto dto = (UserDto) o;

        if (!Objects.equals(id, dto.id)) return false;
        if (!Objects.equals(name, dto.name)) return false;
        if (!Objects.equals(login, dto.login)) return false;
        return Objects.equals(password, dto.password);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
