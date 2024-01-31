package com.joshuahawatta.moneyzilla.configurations.security;

import com.joshuahawatta.moneyzilla.entities.User;
import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
class AuthenticationServiceTest {
    private AutoCloseable closeable;
    private final User USER = new User(null, "user@email.com", null, null);

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void initTest() {
        closeable = openMocks(this);
    }

    @AfterEach
    void destroyTest() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Should return a user when there is one with the choosen e-mail")
    void loadUserByUsernameHappy() {
        when(userRepository.findByEmail(USER.getEmail())).thenReturn(Optional.of(USER));

        var foundedUser = (User) authenticationService.loadUserByUsername(USER.getEmail());

        Assertions.assertNotNull(foundedUser);
        verify(userRepository, times(1)).findByEmail(USER.getEmail());
    }

    @Test
    @DisplayName("Should throw an exception when no user was found on the choosen e-mail")
    void loadUserByUsernameUnhappy() {
        USER.setEmail(null);

        Assertions.assertThrows(
                Exception.class,
                () -> this.authenticationService.loadUserByUsername(USER.getEmail())
        );
    }
}