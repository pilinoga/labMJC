package com.epam.esm.module3.controller.security.jwt;

import com.epam.esm.module3.model.entity.Role;
import com.epam.esm.module3.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * Class contains methods for work with JSON web token.
 */
@Component
public class JwtProvider {
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private Long validTime;
    private final UserDetailsService userDetailsService;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer_";
    private static final String ROLES = "roles";

    @Autowired
    public JwtProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    /**
     *Method for creating token
     *
     * @param user contain login for token
     * @return token
     */
    public String createToken(User user){
        Claims claims = Jwts.claims().setSubject(user.getLogin());
        claims.put(ROLES, getRolesName(user.getRoles()));
        Date now = new Date();
        Date validity = new Date(now.getTime() + validTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Method for getting spring authentication
     *
     * @param token user token
     * @return spring authentication
     */
    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getLogin(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    /**
     * Method for getting login from token.
     *
     * @param token contain login
     * @return login
     */
    public String getLogin(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Method for getting token value from request
     *
     * @param req contains token
     * @return token
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Method to validate token
     *
     * @param token string value
     * @return result of validation
     */
    public boolean validateToken(String token){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Method for getting name from role
     *
     * @param roles contain roles
     * @return lis of roles name
     */
    private List<String> getRolesName(List<Role> roles){
        List<String> result = new ArrayList<>();
        roles.forEach(role ->
                result.add(role.getName())
        );
        return result;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}


