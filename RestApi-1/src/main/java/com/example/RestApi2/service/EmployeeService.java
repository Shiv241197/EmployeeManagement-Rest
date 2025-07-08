package com.example.RestApi2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.RestApi2.model.Employee;
import com.example.RestApi2.model.Project;
import com.example.RestApi2.repository.EmployeeRepository;
import com.example.RestApi2.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service // Marks this class as a Spring Service component, allowing it to be managed as a Spring bean.
public class EmployeeService {

    @Autowired // Automatically injects the EmployeeRepository instance.
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private ProjectRepository projectRepository;

    /*
     * Generates a unique Employee ID in the format "JTC-001", "JTC-002", etc.
     * @return A formatted Employee ID.
     */
    private String generateEmployeeId() {
        Long count = employeeRepository.count() + 1; // Count existing employees and increment
        return String.format("JTC-%03d", count); // Format as "JTC-001", "JTC-002", etc.
    }

    /*
     * Creates a new Employee and assigns a generated Employee ID.
     * @param employee The Employee entity to be added.
     * @return The saved Employee object.
     */
    @Transactional // Ensures database operations are handled within a transaction.
    public Employee addEmployee(Employee employee) {
        employee.setEmployeeId(generateEmployeeId()); // Assign unique Employee ID
        return employeeRepository.save(employee); // Save the employee in the database
    }

    /*
     * Retrieves all employees from the database.
     * @return A list of all Employee entities.
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /*
     * Retrieves an Employee by their unique ID.
     * @param id The Employee ID.
     * @return An Optional containing the Employee if found, otherwise empty.
     */
    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }

    /*
     * Updates an existing Employee's details.
     * @param id The Employee ID of the employee to update.
     * @param updatedEmployee The updated Employee details.
     * @return The updated Employee entity.
     * @throws RuntimeException if the Employee is not found.
     */
    @Transactional // Ensures update operations are done in a transactional context.
    public Employee updateEmployee(String id, Employee updatedEmployee) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setEmployeeName(updatedEmployee.getEmployeeName());
            employee.setEmployeeDept(updatedEmployee.getEmployeeDept());
            employee.setEmployeeEmail(updatedEmployee.getEmployeeEmail());
            employee.setEmployeePhone(updatedEmployee.getEmployeePhone());
            employee.setProjects(updatedEmployee.getProjects());
            return employeeRepository.save(employee); // Save updated employee
        }).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    /*
     * Deletes an Employee by their unique ID.
     * @param id The Employee ID.
     */
    @Transactional // Ensures the delete operation runs within a transaction.
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }
    /*
     * Assigns a project to an employee.
     * If the employee already has a project, it replaces the existing project.
     */
    @Transactional
    public Employee assignProjectToEmployee(String employeeId, String projectId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);

        try {
            Long projectIdLong = Long.parseLong(projectId); // Convert String to Long
            Optional<Project> projectOpt = projectRepository.findById(projectIdLong);

            if (employeeOpt.isPresent() && projectOpt.isPresent()) {
                Employee employee = employeeOpt.get();
                Project project = projectOpt.get();

                employee.getProjects().clear();
                employee.getProjects().add(project);

                return employeeRepository.save(employee);
            } else {
                throw new RuntimeException("Employee or Project not found");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid project ID format: " + projectId);
        }
    }
}
