package com.report.ro.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.report.ro.dto.ProjectDTO;
import com.report.ro.dto.ProjectPerformanceDTO;
import com.report.ro.dto.response.ApiResponse;
import com.report.ro.service.ProjectService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    // Project CRUD Operations
    @PostMapping
    public ResponseEntity<ApiResponse<ProjectDTO>> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        ProjectDTO createdProject = projectService.createProject(projectDTO);
        if (createdProject != null) {
            return ResponseEntity.ok(ApiResponse.success(createdProject));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error("Unable to create project"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectDTO>> getProject(@PathVariable Long id) {
        ProjectDTO project = projectService.getProject(id);
        if (project != null) {
            return ResponseEntity.ok(ApiResponse.success(project));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectDTO>>> getAllProjects() {
        List<ProjectDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(ApiResponse.success(projects));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectDTO>> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectDTO projectDTO) {
        ProjectDTO updatedProject = projectService.updateProject(id, projectDTO);
        if (updatedProject != null) {
            return ResponseEntity.ok(ApiResponse.success(updatedProject));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error("Unable to update project"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id) {
        boolean deleted = projectService.deleteProject(id);
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success(null));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error("Unable to delete project"));
    }


    // Change this endpoint
    @GetMapping("/performance/list")  // Changed from /all-performances
    public ResponseEntity<ApiResponse<List<ProjectPerformanceDTO>>> getAllPerformances() {
        List<ProjectPerformanceDTO> performances = projectService.getAllProjectPerformances();
        return ResponseEntity.ok(ApiResponse.success(performances));
    }

    // Other performance endpoints
    @GetMapping("/{projectId}/performance/list")
    public ResponseEntity<ApiResponse<List<ProjectPerformanceDTO>>> getProjectPerformances(
            @PathVariable Long projectId) {
        List<ProjectPerformanceDTO> performances = projectService.getProjectPerformances(projectId);
        return ResponseEntity.ok(ApiResponse.success(performances));
    }

    @GetMapping("/performance/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<ProjectPerformanceDTO>>> getEmployeePerformances(
            @PathVariable Long employeeId) {
        List<ProjectPerformanceDTO> performances = projectService.getEmployeePerformances(employeeId);
        return ResponseEntity.ok(ApiResponse.success(performances));
    }

    @PostMapping("/{projectId}/performance")
    public ResponseEntity<ApiResponse<ProjectPerformanceDTO>> addPerformance(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectPerformanceDTO performanceDTO) {
        ProjectPerformanceDTO added = projectService.addPerformanceRecord(projectId, performanceDTO);
        if (added != null) {
            return ResponseEntity.ok(ApiResponse.success(added));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error("Unable to add performance record"));
    }

    @PutMapping("/{projectId}/performance/{performanceId}")
    public ResponseEntity<ApiResponse<ProjectPerformanceDTO>> updatePerformance(
            @PathVariable Long projectId,
            @PathVariable Long performanceId,
            @Valid @RequestBody ProjectPerformanceDTO performanceDTO) {
        performanceDTO.setId(performanceId);
        ProjectPerformanceDTO updated = projectService.updatePerformanceRecord(projectId, performanceDTO);
        if (updated != null) {
            return ResponseEntity.ok(ApiResponse.success(updated));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error("Unable to update performance record"));
    }

    @DeleteMapping("/{projectId}/performance/{performanceId}")
    public ResponseEntity<ApiResponse<Void>> deletePerformance(
            @PathVariable Long projectId,
            @PathVariable Long performanceId) {
        boolean deleted = projectService.deletePerformanceRecord(projectId, performanceId);
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success(null));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error("Unable to delete performance record"));
    }
    // Search Operations
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProjectDTO>>> searchProjects(
            @RequestParam String keyword) {
        List<ProjectDTO> projects = projectService.searchProjects(keyword);
        return ResponseEntity.ok(ApiResponse.success(projects));
    }
}