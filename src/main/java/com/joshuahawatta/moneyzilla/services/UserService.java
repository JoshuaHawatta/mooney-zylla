package com.joshuahawatta.moneyzilla.services;

import com.joshuahawatta.moneyzilla.dtos.user.UserDto;
import com.joshuahawatta.moneyzilla.configurations.Validations;
import com.joshuahawatta.moneyzilla.models.Users;
import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementing UserDetailsService for handling user authorizations, finding by username method, etc...
 */
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

    /**
     * @return a list of UserDtos. If the list is empty, only return the empty arrays with no errors.
     */
    public List<UserDto> findAll() {
        List<UserDto> users = new ArrayList<>();

        for (Users user : repository.findAll()) users.add(new UserDto(user));

        return users;
    }

    /**
     * @param id for founding the user.
     * @return an UserDto so we can validate the results.
     * @throws IllegalArgumentException when there´s missing the id.
     * @throws NullPointerException when the account wasn´t able to be found.
     */
    public UserDto findById(Long id) {
        if(id == null || id <= 0) throw new IllegalArgumentException(INVALID_ID_MESSAGE);

        Optional<Users> user = repository.findById(id);

        if (user.isEmpty()) throw new NullPointerException(ACCOUNT_NOT_FOUND_MESSAGE);

        return new UserDto(user.get());
    }

    /**
     * @param users passing the whole Users class as param, we can validate the whole data when the user tries to login.
     * @return an UserDto so we can validate his data after it.
     * @throws IllegalArgumentException when there´s missing content or the validations has failed.
     * @throws NullPointerException when the user account wasn´t able to be found.
     * @throws AccessDeniedException when the user´s password ain´t the same as the account registered on the database.
     */
    public UserDto login(Users users) {
        if(users.getEmail() == null || users.getEmail().isBlank())
            throw new IllegalArgumentException("E-mail obrigatório!");
        else if(users.getPassword() == null || users.getPassword().isBlank())
            throw new IllegalArgumentException("Senha obrigatória!");

        Optional<Users> existingUser = repository.findByEmail(users.getEmail().trim());

        if (existingUser.isEmpty()) throw new NullPointerException(ACCOUNT_NOT_FOUND_MESSAGE);

        Users foundUsers = existingUser.get();

        if(!encoder.matches(users.getPassword(), foundUsers.getPassword()))
            throw new AccessDeniedException("A senha digitada não é a mesma cadastrada!");

        return new UserDto(foundUsers);
    }

    /**
     * @param users for creating an account and already return the results.
     * @return an UserDto, so when the user creates an account, we can validate his data.
     * @throws IllegalArgumentException when there´s missing content.
     */
    public UserDto save(Users users) {
        validations.validate(users);
        if(users.getName().equals(users.getPassword())) throw new IllegalArgumentException(PASSWORD_EQUALS_NAME_MESSAGE);

        Users newUsers = new Users(
            users.getName().trim(),
            users.getEmail().trim(),
            encoder.encode(users.getPassword().trim()),
            users.getMoney()
        );

        repository.save(newUsers);

        return new UserDto(newUsers);
    }

    /**
     * @param id needs an id for founding the user.
     * @param users an Users entity model type as parameter aswell.
     * @return an UserDto, so when the user update his data, he already recives it back.
     * @throws IllegalArgumentException when the validations does not pass or there´s missing content.
     */
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
        foundUsers.setMoney(new BigDecimal("" + users.getMoney() + ""));

        repository.save(foundUsers);

        return new UserDto(foundUsers);
    }

    /**
     * @param id for searching the user at database.
     * @throws IllegalArgumentException when the id is invalid.
     * @throws NullPointerException when there´s no account to delete on that id.
     */
    public void deleteById(Long id) {
        if(id == null || id <= 0) throw new IllegalArgumentException(INVALID_ID_MESSAGE);

        Optional<Users> existingUser = repository.findById(id);

        if(existingUser.isEmpty()) throw new NullPointerException(ACCOUNT_NOT_FOUND_MESSAGE);

        Users foundUsers = existingUser.get();
        repository.deleteById(foundUsers.getId());
    }
}
