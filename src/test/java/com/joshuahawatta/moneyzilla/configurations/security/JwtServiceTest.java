package com.joshuahawatta.moneyzilla.configurations.security;

import com.joshuahawatta.moneyzilla.entities.User;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
class JwtServiceTest {
    private AutoCloseable closeable;
    private final User USER = new User(null, "user@email.com", null, null);

    @Mock
    private JwtService jwtService;

    @BeforeEach
    void initTest() {
        closeable = openMocks(this);
    }

    @AfterEach
    void destroyTest() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("should generate a JWT")
    void generateTokenTest() {
        when(jwtService.generateToken(USER)).thenReturn("fake JWT");

        var jwt = jwtService.generateToken(USER);

        Assertions.assertNotNull(jwt);
        Assertions.assertInstanceOf(String.class, jwt);
        verify(jwtService, times(1)).generateToken(USER);
    }

    @Test
    @DisplayName("should get the e-mail of the JWT's subject")
    void getTokenSubjectTest() {
        when(jwtService.getTokenSubject("fake_token")).thenReturn("user@email.com");

        var subject = jwtService.getTokenSubject("fake_token");

        Assertions.assertNotNull(subject);
        Assertions.assertInstanceOf(String.class, subject);
        verify(jwtService, times(1)).getTokenSubject("fake_token");
    }
}