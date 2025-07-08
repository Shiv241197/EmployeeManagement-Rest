package com.example.RestApi2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import static org.springframework.security.config.Customizer.withDefaults;

/*
 * Security configuration class for defining authentication and authorization settings.
 * This class configures Spring Security to manage user authentication and access control.
 */
@Configuration // Marks this class as a Spring configuration class
@EnableWebSecurity // Enables Spring Security in the application
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    /*
     * Constructor injection for UserDetailsService, which loads user-specific data.
     * @param userDetailsService Service responsible for retrieving user details.
     */
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /*
     * Bean for password encoding using BCrypt hashing algorithm.
     * @return BCryptPasswordEncoder instance for encoding passwords securely.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Bean for configuring DaoAuthenticationProvider.
     * DaoAuthenticationProvider is responsible for retrieving user details and validating passwords.
     * the UserDetailsService and validating passwords using the PasswordEncoder.
     * @return Configured DaoAuthenticationProvider instance.
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Set custom user details service
        provider.setPasswordEncoder(passwordEncoder()); // Set password encoder
        return provider;
    }

    /*
     * Bean for managing authentication providers.
     * AuthenticationManager is responsible for processing authentication requests.
     * It uses ProviderManager to handle authentication using the configured authentication provider.
     * @return AuthenticationManager instance configured with DaoAuthenticationProvider.
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(daoAuthenticationProvider())); // Register authentication provider
    }

    /*
     * Configures HTTP security settings for authentication and authorization.
     * * This method defines which endpoints are publicly accessible and which require authentication.
     * It also disables CSRF protection and enables Basic Authentication.
     * @param http HttpSecurity object to define security rules.
     * @return Configured SecurityFilterChain.
     * @throws Exception if an error occurs while configuring security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection (enable it in production if needed)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/register", "/api/auth/login").permitAll() // Allow public access to authentication endpoints
                .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN") // Restrict access to admin endpoints
                .anyRequest().authenticated() // Require authentication for all other requests
            )
            .httpBasic(withDefaults()); // Enable basic authentication
        
        return http.build();
    }
}
