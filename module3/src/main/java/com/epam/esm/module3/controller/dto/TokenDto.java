package com.epam.esm.module3.controller.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class TokenDto extends RepresentationModel<TokenDto> {
    private String login;
    private String token;

    public TokenDto() {
    }

    public TokenDto(String login, String token) {
        this.login = login;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenDto)) return false;
        if (!super.equals(o)) return false;

        TokenDto tokenDto = (TokenDto) o;

        if (!Objects.equals(login, tokenDto.login)) return false;
        return Objects.equals(token, tokenDto.token);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }
}
