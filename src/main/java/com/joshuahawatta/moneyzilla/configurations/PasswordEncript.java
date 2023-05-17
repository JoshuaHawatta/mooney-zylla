package com.joshuahawatta.moneyzilla.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Just a class for configuring the bcrypt of the project and not allowing the users to have their password exposed at the database.
 */
@Configuration
public class PasswordEncript {
    @Bean
    public PasswordEncoder setBycript() {
        return new BCryptPasswordEncoder();
    }
}
