package com.report.ro.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.report.ro.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Basic finder methods
    List<Project> findByPm(String pm);
    List<Project> findByAccManager(String accManager);
    List<Project> findByParentProjectIsNull();
    Optional<Project> findByName(String name);
    boolean existsByNameAndParentProjectId(String name, Long parentProjectId);

    // Custom queries for projects
    @Query("SELECT p FROM Project p WHERE LOWER(p.name) LIKE LOWER(concat('%', :keyword, '%')) " +
           "OR LOWER(p.pm) LIKE LOWER(concat('%', :keyword, '%')) " +
           "OR LOWER(p.accManager) LIKE LOWER(concat('%', :keyword, '%'))")
    List<Project> searchProjects(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT p.projectClass FROM Project p WHERE p.projectClass IS NOT NULL")
    List<String> findDistinctProjectClasses();

    // Project hierarchy related queries
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.subProjects WHERE p.parentProject IS NULL")
    List<Project> findAllParentProjects();

    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.subProjects WHERE p.id = :projectId")
    Optional<Project> findProjectWithSubProjects(@Param("projectId") Long projectId);

    // Project with performances
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.performances WHERE p.id = :projectId")
    Optional<Project> findProjectWithPerformances(@Param("projectId") Long projectId);
}