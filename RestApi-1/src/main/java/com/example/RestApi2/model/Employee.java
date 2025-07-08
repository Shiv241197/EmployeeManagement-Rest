package com.example.RestApi2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity  // Marks this class as a JPA entity, meaning it is mapped to a database table.
@Getter  // Lombok annotation to automatically generate getter methods.
@Setter  // Lombok annotation to automatically generate setter methods.
public class Employee {

    @Id  // Marks this field as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generates the primary key using an identity column in the database.
    private String id;

    @Column(unique = true, nullable = false)  // Ensures that the `employeeId` is unique and cannot be null.
    private String employeeId;  // Custom ID format (e.g., JTC-001, JTC-002, etc.).

    @Column(nullable = false)  // Ensures `employeeName` cannot be null in the database.
    private String employeeName;

    @Column(nullable = false)  // Ensures `employeeDept` cannot be null.
    private String employeeDept;

    @Column(unique = true, nullable = false)  // Ensures `employeeEmail` is unique and mandatory.
    private String employeeEmail;

    @Column(nullable = false)  // Ensures `employeePhone` cannot be null.
    private String employeePhone;

    @Column(nullable = false)  // Ensures `employeeDateOfJoining` cannot be null.
    private LocalDate employeeDateOfJoining = LocalDate.now();  // Default value is the current date.

    @ManyToMany  // Defines a many-to-many relationship between employees and projects.
    @JoinTable(
        name = "employee_projects",  // Name of the join table that links employees and projects.
        joinColumns = @JoinColumn(name = "employee_id"),  // The column that refers to employees.
        inverseJoinColumns = @JoinColumn(name = "project_id")  // The column that refers to projects.
    )
    @JsonBackReference("employee-project") // Prevents infinite recursion when serializing JSON responses.
    private Set<Project> projects; // Stores the projects assigned to an employee.
}
