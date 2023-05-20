package com.joshuahawatta.moneyzilla.configurations.security.jwt;

import com.joshuahawatta.moneyzilla.configurations.contextload.ApplicationContextLoad;
import com.joshuahawatta.moneyzilla.models.Users;
import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/** Handle the creation and fetching methods of JWT */
@Service
public class JwtAuthenticationService {
    private static final long TOKEN_EXPIRATION_TIME = TimeUnit.HOURS.toMillis(24);
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_PREFIX = "Authorization";

    @Value("$jwt.secret")
    private String jwtSecret;

    @Autowired
    ApplicationContextLoad appContextLoad;

    /**
     * Create a JWT, writing it also on the header of the requests and on the responses.
     * @param res used for setting the Authorization at Headers and to write on the response to,
     * @param userName the user that we want to authenticate
     * @throws IOException when neither the token was able to add on header or write at response.
     */
    public void generateToken(HttpServletResponse res, String userName) throws IOException {
        final String JWT = Jwts.builder()
            .setSubject(userName)
            .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();

        final String BEARER_TOKEN = TOKEN_PREFIX + JWT;

        //WRITE_TOKEN_ON_HEADERS_OF_REQUESTS
        res.addHeader(HEADER_PREFIX, BEARER_TOKEN);

        //WRITE_TOKEN_AS_JSON_ASWELL
        res.getWriter().write("{\"Authorization\": \"" +  BEARER_TOKEN + "\"}");
    }

    /**
     * Find authenticated token, cheking if it has a token and a user, if not, return null.
     * @param req get 'Authorization' from the request headers.
     * @return a authenticated user that was found by JWT or null if nothing was found.
     */
    public Authentication getToken(HttpServletRequest req) throws ExpiredJwtException, SignatureException {
        String token = req.getHeader(HEADER_PREFIX);

        if(token != null) {
            String userToken = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

            if(userToken != null) {
                Optional<Users> user = appContextLoad
                    .getApplicationContext()
                    .getBean(UserRepository.class)
                    .findByEmail(userToken);

                if(user.isPresent()) {
                    Users foundUser = user.get();

                    return new UsernamePasswordAuthenticationToken(
                        foundUser.getEmail(),
                        foundUser.getPassword(),
                        foundUser.getAuthorities()
                    );
                }

                return null;
            }

            return null;
        }

        return null;
    }
}
