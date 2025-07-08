package com.example.RestApi2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.RestApi2.model.User;

import java.util.Optional;

/*
 * Repository interface for managing User entities in the database.
 * Extends JpaRepository to provide built-in CRUD operations.
 */
@Repository // Marks this interface as a Spring Data JPA repository
public interface UserRepository extends JpaRepository<User, Long> {

    /*
     * Finds a user by their username.
     * @param username The username to search for.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findByUsername(String username);

    /*
     * Finds a user by their email address.
     * @param email The email to search for.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findByEmail(String email);
}
