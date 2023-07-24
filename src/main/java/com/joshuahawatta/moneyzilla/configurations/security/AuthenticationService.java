package com.joshuahawatta.moneyzilla.configurations.security;

import com.joshuahawatta.moneyzilla.models.Users;
import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = repository.findByEmail(username);

        if(username == null || username.isBlank()) throw new IllegalArgumentException("E-mail obrigatório!");
        else if(user.isEmpty())
            throw new NullPointerException("Não achamos nenhum mochileiro que use essa identificação!");

        return user.get();
    }
}
