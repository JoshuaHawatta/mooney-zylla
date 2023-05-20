package com.joshuahawatta.moneyzilla.configurations.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;

/**
 * Filter where all requests will be captured for authenticate
 */
public class JwtAuthenticationFilter extends GenericFilterBean {
    /**
     * Stablishes authentication of requests here
     * @param req  The request to process
     * @param res The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     *
     * @throws IOException when doFilter() method goes wrong.
     * @throws ServletException when doFilter() method goes wrong.
     */
    @Override
    public void doFilter(
        ServletRequest req, ServletResponse res, FilterChain chain
    ) throws IOException, ServletException {
        Authentication authentication = new JwtAuthenticationService().getToken((HttpServletRequest) req);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(req, res);
    }
}
