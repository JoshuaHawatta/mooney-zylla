package com.joshuahawatta.moneyzilla.services;

import com.joshuahawatta.moneyzilla.configurations.security.AuthenticationService;
import com.joshuahawatta.moneyzilla.configurations.security.JwtService;
import com.joshuahawatta.moneyzilla.dtos.user.CreateOrUpdateAccountDto;
import com.joshuahawatta.moneyzilla.dtos.user.LoginDto;
import com.joshuahawatta.moneyzilla.dtos.user.UserDto;
import com.joshuahawatta.moneyzilla.configurations.validations.Validations;
import com.joshuahawatta.moneyzilla.entities.User;
import com.joshuahawatta.moneyzilla.helpers.Message;
import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
public class UserService {
    private static final String PASSWORD_EQUALS_NAME_MESSAGE = "A senha não pode ser igual ao nome!";
    private final UserRepository repository;
    private final Validations validations;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public UserService(
            UserRepository repository,
            Validations validations,
            PasswordEncoder encoder,
            JwtService jwtService,
            AuthenticationService authenticationService
    ) {
        this.repository = repository;
        this.validations = validations;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    public Map<String, Object> login(LoginDto user) {
        validations.validate(user);

        var foundUser = (User) authenticationService.loadUserByUsername(user.email());

        if(!encoder.matches(user.password(), foundUser.getPassword()))
            throw new IllegalArgumentException("As senhas não batem!");

        var results = new HashMap<String, Object>();

        try {
            results.put("user", new UserDto(foundUser));
            results.put("token", jwtService.generateToken(foundUser));
            Message.putOnJson(String.format("Bem-vindo, %s!", foundUser.getName()), results);

            return results;
        } catch (AuthenticationException e) {
            throw new AccessDeniedException("E-mail ou senha inválidos!");
        }
    }

    public Map<String, Object> save(CreateOrUpdateAccountDto user) {
        validations.validate(user);

        repository.findByEmail(user.email()).ifPresent(account -> {
            throw new IllegalArgumentException("Um usuário já está usando esse e-mail!");
        });

        if(user.name().equals(user.password()))
            throw new IllegalArgumentException(PASSWORD_EQUALS_NAME_MESSAGE);
        else if(!user.confirmPassword().equals(user.password()))
            throw new IllegalArgumentException("As senhas não são iguais!");

        var newAccount = new User(
            user.name().trim(),
            user.email().trim(),
            encoder.encode(user.password().trim()),
            user.money()
        );

        repository.save(newAccount);

        var results = new HashMap<String, Object>();

        results.put("user", new UserDto(newAccount));
        results.put("token", jwtService.generateToken(newAccount));
        Message.putOnJson(String.format("Bem-vindo, %s!", newAccount.getName()), results);

        return results;
    }

    public UserDto update(User loggedUser, CreateOrUpdateAccountDto user) {
        validations.validate(user);

        repository.findByEmail(user.email()).ifPresent(account -> {
            if (!account.getId().equals(loggedUser.getId()))
               throw new IllegalArgumentException("Um usuário já está usando esse e-mail!");
        });

        if(user.name().equals(user.password())) throw new IllegalArgumentException(PASSWORD_EQUALS_NAME_MESSAGE);

        loggedUser.setName(user.name().trim());
        loggedUser.setEmail(user.email().trim());
        loggedUser.setPassword(encoder.encode(user.password().trim()));
        loggedUser.setMoney(new BigDecimal(user.money().toString()));

        repository.save(loggedUser);

        return new UserDto(loggedUser);
    }

    public Map<String, String> deleteAccount(User loggedUser) {
        repository.deleteById(loggedUser.getId());

        var resultMessage = new HashMap<String, String>();
        resultMessage.put("message", "Até mais, obrigado pelos peixes!");

        return resultMessage;
    }
}
