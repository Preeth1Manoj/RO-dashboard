package com.report.ro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.report.ro.model.EmployeeProject;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {
 List<EmployeeProject> findByEmployeeIdAndActiveTrue(Long employeeId);
 boolean existsByEmployeeIdAndProjectIdAndActiveTrue(Long employeeId, Long projectId);
}