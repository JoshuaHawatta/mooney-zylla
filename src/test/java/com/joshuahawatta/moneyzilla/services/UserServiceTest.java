package com.joshuahawatta.moneyzilla.services;

import com.joshuahawatta.moneyzilla.configurations.security.AuthenticationService;
import com.joshuahawatta.moneyzilla.configurations.security.JwtService;
import com.joshuahawatta.moneyzilla.configurations.validations.Validations;
import com.joshuahawatta.moneyzilla.dtos.user.CreateOrUpdateAccountDto;
import com.joshuahawatta.moneyzilla.dtos.user.LoginDto;
import com.joshuahawatta.moneyzilla.dtos.user.UserDto;
import com.joshuahawatta.moneyzilla.entities.User;
import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class UserServiceTest {
    private AutoCloseable autoCloseable;
    private final CreateOrUpdateAccountDto createOrUpdateAccountDto = new CreateOrUpdateAccountDto(
            "user",
            "email@email.com",
            "pass_word",
            "pass_word",
            new BigDecimal("31.99")
    );

    @Mock
    private UserRepository repository;

    @Mock
    private Validations validations;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() { autoCloseable = MockitoAnnotations.openMocks(this); }

    @AfterEach
    void tearDown() throws Exception { autoCloseable.close(); }

    @Test
    @DisplayName("should return a Hash Map when login succeeds")
    void loginHappy() {
        var loginDto = new LoginDto("email@fake.com", "fake_passwd");
        var foundUser = new User();

        foundUser.setEmail(loginDto.email());
        foundUser.setPassword(loginDto.password());

        when(authenticationService.loadUserByUsername(loginDto.email())).thenReturn(foundUser);
        when(encoder.matches(loginDto.password(), foundUser.getPassword())).thenReturn(true);

        var results = userService.login(loginDto);

        assertInstanceOf(HashMap.class, results);

        verify(authenticationService, times(1))
                .loadUserByUsername(loginDto.email());

        verify(encoder, times(1))
                .matches(loginDto.password(), foundUser.getPassword());
        verify(validations, times(1)).validate(loginDto);
    }

    @Test
    @DisplayName("should throw an exception when login fails")
    void loginUnhappy() {
        var loginDto = new LoginDto("email@fake.com", "fake_passwd");
        var foundUser = new User();

        foundUser.setEmail(loginDto.email());
        foundUser.setPassword(loginDto.password());

        assertThrows(Exception.class, () -> userService.login(loginDto));

         verify(authenticationService, times(1))
                 .loadUserByUsername(loginDto.email());
        verify(validations, times(1)).validate(loginDto);
    }


    @Test
    @DisplayName("Shoudld return a Hash Map when creating an account succeeds")
    void saveHappy() {
        var newAccount = new User(
                createOrUpdateAccountDto.name(),
                createOrUpdateAccountDto.email(),
                null,
                createOrUpdateAccountDto.money()
        );

        when(repository.findByEmail(createOrUpdateAccountDto.email())).thenReturn(Optional.empty());

        var results = userService.save(createOrUpdateAccountDto);

        assertInstanceOf(HashMap.class, results);
        verify(validations, times(1)).validate(createOrUpdateAccountDto);
        verify(jwtService, times(1)).generateToken(newAccount);
        verify(encoder, times(1)).encode(createOrUpdateAccountDto.password());
    }

    @Test
    @DisplayName("Shoudld thrown an exception when creating an account fails")
    void saveUnhappy() {
        var newAccount = new User(null, createOrUpdateAccountDto.email(), null, null);

        when(repository.findByEmail(createOrUpdateAccountDto.email())).thenReturn(Optional.of(newAccount));
        assertThrows(Exception.class, () -> userService.save(createOrUpdateAccountDto));
    }


    @Test
    @DisplayName("Should return an User DTO when updating account succeeds")
    void updateHappy() {
        var newAccount = new User(
                createOrUpdateAccountDto.name(),
                createOrUpdateAccountDto.email(),
                createOrUpdateAccountDto.password(),
                createOrUpdateAccountDto.money()
        );

        newAccount.setId(1L);

        when(repository.findByEmail(createOrUpdateAccountDto.email())).thenReturn(Optional.of(newAccount));

        var results = userService.update(newAccount, createOrUpdateAccountDto);

        assertInstanceOf(UserDto.class, results);
        verify(repository, times(1)).findByEmail(newAccount.getEmail());
        verify(validations, times(1)).validate(createOrUpdateAccountDto);
    }

    @Test
    @DisplayName("Should throw an exception when updating account fails")
    void updateUnhappy() {
        var newAccount = new User(
                createOrUpdateAccountDto.name(),
                createOrUpdateAccountDto.email(),
                createOrUpdateAccountDto.password(),
                createOrUpdateAccountDto.money()
        );

        assertThrows(Exception.class, () -> userService.update(newAccount, createOrUpdateAccountDto));
        verify(repository, times(1)).findByEmail(newAccount.getEmail());
        verify(validations, times(1)).validate(createOrUpdateAccountDto);
    }

    @Test
    @DisplayName("Should return a Hash Map when deleting an account succeeds")
    void deleteAccountHappy() {
        var newAccount = new User();

        newAccount.setId(1L);

        when(repository.findById(newAccount.getId())).thenReturn(Optional.of(newAccount));

        var results = userService.deleteAccount(newAccount);

        assertInstanceOf(HashMap.class, results);
        verify(repository, times(1)).deleteById(newAccount.getId());
        verify(repository, times(1)).findById(newAccount.getId());
    }

    @Test
    @DisplayName("Should return a Hash Map when deleting an account succeeds")
    void deleteAccountUnhappy() {
        var newAccount = new User();

        newAccount.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> userService.deleteAccount(newAccount));
        verify(repository, times(1)).findById(newAccount.getId());
    }
}