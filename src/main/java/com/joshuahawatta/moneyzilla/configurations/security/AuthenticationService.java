package com.joshuahawatta.moneyzilla.configurations.security;

import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null || username.isBlank()) throw new IllegalArgumentException("E-mail obrigatório!");

        return repository.findByEmail(username)
                .orElseThrow(() -> new NullPointerException("Não achamos o usuário deste e-mail!"));
    }
}