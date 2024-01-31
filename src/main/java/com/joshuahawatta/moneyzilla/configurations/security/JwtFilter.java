package com.joshuahawatta.moneyzilla.configurations.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshuahawatta.moneyzilla.enums.PublicRoutes;
import com.joshuahawatta.moneyzilla.helpers.Message;
import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final List<String> PUBLIC_URLS = Arrays.stream(PublicRoutes.values())
            .map(PublicRoutes::getRoute).toList();

    private final JwtService service;
    private final UserRepository userRepository;

    public JwtFilter(JwtService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest req,
        HttpServletResponse res,
        FilterChain nextFilter
    ) throws IOException, ServletException {
        if (PUBLIC_URLS.contains(req.getServletPath())) {
            nextFilter.doFilter(req, res);
            return;
        }

        try {
            var authorization = req.getHeader("Authorization");

            if(authorization == null) throw new AccessDeniedException("Você precisa fazer o login!");

            var token = authorization.replace("Bearer ", "");

            if(token.isEmpty()) throw new AccessDeniedException("Você precisa fazer o login!");

            var subject = service.getTokenSubject(token);

            var user = userRepository.findByEmail(subject)
                    .orElseThrow(() -> new NullPointerException("E-mail não encontrado!"));

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
            );

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
