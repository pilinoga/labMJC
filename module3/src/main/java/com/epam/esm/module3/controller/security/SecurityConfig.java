package com.epam.esm.module3.controller.security;

import com.epam.esm.module3.controller.security.jwt.JwtConfigure;
import com.epam.esm.module3.controller.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

/**
 * Class contains spring security configuration.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtProvider jwtProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String CERTIFICATES_ENDPOINT = "/api/certificates/**";
    public static final String ORDERS_ENDPOINT = "/api/orders/**";
    public static final String USERS_ENDPOINT = "/api/users/**";
    public static final String TAGS_ENDPOINT = "/api/tags/**";
    public static final String AUTH_ENDPOINT = "/api/auth";
    public static final String REGIST_ENDPOINT = "/api/regist";

    @Autowired
    public SecurityConfig(JwtProvider jwtProvider,
                          AuthenticationEntryPoint authenticationEntryPoint,
                          AccessDeniedHandler accessDeniedHandler) {
        this.jwtProvider = jwtProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //Public endpoints
                .antMatchers(AUTH_ENDPOINT,REGIST_ENDPOINT).permitAll()
                .antMatchers(GET,CERTIFICATES_ENDPOINT).permitAll()
                //USER endpoints
                .antMatchers(GET,TAGS_ENDPOINT).hasAnyRole(USER,ADMIN)
                .antMatchers(GET,ORDERS_ENDPOINT).hasAnyRole(USER,ADMIN)
                .antMatchers(POST,USERS_ENDPOINT).hasRole(USER)
                //ADMIN endpoints
                .antMatchers(POST,TAGS_ENDPOINT).hasRole(ADMIN)
                .antMatchers(DELETE,TAGS_ENDPOINT).hasRole(ADMIN)
                .antMatchers(GET,USERS_ENDPOINT).hasRole(ADMIN)
                .antMatchers(PUT,CERTIFICATES_ENDPOINT).hasRole(ADMIN)
                .antMatchers(POST,CERTIFICATES_ENDPOINT).hasRole(ADMIN)
                .antMatchers(DELETE,CERTIFICATES_ENDPOINT).hasRole(ADMIN)
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .apply(new JwtConfigure(jwtProvider));
    }

}
