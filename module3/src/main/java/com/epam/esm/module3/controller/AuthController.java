package com.epam.esm.module3.controller;

import com.epam.esm.module3.controller.dto.TokenDto;
import com.epam.esm.module3.controller.dto.UserDto;
import com.epam.esm.module3.controller.dto.converter.DtoConverter;
import com.epam.esm.module3.controller.exception.UserValidationException;
import com.epam.esm.module3.controller.security.jwt.JwtProvider;
import com.epam.esm.module3.model.entity.User;
import com.epam.esm.module3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
 * Class AuthController represent api which allows to perform authentication and registration.
 */
@RestController
@RequestMapping
public class AuthController {
    private final UserService service;
    private final DtoConverter<User,UserDto> converter;
    private final AuthenticationManager manager;
    private final JwtProvider provider;

    @Autowired
    public AuthController(UserService service, DtoConverter<User, UserDto> converter,
                          AuthenticationManager manager, JwtProvider provider) {
        this.service = service;
        this.converter = converter;
        this.manager = manager;
        this.provider = provider;
    }

    /**
     * Method to perform authentication
     *
     * @param dto contain login and password
     * @param bindingResult contain validation errors
     * @return token
     */
    @PostMapping(value = "/api/auth")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto signIn(@Valid @RequestBody UserDto dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new UserValidationException();
        }
        String login = dto.getLogin();
        manager.authenticate(new UsernamePasswordAuthenticationToken(login, dto.getPassword()));
        User user = service.getByLogin(login);
        String token = provider.createToken(user);
        return new TokenDto(login,token);
    }

    /**
     * Method for registration
     *
     * @param dto contain login and password
     * @param bindingResult contain validation errors
     * @return registered user
     */
    @PostMapping(value = "/api/regist")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto signUp(@Valid @RequestBody UserDto dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new UserValidationException();
        }
        User user= service.save(converter.convert(dto));
        return converter.convert(user);
    }
}
