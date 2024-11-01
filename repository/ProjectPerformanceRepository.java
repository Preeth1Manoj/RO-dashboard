package com.report.ro.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.report.ro.model.ProjectPerformance;

@Repository
public interface ProjectPerformanceRepository extends JpaRepository<ProjectPerformance, Long> {
    // Basic finder methods
    List<ProjectPerformance> findByProjectId(Long projectId);
    List<ProjectPerformance> findByEmployeeId(Long employeeId);

    // Custom queries with joins
    @Query("SELECT pp FROM ProjectPerformance pp " +
           "LEFT JOIN FETCH pp.project p " +
           "LEFT JOIN FETCH pp.employee e " +
           "WHERE pp.employee.id = :employeeId")
    List<ProjectPerformance> findAllByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT pp FROM ProjectPerformance pp " +
           "LEFT JOIN FETCH pp.project p " +
           "LEFT JOIN FETCH pp.employee e " +
           "WHERE pp.project.id = :projectId")
    List<ProjectPerformance> findAllByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT pp FROM ProjectPerformance pp " +
           "LEFT JOIN FETCH pp.project p " +
           "LEFT JOIN FETCH pp.employee e")
    List<ProjectPerformance> findAllWithProjectAndEmployee();

    @Query("SELECT pp FROM ProjectPerformance pp " +
           "LEFT JOIN FETCH pp.project p " +
           "LEFT JOIN FETCH pp.employee e " +
           "WHERE pp.project.id = :projectId " +
           "AND pp.employee.id = :employeeId")
    List<ProjectPerformance> findByProjectIdAndEmployeeId(
            @Param("projectId") Long projectId,
            @Param("employeeId") Long employeeId
    );

    // Latest performance queries
    @Query("SELECT pp FROM ProjectPerformance pp " +
           "WHERE pp.project.id = :projectId " +
           "AND pp.evaluationDate = (" +
           "    SELECT MAX(p.evaluationDate) " +
           "    FROM ProjectPerformance p " +
           "    WHERE p.project.id = :projectId)")
    ProjectPerformance findLatestByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT pp FROM ProjectPerformance pp " +
           "WHERE pp.employee.id = :employeeId " +
           "AND pp.evaluationDate = (" +
           "    SELECT MAX(p.evaluationDate) " +
           "    FROM ProjectPerformance p " +
           "    WHERE p.employee.id = :employeeId)")
    ProjectPerformance findLatestByEmployeeId(@Param("employeeId") Long employeeId);
}