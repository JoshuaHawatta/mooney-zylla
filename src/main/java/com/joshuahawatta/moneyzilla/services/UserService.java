package com.joshuahawatta.moneyzilla.services;

import com.joshuahawatta.moneyzilla.dtos.user.UserDto;
import com.joshuahawatta.moneyzilla.configurations.Validations;
import com.joshuahawatta.moneyzilla.models.User;
import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final String INVALID_ID_MESSAGE = "ID inválido!";
    private static final String ACCOUNT_NOT_FOUND_MESSAGE = "Conta não encontrada!";
    private static final String PASSWORD_EQUALS_NAME_MESSAGE = "A senha não pode ser igual ao nome!";

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    Validations validations;

    public List<UserDto> findAll() {
        List<UserDto> users = new ArrayList<>();

        for (User user : repository.findAll()) users.add(new UserDto(user));

        return users;
    }

    public UserDto findById(Long id) {
        if(id == null || id <= 0) throw new IllegalArgumentException(INVALID_ID_MESSAGE);

        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) throw new NullPointerException(ACCOUNT_NOT_FOUND_MESSAGE);

        return new UserDto(user.get());
    }

    public UserDto login(User user) {
        if(user.getEmail() == null || user.getEmail().isBlank())
            throw new IllegalArgumentException("E-mail obrigatório!");
        else if(user.getPassword() == null || user.getPassword().isBlank())
            throw new IllegalArgumentException("Senha obrigatória!");

        Optional<User> existingUser = repository.findByEmail(user.getEmail().trim());

        if (existingUser.isEmpty()) throw new NullPointerException(ACCOUNT_NOT_FOUND_MESSAGE);

        User foundUser = existingUser.get();

        if(!encoder.matches(user.getPassword(), foundUser.getPassword()))
            throw new AccessDeniedException("A senha digitada não é a mesma cadastrada!");

        return new UserDto(foundUser);
    }

    public UserDto save(User user) {
        validations.validate(user);
        if(user.getName().equals(user.getPassword())) throw new IllegalArgumentException(PASSWORD_EQUALS_NAME_MESSAGE);

        User newUser = new User(
            user.getName().trim(),
            user.getEmail().trim(),
            encoder.encode(user.getPassword().trim()),
            user.getMoney()
        );

        repository.save(newUser);

        return new UserDto(newUser);
    }

    public UserDto update(Long id, User user) {
        if(id == null || id <= 0) throw new IllegalArgumentException(INVALID_ID_MESSAGE);

        Optional<User> existingUser = repository.findById(id);

        if(existingUser.isEmpty()) throw new NullPointerException(ACCOUNT_NOT_FOUND_MESSAGE);

        User foundUser = existingUser.get();

        validations.validate(user);
        if(user.getName().equals(user.getPassword())) throw new IllegalArgumentException(PASSWORD_EQUALS_NAME_MESSAGE);
        if(user.getPassword() == null || user.getPassword().isBlank()) throw new IllegalArgumentException("Senha inválida!");

        foundUser.setName(user.getName().trim());
        foundUser.setEmail(user.getEmail().trim());
        foundUser.setPassword(encoder.encode(user.getPassword().trim()));
        foundUser.setMoney(new BigDecimal("" + user.getMoney() + ""));

        repository.save(foundUser);

        return new UserDto(foundUser);
    }

    public void deleteById(Long id) {
        if(id == null || id <= 0) throw new IllegalArgumentException(INVALID_ID_MESSAGE);

        Optional<User> existingUser = repository.findById(id);

        if(existingUser.isEmpty()) throw new NullPointerException(ACCOUNT_NOT_FOUND_MESSAGE);

        User foundUser = existingUser.get();
        repository.deleteById(foundUser.getId());
    }
}
