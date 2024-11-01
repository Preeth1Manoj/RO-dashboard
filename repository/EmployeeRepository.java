package com.report.ro.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.report.ro.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.project WHERE e.id = :employeeId")
    Optional<Employee> findEmployeeWithProjectById(@Param("employeeId") Long employeeId);
    
    List<Employee> findByProjectId(Long projectId);
}
