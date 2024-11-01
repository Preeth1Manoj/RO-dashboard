// SkillAssessmentController.java
package com.report.ro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.report.ro.dto.SkillAssessmentDTO;
import com.report.ro.dto.request.SkillAssessmentCreateRequest;
import com.report.ro.dto.request.SkillAssessmentUpdateRequest;
import com.report.ro.dto.response.ApiResponse;
import com.report.ro.service.SkillAssessmentService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/skill-assessments")
@CrossOrigin(origins = "*")
public class SkillAssessmentController {

    @Autowired
    private SkillAssessmentService skillAssessmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<SkillAssessmentDTO>> createAssessment(
            @Valid @RequestBody SkillAssessmentCreateRequest request) {
        SkillAssessmentDTO created = skillAssessmentService.createAssessment(request);
        if (created == null) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Failed to create assessment"));
        }
        return ResponseEntity.ok(ApiResponse.success(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillAssessmentDTO>> getAssessment(@PathVariable Long id) {
        SkillAssessmentDTO assessment = skillAssessmentService.getAssessment(id);
        if (assessment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(assessment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillAssessmentDTO>> updateAssessment(
            @PathVariable Long id,
            @Valid @RequestBody SkillAssessmentUpdateRequest request) {
        SkillAssessmentDTO updated = skillAssessmentService.updateAssessment(id, request);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAssessment(@PathVariable Long id) {
        boolean deleted = skillAssessmentService.deleteAssessment(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<SkillAssessmentDTO>>> getEmployeeAssessments(
            @PathVariable Long employeeId) {
        List<SkillAssessmentDTO> assessments = skillAssessmentService.getEmployeeAssessmentHistory(employeeId);
        return ResponseEntity.ok(ApiResponse.success(assessments));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("Test endpoint hit"); // Add this line
        return ResponseEntity.ok("Test endpoint working");
    }
}

