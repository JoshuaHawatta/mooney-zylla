package com.joshuahawatta.moneyzilla.services.user;

import com.joshuahawatta.moneyzilla.configurations.security.JwtService;
import com.joshuahawatta.moneyzilla.dtos.user.CreateAccountDto;
import com.joshuahawatta.moneyzilla.dtos.user.UserDto;
import com.joshuahawatta.moneyzilla.configurations.validations.Validations;
import com.joshuahawatta.moneyzilla.models.Users;
import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    Validations validations;

    @Autowired
    JwtService jwtService;

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

        var user = repository.findById(id);

        if (user.isEmpty()) throw new NullPointerException(ACCOUNT_NOT_FOUND_MESSAGE);

        return new UserDto(user.get());
    }

    /**
     * @param users passing the whole Users class as param, we can validate the whole data when the user tries to log in.
     * @return a Map so we can return the user data and his new JWT.
     * @throws IllegalArgumentException when there´s missing content or the validations has failed.
     * @throws NullPointerException when the user account wasn´t able to be found.
     * @throws AccessDeniedException when the user´s password ain´t the same as the account registered on the database.
     */
    public Map<String, Object> login(Users users) {
        if (users.getEmail() == null || users.getEmail().isBlank())
            throw new IllegalArgumentException("E-mail obrigatório!");
        else if (users.getPassword() == null || users.getPassword().isBlank())
            throw new IllegalArgumentException("Senha obrigatória!");

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(users.getEmail(), users.getPassword())
        );
        var existingUser = repository.findByEmail(authentication.getName());
        Map<String, Object> results = new HashMap<>();

        try {
            if (existingUser.isEmpty()) throw new NullPointerException("Usuário não encontrado!");

            results.put("user", existingUser.get());
            results.put("token", jwtService.generateToken(existingUser.get()));

            return results;
        } catch (AuthenticationException e) {
            throw new AccessDeniedException("E-mail ou senha inválidos!");
        }
    }

    /**
     * @param user for creating an account and already return the results.
     * @return a hashed map, so when the user creates an account, we can get his data and a new JWT.
     * @throws IllegalArgumentException when there´s missing content.
     */
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

    /**
     * @param id needs an id for founding the user.
     * @param users a Users entity model type as parameter aswell.
     * @return an UserDto, so when the user update his data, he already recives it back.
     * @throws IllegalArgumentException when the validations do not pass or there´s missing content.
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
        foundUsers.setMoney(new BigDecimal(users.getMoney().toString()));

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
