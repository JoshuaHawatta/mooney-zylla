package com.joshuahawatta.moneyzilla.configurations.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshuahawatta.moneyzilla.helpers.Message;
import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    ) throws IOException {
        try {
            var authorization = req.getHeader("Authorization");

            if(authorization == null)
                throw new NullPointerException("Você não tem a autorização necessária para fazer isso!");

            var token = authorization.replace("Bearer ", "");
            var subject = service.getTokenSubject(token);

            var user = repository.findByEmail(subject)
                    .orElseThrow(() -> new NullPointerException("Você não está autenticado!"));

            var authenticatedUser = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

            nextFilter.doFilter(req, res);
        } catch(Exception exception) {
            var writer = res.getWriter();

            res.setContentType("application/json");
            res.setStatus(HttpStatus.UNAUTHORIZED.value());

            writer.print(new ObjectMapper().writeValueAsString(Message.asJson(exception.getMessage())));
            writer.flush();
        }
    }
}
