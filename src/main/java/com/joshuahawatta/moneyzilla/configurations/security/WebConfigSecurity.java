package com.joshuahawatta.moneyzilla.configurations.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**Class for handling URLs, authorizations, adresses, authorizes or blocks accesses of URLs*/
@Configuration
@EnableWebSecurity
public class WebConfigSecurity {
    private static final String LOGIN_ROUTE = "/user/login";

    /** @return a new BCryptPasswordEncoder */
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
