package com.epam.esm.module3.controller.security.jwt;

import com.epam.esm.module3.model.entity.Role;
import com.epam.esm.module3.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class is implementation of spring security UserDetails interface.
 */
public class UserDetailsImpl implements UserDetails {
    private final String login;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String login, String password, Collection<? extends GrantedAuthority> authorities) {
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Method to create UserDetails from user
     *
     * @param user contains login,password,roles
     * @return user for spring
     */
    public static UserDetailsImpl create(User user){
        return new UserDetailsImpl(
                user.getLogin(),
                user.getPassword(),
                mapToGrantedAuthority(new ArrayList<>(user.getRoles()))
        );
    }

    /**
     * Method to convert role to authority
     *
     * @param userRoles list of roles
     * @return list of authorities
     */
    private static List<GrantedAuthority> mapToGrantedAuthority(List<Role> userRoles){
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
