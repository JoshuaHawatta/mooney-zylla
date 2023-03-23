package com.joshuahawatta.moneyzilla.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncript {
    @Bean
    public PasswordEncoder setBycript() {
        return new BCryptPasswordEncoder();
    }
}
