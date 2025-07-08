package com.example.RestApi2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.RestApi2.model.User;
import com.example.RestApi2.service.UserService;

/**
 * Authentication Controller for handling user login and registration.
 * Provides REST endpoints for authentication and user management.
 */
@RestController
@RequestMapping("/api/auth") // Base URL for authentication-related endpoints
public class AuthController {

    @Autowired // Injects AuthenticationManager to handle authentication logic
    private AuthenticationManager authenticationManager;

    @Autowired // Injects UserService for user registration
    private UserService userService;

    /*
     * Endpoint for user login.
     * @param user The user object containing username and password.
     * @return ResponseEntity with login success message.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        // Authenticate user credentials
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        // Store authentication details in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("Login successful!");
    }

    /*
     * Endpoint for user registration.
     * @param user The user object containing registration details.
     * @return ResponseEntity with the registered user object.
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        // Register the new user using UserService
        User newUser = userService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }
}
