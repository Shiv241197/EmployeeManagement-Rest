package com.example.RestApi2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity  // Marks this class as a JPA entity, meaning it will be mapped to a database table.
@Getter  // Lombok annotation to generate getter methods automatically.
@Setter  // Lombok annotation to generate setter methods automatically.
public class Project {

    @Id  // Marks this field as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generates primary key values using the database identity column.
    private Long id;

    @Column(unique = true, nullable = false)  // Ensures `projectId` is unique and cannot be null.
    private String projectId;  // Custom Project ID (e.g., project-001).

    @Column(nullable = false)  // Ensures `projectName` cannot be null.
    @NotNull  // Additional validation to ensure non-null value.
    private String projectName;

    @Column(nullable = false)  // Ensures `projectStartDate` cannot be null.
    @NotNull  // Additional validation to ensure non-null value.
    @FutureOrPresent(message = "Project start date must be today or in the future.")  
    // Ensures the start date is either today or in the future (cannot be in the past).
    private LocalDate projectStartDate;

    @Column(nullable = false)  // Ensures `projectEndDate` cannot be null.
    @NotNull  // Additional validation to ensure non-null value.
    @Future(message = "Project end date must be in the future.")  
    // Ensures the end date is strictly in the future.
    private LocalDate projectEndDate;

    @ManyToOne  // Defines a many-to-one relationship between projects and clients.
    @JoinColumn(name = "client_id", nullable = false) 
    @JsonBackReference("client-project")  // ✅ Ensure this matches the reference in Client.java
    // Foreign key column `client_id` in `projects` table, linking to `clients` table.
    private Client client;

    @ManyToMany  // Defines a many-to-many relationship between projects and employees.
    @JoinTable(
        name = "employee_projects",  // Name of the join table.
        joinColumns = @JoinColumn(name = "project_id"),  // Column referencing `project_id` in the join table.
        inverseJoinColumns = @JoinColumn(name = "employee_id")  // Column referencing `employee_id` in the join table.
    )
    @JsonBackReference("employee-project")  // Handles serialization to prevent infinite recursion in JSON output.
    private Set<Employee> employees;  // A project can have multiple employees assigned.

    /**
     * Ensures that the project end date is after the start date.
     * This validation runs before inserting or updating the record in the database.
     */
    @PrePersist  // Executes before a new record is inserted.
    @PreUpdate  // Executes before an existing record is updated.
    public void validateDates() {
        if (this.projectEndDate != null && this.projectStartDate != null) {
            if (this.projectEndDate.isBefore(this.projectStartDate)) {
                throw new IllegalArgumentException("Project end date cannot be before the start date.");
            }
        }
    }
}
