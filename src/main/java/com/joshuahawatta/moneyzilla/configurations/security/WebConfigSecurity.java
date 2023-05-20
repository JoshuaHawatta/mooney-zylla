package com.joshuahawatta.moneyzilla.configurations.security;

import com.joshuahawatta.moneyzilla.configurations.security.jwt.JwtAuthenticationFilter;
import com.joshuahawatta.moneyzilla.configurations.security.jwt.JwtLoginFilter;
import com.joshuahawatta.moneyzilla.services.user.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**Class for handling URLs, authorizations, adresses, authorizes or blocks accesses of URLs*/
@Configuration
@EnableWebSecurity
public class WebConfigSecurity {
    public static final String LOGIN_ROUTE = "/user/login";

    /** @return a new BCryptPasswordEncoder */
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    /**
     * set the UserDetailsService and the password encoder
     * @return a new DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(new UserDetailsServiceImpl());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Always use the same instance of authenticationConfiguration at authenticationManager() and JwtLoginFilter()
     * @return a new Authentication configuration
     */
    @Bean
    AuthenticationConfiguration authenticationConfiguration() { return new AuthenticationConfiguration(); }

    /**
     * @param authenticationConfiguration uses the authenticationConfiguration() bean above this one.
     * @return a instance of an authentication manager.
     * @throws Exception when something goes wrong.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return this.authenticationConfiguration().getAuthenticationManager();
    }

    /**
     * used to set what routes are public, witch require authentication and authentication filters.
     * @param http for handling routing and authentication
     * @return a new SecurityFilterChain
     * @throws Exception when something goes wrong
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .disable()
            .authorizeHttpRequests(req ->
                req.requestMatchers("/", "/index", "/home", "/dashboard", LOGIN_ROUTE, "/user/register")
                    .permitAll()
                    .anyRequest().authenticated()
            )
            .logout()
            .logoutSuccessUrl(LOGIN_ROUTE)
            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            .and()
            .addFilterBefore(new JwtLoginFilter(LOGIN_ROUTE, this.authenticationConfiguration().getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .httpBasic();

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }
}
