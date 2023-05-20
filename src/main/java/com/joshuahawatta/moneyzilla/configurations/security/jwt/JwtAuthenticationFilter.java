package com.joshuahawatta.moneyzilla.configurations.security.jwt;

import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;

/**
 * Filter where all requests will be captured for authenticate
 */
@Component
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtAuthenticationService jwtAuthenticationService;

    /**
     * handling dependency injection (must have the same repository as JwtAuthenticationService).
     * @param userRepository the repository that will do the user fetching.
     * @param jwtAuthenticationService the authentication service that will handle JWT.
     */
    public JwtAuthenticationFilter(UserRepository userRepository, JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    /**
     * Stablishes authentication of requests here
     * @param req  The request to process
     * @param res The response associated with the request
     * @param chain filter user if he´s authenticated by the http requests and responses.
     * @throws IOException when doFilter() method goes wrong.
     * @throws ServletException when doFilter() method goes wrong.
     */
    @Override
    public void doFilter(
        ServletRequest req, ServletResponse res, FilterChain chain
    ) throws IOException, ServletException {
        Authentication authentication = jwtAuthenticationService.getToken((HttpServletRequest) req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
}
