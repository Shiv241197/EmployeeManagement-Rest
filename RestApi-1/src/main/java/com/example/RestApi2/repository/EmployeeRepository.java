package com.example.RestApi2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RestApi2.model.Employee;
import java.util.Optional;

@Repository  // Marks this interface as a Spring Data repository, enabling automatic CRUD operations.
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    
    /*
     * Custom method to find an Employee by email.
     * 
     * @param email Employee's email (must be unique).
     * @return An Optional containing the Employee if found, otherwise empty.
     */
    Optional<Employee> findByEmployeeEmail(String email);

    /*
     * Checks whether an Employee with the given email exists.
     * 
     * @param email Employee's email.
     * @return true if an employee with the email exists, false otherwise.
     */
    boolean existsByEmployeeEmail(String email);
}
