package com.joshuahawatta.moneyzilla.configurations.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.joshuahawatta.moneyzilla.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {
    private final String jwtSecret;
    public static final String ISSUER = "User";

    public JwtService(@Value("${JWT_SECRET}") String jwtSecret) { this.jwtSecret = jwtSecret; }

    public String generateToken(User user) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getUsername())
                .withClaim("id", user.getId())
                .withExpiresAt(LocalDateTime.now().plusHours(8L).toInstant(ZoneOffset.of("-03:00")))
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String getTokenSubject(String token) {
        return JWT.require(Algorithm.HMAC256(jwtSecret)).withIssuer(ISSUER).build().verify(token).getSubject();
    }
}
