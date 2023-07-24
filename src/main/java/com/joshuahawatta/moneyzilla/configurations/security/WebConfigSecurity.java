package com.joshuahawatta.moneyzilla.configurations.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**Class for handling URLs, authorizations, adresses, authorizes or blocks accesses of URLs*/
@Configuration
@EnableWebSecurity
public class WebConfigSecurity {
    private static final String USERS_ROUTE = "/user";
    private final JwtFilter jwtFilter;

    /**
     * handling dependency injection
     * @param jwtFilter dependency
     */
    public WebConfigSecurity(JwtFilter jwtFilter) { this.jwtFilter = jwtFilter; }

    /**
     * Defines witch route the user can access even if it doesn't have a JWT with it.
     * @param http to set the routes protection by filtering it.
     * @return the HTTP security filter.
     * @throws Exception when something unexpected happen when defining the HTTP routes filtering.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, USERS_ROUTE, USERS_ROUTE + "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/index", "/home", "/").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /** @return a new BCryptPasswordEncoder */
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    /**
     * A bean for configurating Authentication Manager settings
     * @param authenticationConfiguration to return the configurations
     * @return the global instance of the Authentication Configuration
     * @throws Exception When something unexpected happens
     */
    @Bean
    AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
