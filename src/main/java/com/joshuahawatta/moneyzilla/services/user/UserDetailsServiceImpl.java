package com.joshuahawatta.moneyzilla.services.user;

import com.joshuahawatta.moneyzilla.models.Users;
import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository repository;

    private static final String ACCOUNT_NOT_FOUND_MESSAGE = "Conta não encontrada!";

    /**
     * @param username used for founding the user on the database by it´s e-mail.
     * @return a new userDetails.User (do not confund with Users, the entity model).
     * @throws UsernameNotFoundException when user is not found, throw this exception (only to respect the implementation).
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null || username.isBlank()) throw new IllegalArgumentException("E-mail obrigatório!");

        Optional<Users> existingUser = repository.findByEmail(username.trim());

        if (existingUser.isEmpty()) throw new NullPointerException(ACCOUNT_NOT_FOUND_MESSAGE);

        Users foundUsers = existingUser.get();

        return new User(foundUsers.getUsername(), foundUsers.getPassword(), foundUsers.getAuthorities());
    }
}
