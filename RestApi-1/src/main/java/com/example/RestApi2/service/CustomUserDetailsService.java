package com.example.RestApi2.service;

import com.example.RestApi2.model.User;
import com.example.RestApi2.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Custom implementation of UserDetailsService to load user details from the database.
 * This service is used by Spring Security for authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired // Injects the UserRepository to interact with the database
    private UserRepository userRepository;

    /*
     * Loads a user by their username.
     * @param username The username of the user to load.
     * @return UserDetails object containing user authentication details.
     * @throws UsernameNotFoundException If the user is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), // Retrieves username
                user.getPassword(), // Retrieves hashed password
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())) // Assigns role-based authority
        );
    }
}
