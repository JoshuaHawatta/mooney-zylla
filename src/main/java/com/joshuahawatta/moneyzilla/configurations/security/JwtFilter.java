package com.joshuahawatta.moneyzilla.configurations.security;

import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtService service;

    @Autowired
    UserRepository repository;

    @Override
    protected void doFilterInternal(
        HttpServletRequest req,
        HttpServletResponse res,
        FilterChain nextFilter
    ) throws ServletException, IOException {
        String token;
        var authorization = req.getHeader("Authorization");

        if(authorization != null) {
            token = authorization.replace("Bearer ", "");

            var subject = service.getTokenSubject(token);
            var user = repository.findByEmail(subject);

            if(user.isEmpty())
                throw new NullPointerException("Você não está autenticado! Faça o login e tente de novo.");

            var authentication = new UsernamePasswordAuthenticationToken(
                    user.get(), null, user.get().getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        nextFilter.doFilter(req, res);
    }
}
