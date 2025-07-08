package com.example.RestApi2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity  // Marks this class as a JPA entity, meaning it is mapped to a database table.
@Getter  // Lombok annotation to automatically generate getter methods.
@Setter  // Lombok annotation to automatically generate setter methods.
public class Client {

    @Id  // Specifies this field as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    // Uses the database’s auto-increment feature to generate unique IDs.
    private Long id;

    @Column(unique = true, nullable = false)  
    // Ensures `clientId` is unique and cannot be null.
    private String clientId;  // Custom Client ID (e.g., client-001, client-002).

    @Column(nullable = false)  // Ensures `clientName` cannot be null.
    private String clientName; // Stores the company name.

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)  
    // Defines a one-to-many relationship with `ContactPerson`.
    // `mappedBy = "client"` indicates that `ContactPerson` owns the relationship.
    // `cascade = CascadeType.ALL` means all changes to `Client` cascade to `ContactPerson`.
    // `orphanRemoval = true` ensures that if a `ContactPerson` is removed from the list, it is also deleted from the database.
    @JsonIgnore  // This will prevent serialization issues
    private Set<ContactPerson> contactPersons;  // A client can have multiple contact persons.

    @Column(nullable = false)  // Ensures `clientRelationshipDate` cannot be null.
    private LocalDate clientRelationshipDate;  // Stores the date of the relationship.

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
 
    // Defines a one-to-many relationship with `Project`.
    // If a client is deleted, all associated projects are also deleted.
    @JsonManagedReference("client-project") // Manages the reference properly
    // Prevents infinite recursion when serializing `Client` along with its related `Project` objects.
    private Set<Project> projects;  // A client can have multiple projects.

}
