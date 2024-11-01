
package com.report.ro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.report.ro.dto.ProjectPerformanceDTO;
import com.report.ro.dto.SkillAssessmentDTO;
import com.report.ro.dto.request.SkillAssessmentCreateRequest;
import com.report.ro.dto.request.SkillAssessmentUpdateRequest;
import com.report.ro.dto.response.ApiResponse;
import com.report.ro.service.ProjectService;
import com.report.ro.service.SkillAssessmentService;

import jakarta.validation.Valid;

@RestController
public class TroubleShootController {
	
	 @Autowired
	    private SkillAssessmentService skillAssessmentService;

    @GetMapping("/troubleshoot")
    public String troubleshoot() {
        System.out.println("Troubleshoot endpoint hit");
        return "Troubleshoot working!";
    }
    @Autowired
    private ProjectService projectService;
 // Change this endpoint
    @GetMapping("/performance/list")  // Changed from /all-performances
    public ResponseEntity<ApiResponse<List<ProjectPerformanceDTO>>> getAllPerformances() {
        List<ProjectPerformanceDTO> performances = projectService.getAllProjectPerformances();
        return ResponseEntity.ok(ApiResponse.success(performances));
    }
    
    @DeleteMapping("/skill-assessments/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAssessment(@PathVariable Long id) {
        boolean deleted = skillAssessmentService.deleteAssessment(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/skill-assessments/{employeeId}")
    public ResponseEntity<ApiResponse<List<SkillAssessmentDTO>>> getEmployeeAssessments(
            @PathVariable Long employeeId) {
        try {
            List<SkillAssessmentDTO> assessments = skillAssessmentService.getEmployeeAssessmentHistory(employeeId);
            return ResponseEntity.ok(ApiResponse.success(assessments));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
   
    
    @GetMapping("/skill-assessments")
    public ResponseEntity<ApiResponse<List<SkillAssessmentDTO>>> getAllAssessments() {
        List<SkillAssessmentDTO> assessments = skillAssessmentService.getAllAssessments();
        return ResponseEntity.ok(ApiResponse.success(assessments));
    }
    
    @PostMapping("/skill-assessments")
    public ResponseEntity<ApiResponse<SkillAssessmentDTO>> createAssessment(
            @Valid @RequestBody SkillAssessmentCreateRequest request) {
        SkillAssessmentDTO created = skillAssessmentService.createAssessment(request);
        if (created == null) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Failed to create assessment"));
        }
        return ResponseEntity.ok(ApiResponse.success(created));
    }

    @PutMapping("/skill-assessments/{id}")
    public ResponseEntity<ApiResponse<SkillAssessmentDTO>> updateAssessment(
            @PathVariable Long id,
            @Valid @RequestBody SkillAssessmentUpdateRequest request) {
        SkillAssessmentDTO updated = skillAssessmentService.updateAssessment(id, request);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

}