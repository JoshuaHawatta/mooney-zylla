package com.joshuahawatta.moneyzilla.configurations.security;

import com.joshuahawatta.moneyzilla.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
class JwtFilterTest {
    private AutoCloseable closeable;

    @Mock
    JwtService jwtService;

    @Mock
    UserRepository userRepository;

    @Autowired
    @InjectMocks
    JwtFilter jwtFilter;

    @BeforeEach
    void initTest() {
        closeable = openMocks(this);
    }

    @AfterEach
    void destroyTest() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Should allow user to access route when it has all permissions")
    void doFilterInternalTestHappy() {
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var filterChain = new MockFilterChain();
        var token = "fake_token";

        request.addHeader("Authorization", "Bearer " + token);
        when(jwtService.getTokenSubject(token)).thenReturn("user@email.com");

        Assertions.assertDoesNotThrow(() -> this.jwtFilter.doFilterInternal(request, response, filterChain));
        verify(userRepository, times(1)).findByEmail("user@email.com");
        verify(jwtService, times(1)).getTokenSubject(token);
    }

    @Test
    @DisplayName("Should stop the user from accesssing routes with no permissions")
    void doFilterInternalTestUnhappy() {
        var token = "fake_token";

        when(jwtService.getTokenSubject(token)).thenReturn(null);
        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}