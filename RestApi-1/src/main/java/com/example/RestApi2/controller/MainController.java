package com.example.RestApi2.controller;
//import org.springframework.http.HttpStatus;

import com.example.RestApi2.model.Employee;
import com.example.RestApi2.model.Project;
import com.example.RestApi2.model.Client;
import com.example.RestApi2.service.EmployeeService;
import com.example.RestApi2.service.ProjectService;
import com.example.RestApi2.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
 * MainController acts as the entry point for handling API requests
 * related to Employees, Projects, and Clients.
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')") // ✅ Ensures only ROLE_ADMIN can access
public class MainController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ClientService clientService;

    // ---------------- EMPLOYEE APIs ----------------

    /*
     * Endpoint to add a new Employee
     * @param employee Employee object from request body
     * @return Saved Employee object
     */   
    
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
    return employeeService.addEmployee(employee);
    }
    
    //ADDING EMPLOYEE BY RETURNING SIMPLE VOID
    //This approach is perfectly valid if you don't need to return any data in the response.
   /*@PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }*/


    
    /*
     * Endpoint to retrieve all employees
     * @return List of all employees
     */
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /*
     * Endpoint to retrieve an employee by ID
     * @param id Employee ID from path variable
     * @return Employee object if found
     */
    @GetMapping("/employees/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable String id) {
        return employeeService.getEmployeeById(id);
    }

    /*
     * Endpoint to update an employee
     * @param id Employee ID from path variable
     * @param updatedEmployee Updated Employee object from request body
     * @return Updated Employee object
     */
    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) {
        return employeeService.updateEmployee(id, updatedEmployee);
    }

    /*
     * Endpoint to delete an employee
     * @param id Employee ID from path variable
     */
    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
    }
    /*
     * ✅ New API to assign an employee to a project
     */
    @PostMapping("/employees/{employeeId}/assignProject/{projectId}")
    public ResponseEntity<Employee> assignProject(@PathVariable String employeeId, @PathVariable String projectId) {
        Employee updatedEmployee = employeeService.assignProjectToEmployee(employeeId, projectId);
        return ResponseEntity.ok(updatedEmployee);
    }


    // ---------------- PROJECT APIs ----------------

    /*
     * Endpoint to create a new project
     * @param project Project object from request body
     * @return ResponseEntity containing saved project
     */
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        System.out.println("Received project details: " + project);
        Project savedProject = projectService.saveOrUpdateProject(project);
        return ResponseEntity.ok(savedProject);
    }

    /*
     * Endpoint to retrieve all projects
     * @return List of all projects
     */
    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    /*
     * Endpoint to retrieve a project by ID
     * @param id Project ID from path variable
     * @return Project object if found
     */
    @GetMapping("/projects/{id}")
    public Optional<Project> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    /*
     * Endpoint to update an existing project
     * @param id Project ID from path variable
     * @param updatedProject Updated Project object from request body
     * @return Updated Project object
     */
    @PutMapping("/projects/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody Project updatedProject) {
        return projectService.updateProject(id, updatedProject);
    }

    /*
     * Endpoint to delete a project
     * @param id Project ID from path variable
     */
    @DeleteMapping("/projects/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    // ---------------- CLIENT APIs ----------------

    

     
    @PostMapping("/clients")
    public Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    /*
     * Endpoint to retrieve all clients
     * @return List of all clients
     */
    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    /*
     * Endpoint to retrieve a client by ID
     * @param id Client ID from path variable
     * @return Client object if found
     */
    @GetMapping("/clients/{id}")
    public Optional<Client> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    /*
     * Endpoint to update an existing client
     * @param id Client ID from path variable
     * @param updatedClient Updated Client object from request body
     * @return Updated Client object
     */
    @PutMapping("/clients/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        return clientService.updateClient(id, updatedClient);
    }

    /*
     * Endpoint to delete a client
     * @param id Client ID from path variable
     */
    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}
