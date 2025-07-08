package com.example.RestApi2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.RestApi2.model.User;
import com.example.RestApi2.repository.UserRepository;

import java.util.Optional;

/*
 * Service class for managing user-related operations.
 * This class provides methods for user registration and retrieval by username or email.
 */
@Service // Marks this class as a service component in Spring's application context.
public class UserService {

    @Autowired // Automatically injects the UserRepository dependency.
    private UserRepository userRepository;

    @Autowired // Injects the PasswordEncoder to securely hash passwords.
    private PasswordEncoder passwordEncoder;

    /*
     * Registers a new user by checking for existing username and encrypting the password.
     * @param user The user object containing registration details.
     * @return The saved user entity after successful registration.
     * @throws RuntimeException If the username already exists in the database.
     */
    public User registerUser(User user) {
        System.out.println("Registering user: " + user.getUsername()); // Debug log
        
        // Check if the username is already taken.
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        // Hash the password before storing it in the database.
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        System.out.println("Hashed Password: " + hashedPassword); // Debug log
        
        user.setPassword(hashedPassword); // Set the hashed password to the user object.

        return userRepository.save(user); // Save and return the registered user.
    }

    /*
     * Retrieves a user by their username.
     * @param username The username to search for.
     * @return An Optional containing the user if found, otherwise empty.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /*
     * Retrieves a user by their email.
     * @param email The email to search for.
     * @return An Optional containing the user if found, otherwise empty.
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
