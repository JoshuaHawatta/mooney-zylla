package com.joshuahawatta.moneyzilla.security;

import com.joshuahawatta.moneyzilla.services.user.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**Class for handling URLs, authorizations, adresses, authorizes or blocks accesses of URLs*/
@Configuration
@EnableWebSecurity
public class WebConfigSecurity {
    public static final String LOGIN_ROUTE = "/user/login";

    /**
     * every route that ends with: .permitAll() means there´s no need for authentication.
     * send user back to login route if it´s not authenticated.
     * csrfTokenRepository method says that only users with tokens can send requests for the server (excluding above settings).
     * @param http set routes accesses
     * @return the build of your configuration of this bean
     * @throws Exception when an unknown error is found
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.userDetailsService(new UserDetailsServiceImpl());

        http.csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .disable()
            .authorizeHttpRequests(req -> {
                    req.requestMatchers("/", "/index", LOGIN_ROUTE, "/user/register").permitAll();

                    try {
                        req.anyRequest()
                            .authenticated().and()
                            .logout().logoutSuccessUrl(LOGIN_ROUTE)
                            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"));
                    } catch (Exception e) {
                        throw new AccessDeniedException("Ops, não reconhecemos sua toalha. Identifique-se, mochileiro(a)!");
                    }
                }
            )
            .httpBasic();

        //filtrar requisições de login para autenticação do JWT no header http.

        return http.build();
    }
}
