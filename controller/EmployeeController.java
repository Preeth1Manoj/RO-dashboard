package com.report.ro.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.report.ro.service.EmployeeService;
import com.report.ro.service.ProjectPerformanceService;


import jakarta.validation.Valid;

import com.report.ro.dto.EmployeeDTO;
import com.report.ro.dto.ProjectDTO;
import com.report.ro.dto.ProjectPerformanceDTO;
import com.report.ro.dto.response.ApiResponse;
import com.report.ro.mapper.ProjectMapper;
import com.report.ro.model.Employee;
import com.report.ro.model.Project;
import com.report.ro.repository.EmployeeRepository;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	private final EmployeeService employeeService;
    private final ProjectPerformanceService performanceService;
    private final ProjectMapper projectMapper;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(
            EmployeeService employeeService,
            ProjectPerformanceService performanceService,
            ProjectMapper projectMapper,
            EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.performanceService = performanceService;
        this.projectMapper = projectMapper;
        this.employeeRepository = employeeRepository;
    }
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO created = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id, 
            @RequestBody EmployeeDTO employeeDTO) {
        employeeDTO.setId(id);
        EmployeeDTO updated = employeeService.updateEmployee(employeeDTO);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/employees/{id}/performances")
    public ResponseEntity<ApiResponse<List<ProjectPerformanceDTO>>> getEmployeePerformances(
            @PathVariable Long id) {
        try {
        	
            List<ProjectPerformanceDTO> performances = performanceService.getPerformancesByEmployeeId(id);
            if (performances.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "No performances found", performances));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Performances retrieved successfully", performances));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, "Error retrieving performances: " + e.getMessage(), null));
        }
    }
    @PostMapping("/employees/{id}/performances")
    public ResponseEntity<ApiResponse<ProjectPerformanceDTO>> addPerformance(
            @PathVariable Long id,
            @Valid @RequestBody ProjectPerformanceDTO performanceDTO) {
        try {
            performanceDTO.setEmployeeId(id);
            ProjectPerformanceDTO created = performanceService.createPerformance(performanceDTO);
            return ResponseEntity.ok(ApiResponse.success(created));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Error creating performance: " + e.getMessage()));
        }
    }
    @GetMapping("/{id}/project")
    public ResponseEntity<ApiResponse<ProjectDTO>> getEmployeeProject(@PathVariable Long id) {
        try {
            Optional<Employee> employeeOpt = employeeRepository.findEmployeeWithProjectById(id);
            
            if (employeeOpt.isPresent() && employeeOpt.get().getProject() != null) {
                Employee employee = employeeOpt.get();
                Project project = employee.getProject();
                ProjectDTO projectDTO = projectMapper.toDto(project);
                return ResponseEntity.ok(ApiResponse.success(projectDTO));
            }
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Project not found for employee"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving project: " + e.getMessage()));
        }
    }
    }
   

