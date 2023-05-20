package com.joshuahawatta.moneyzilla.configurations.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshuahawatta.moneyzilla.models.Users;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

/** Stabilishes the Token managment/manager */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
    JwtAuthenticationService service;

    /**
     * Oblige the constructor to always authenticate URL.
     * @param url get the URL that is trying to authenticate
     */
    public JwtLoginFilter(String url, AuthenticationManager authenticationManager, JwtAuthenticationService authenticationService) {
        super(new AntPathRequestMatcher(url));
        this.service = authenticationService;
        setAuthenticationManager(authenticationManager);
    }

    /**
     * @param req used to get the user by it´s token.
     * @param res only there to complete the @Override annotation and not throwing any errors.
     *@return the processed user after process the authentication.
     */
    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest req,
        HttpServletResponse res
    ) throws AuthenticationException, IOException {
        Users user = new ObjectMapper().readValue(req.getInputStream(), Users.class);

        return getAuthenticationManager()
            .authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), user.getAuthorities())
            );
    }

    /**
     * get the logged user by the token of the response.
     * @param req only there to complete the @Override annotation and not throwing any errors.
     * @param res get the user by the response.
     * @param filterChain only there to complete the @Override annotation and not throwing any errors.
     * @param authentication help getting user data from the token.
     * @throws IOException only there to complete the @Override annotation and not throwing any errors.
     */
    @Override
    protected void successfulAuthentication(
        HttpServletRequest req,
        HttpServletResponse res,
        FilterChain filterChain, Authentication authentication
    ) throws IOException {
        service.generateToken(res, authentication.getName());
    }
}
