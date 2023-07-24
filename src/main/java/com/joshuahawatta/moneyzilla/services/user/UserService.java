package com.joshuahawatta.moneyzilla.services.user;

import com.joshuahawatta.moneyzilla.configurations.security.AuthenticationService;
import com.joshuahawatta.moneyzilla.configurations.security.JwtService;
import com.joshuahawatta.moneyzilla.dtos.user.CreateAccountDto;
import com.joshuahawatta.moneyzilla.dtos.user.UserDto;
import com.joshuahawatta.moneyzilla.configurations.validations.Validations;
import com.joshuahawatta.moneyzilla.entities.Users;
import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
public class UserService {
    private static final String INVALID_ID_MESSAGE = "ID inválido!";
    private static final String ACCOUNT_NOT_FOUND_MESSAGE = "Conta não encontrada!";
    private static final String PASSWORD_EQUALS_NAME_MESSAGE = "A senha não pode ser igual ao nome!";

    @Autowired
    UserRepository repository;

    @Autowired
    Validations validations;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationService authenticationService;

    public List<UserDto> findAll() {
        List<UserDto> users = new ArrayList<>();

        for (Users user : repository.findAll()) users.add(new UserDto(user));

        return users;
    }

    public UserDto findById(Long id) {
        if(id == null || id <= 0) throw new IllegalArgumentException(INVALID_ID_MESSAGE);

        var user = repository.findById(id);

        if (user.isEmpty()) throw new NullPointerException(ACCOUNT_NOT_FOUND_MESSAGE);

        return new UserDto(user.get());
    }

    public Map<String, Object> login(Users user) {
        if (user.getEmail() == null || user.getEmail().isBlank())
            throw new IllegalArgumentException("E-mail obrigatório!");
        else if (user.getPassword() == null || user.getPassword().isBlank())
            throw new IllegalArgumentException("Senha obrigatória!");

        var foundUser = (Users) authenticationService.loadUserByUsername(user.getEmail());

        if(!encoder.matches(user.getPassword(), foundUser.getPassword()))
            throw new IllegalArgumentException("As senhas não batem!");

        Map<String, Object> results = new HashMap<>();

        try {
            results.put("user", new UserDto(foundUser));
            results.put("token", jwtService.generateToken(foundUser));

            return results;
        } catch (AuthenticationException e) {
            throw new AccessDeniedException("E-mail ou senha inválidos!");
        }
    }

    public Map<String, Object> save(CreateAccountDto user) {
        validations.validate(user);
        var existingAccount = repository.findByEmail(user.getEmail());

        if(existingAccount.isPresent()) throw new IllegalArgumentException("Um usuário já está usando esse e-mail!");
        else if(user.getName().equals(user.getPassword()))
            throw new IllegalArgumentException(PASSWORD_EQUALS_NAME_MESSAGE);
        else if(user.getPassword() == null || user.getPassword().isBlank())
            throw new IllegalArgumentException("Digite uma senha válida!");
        else if(!user.getConfirmPassword().equals(user.getPassword()))
            throw new IllegalArgumentException("As senhas não são iguais!");

        var newAccount = new Users(
            user.getName().trim(),
            user.getEmail().trim(),
            encoder.encode(user.getPassword().trim()),
            user.getMoney()
        );

        repository.save(newAccount);

        Map<String, Object> results = new HashMap<>();

        results.put("user", new UserDto(newAccount));
        results.put("token", jwtService.generateToken(newAccount));

        return results;
    }

    public UserDto update(Long id, Users users) {
        if(id == null || id <= 0) throw new IllegalArgumentException(INVALID_ID_MESSAGE);

        Optional<Users> existingUser = repository.findById(id);

        if(existingUser.isEmpty()) throw new NullPointerException(ACCOUNT_NOT_FOUND_MESSAGE);

        Users foundUsers = existingUser.get();

        validations.validate(users);
        if(users.getName().equals(users.getPassword())) throw new IllegalArgumentException(PASSWORD_EQUALS_NAME_MESSAGE);
        if(users.getPassword() == null || users.getPassword().isBlank()) throw new IllegalArgumentException("Senha inválida!");

        foundUsers.setName(users.getName().trim());
        foundUsers.setEmail(users.getEmail().trim());
        foundUsers.setPassword(encoder.encode(users.getPassword().trim()));
        foundUsers.setMoney(new BigDecimal(users.getMoney().toString()));

        repository.save(foundUsers);

        return new UserDto(foundUsers);
    }

    public void deleteById(Long id) {
        if(id == null || id <= 0) throw new IllegalArgumentException(INVALID_ID_MESSAGE);

        Optional<Users> existingUser = repository.findById(id);

        if(existingUser.isEmpty()) throw new NullPointerException(ACCOUNT_NOT_FOUND_MESSAGE);

        Users foundUsers = existingUser.get();
        repository.deleteById(foundUsers.getId());
    }
}
