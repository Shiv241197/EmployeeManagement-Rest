package com.example.RestApi2.service;

import com.example.RestApi2.model.Client;
import com.example.RestApi2.model.Project;
import com.example.RestApi2.repository.ClientRepository;
import com.example.RestApi2.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ClientRepository clientRepository;

    /*
     * Save or update a project.
     * If a new project is created, it generates an ID and validates the client.
     */
    @Transactional
    public Project saveOrUpdateProject(Project project) {
        if (project.getProjectId() == null || project.getProjectId().isEmpty()) {
            project.setProjectId(generateNextProjectId());
        }

        if (project.getClient() == null || project.getClient().getId() == null) {
            throw new RuntimeException("Client ID is required");
        }

        Client client = clientRepository.findById(project.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        project.setClient(client);
        return projectRepository.save(project);
    }

    /*
     * Generates a unique Project ID in the format "project-001", "project-002", etc.
     */
    private String generateNextProjectId() {
        Optional<Project> lastProject = projectRepository.findTopByOrderByIdDesc();

        if (lastProject.isPresent()) {
            try {
                int nextId = Integer.parseInt(lastProject.get().getProjectId().replace("project-", "")) + 1;
                return String.format("project-%03d", nextId);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Error in generating Project ID", e);
            }
        }
        return "project-001";
    }

    /*
     * Retrieves all projects.
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /*
     * Retrieves a project by ID.
     */
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    /*
     * Updates an existing project.
     */
    @Transactional
    public Project updateProject(Long id, Project updatedProject) {
        return projectRepository.findById(id).map(project -> {
            project.setProjectName(updatedProject.getProjectName());
            project.setProjectStartDate(updatedProject.getProjectStartDate());
            project.setProjectEndDate(updatedProject.getProjectEndDate());

            if (updatedProject.getClient() != null && updatedProject.getClient().getId() != null) {
                Client client = clientRepository.findById(updatedProject.getClient().getId())
                        .orElseThrow(() -> new RuntimeException("Client not found"));
                project.setClient(client);
            }

            project.setEmployees(updatedProject.getEmployees());
            return projectRepository.save(project);
        }).orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));
    }

    /*
     * Deletes a project by ID.
     */
    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found with ID: " + id);
        }
        projectRepository.deleteById(id);
    }
}
