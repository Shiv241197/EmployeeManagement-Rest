package com.example.RestApi2.service;

import com.example.RestApi2.model.Client;
import com.example.RestApi2.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    /*
     * Generates a unique Client ID in the format "client-001", "client-002", etc.
     */
    private String generateClientId() {
        Optional<Client> lastClient = clientRepository.findTopByOrderByIdDesc();
        int nextId = lastClient.map(c -> {
            try {
                return Integer.parseInt(c.getClientId().replace("client-", "")) + 1;
            } catch (NumberFormatException e) {
                throw new RuntimeException("Error in generating Client ID", e);
            }
        }).orElse(1);
        return String.format("client-%03d", nextId);
    }

    /*
     * Creates a new client with a unique Client ID.
     */
    @Transactional
    public Client addClient(Client client) {
        if (client.getClientId() == null || client.getClientId().isEmpty()) {
            client.setClientId(generateClientId());
        }
        return clientRepository.save(client);
    }

    /*
     * Retrieves all clients.
     */
    @Transactional
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    /*
     * Retrieves a client by ID.
     */
    @Transactional
    public Optional<Client> getClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        client.ifPresent(c -> c.getProjects().size()); // Force loading
        return client;
    }


    /*
     * Updates an existing client.
     */
    @Transactional
    public Client updateClient(Long id, Client updatedClient) {
        return clientRepository.findById(id).map(client -> {
            client.setClientName(updatedClient.getClientName());
            client.setClientRelationshipDate(updatedClient.getClientRelationshipDate());

            if (updatedClient.getContactPersons() != null) {
                client.setContactPersons(updatedClient.getContactPersons());
            }

            if (updatedClient.getProjects() != null) {
                client.setProjects(updatedClient.getProjects());
            }

            return clientRepository.save(client);
        }).orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
    }

    /*
     * Deletes a client by ID.
     */
    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found with ID: " + id);
        }
        clientRepository.deleteById(id);
    }
}
