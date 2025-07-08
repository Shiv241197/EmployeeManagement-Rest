package com.example.RestApi2.repository;

import com.example.RestApi2.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository  // Marks this interface as a Spring Data repository, enabling automatic CRUD operations.
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /*
     * Finds a project by its custom project ID (e.g., "project-001").
     * 
     * @param projectId The custom project ID.
     * @return The Project entity if found, otherwise null.
     */
    Project findByProjectId(String projectId);

    /*
     * Retrieves the latest project entry based on the highest ID value.
     * 
     * @return An Optional containing the latest Project if available.
     */
    Optional<Project> findTopByOrderByIdDesc();
}
