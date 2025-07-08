package com.example.RestApi2.repository;

import com.example.RestApi2.model.Client;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marks this interface as a Spring Data repository, enabling automatic CRUD operations.
public interface ClientRepository extends JpaRepository<Client, Long> {

    /*
     * Finds a client by its custom client ID (e.g., "client-001").
     *
     * @param clientId The custom client ID.
     * @return The Client entity if found, otherwise null.
     */
    Client findByClientId(String clientId);
   // Custom method to get the last inserted client based on ID
    Optional<Client> findTopByOrderByIdDesc();
}

