package com.joshuahawatta.moneyzilla.services;

import com.joshuahawatta.moneyzilla.dtos.user.UserDto;
import com.joshuahawatta.moneyzilla.entities.validations.Validations;
import com.joshuahawatta.moneyzilla.models.User;
import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
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
        if(id == null || id <= 0) throw new IllegalArgumentException("ID inválido!");

        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) throw new NullPointerException("Conta não encontrado!");

        return new UserDto(user.get());
    }

    public void save(User user) {
        validations.setValidations(user);
        if(user.getName().equals(user.getPassword())) throw new IllegalArgumentException("A senha não pode ser igual ao nome!");

        User newUser = new User(user.getName(), user.getEmail(), encoder.encode(user.getPassword()), user.getMoney());
        repository.save(newUser);
    }

    public void update(Long id, User user) {
        if(id == null || id <= 0) throw new IllegalArgumentException("ID inválido!");

        Optional<User> existingUser = repository.findById(id);

        if(existingUser.isEmpty()) throw new NullPointerException("Conta não encontrada!");

        User foundUser = existingUser.get();

        //VALIDATIONS
        validations.setValidations(user);
        if(user.getName().equals(user.getPassword())) throw new IllegalArgumentException("A senha não pode ser igual ao nome!");

        foundUser.setName(user.getName());
        foundUser.setEmail(user.getEmail());
        foundUser.setPassword(encoder.encode(user.getPassword()));
        foundUser.setMoney(new BigDecimal("" + user.getMoney() + ""));

        repository.save(foundUser);
    }

    public void deleteById(Long id) {
        if(id == null || id <= 0) throw new IllegalArgumentException("ID inválido!");

        Optional<User> existingUser = repository.findById(id);

        if(existingUser.isEmpty()) throw new NullPointerException("Conta não encontrada!");

        User foundUser = existingUser.get();
        repository.deleteById(foundUser.getId());
    }
}
