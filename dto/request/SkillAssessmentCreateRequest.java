
package com.report.ro.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.report.ro.dto.SoftSkillDTO;
import com.report.ro.dto.TechnicalSkillDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class SkillAssessmentCreateRequest {
    @NotNull(message = "Employee ID is required")
    private Long employeeId;
    
    @NotNull(message = "Assessment date is required")
    private LocalDate assessmentDate;
    
    private String comments;
    
    @Valid
    private List<TechnicalSkillDTO> technicalSkills;
    
    @Valid
    private List<SoftSkillDTO> softSkills;

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDate getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(LocalDate assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public List<TechnicalSkillDTO> getTechnicalSkills() {
		return technicalSkills;
	}

	public void setTechnicalSkills(List<TechnicalSkillDTO> technicalSkills) {
		this.technicalSkills = technicalSkills;
	}

	public List<SoftSkillDTO> getSoftSkills() {
		return softSkills;
	}

	public void setSoftSkills(List<SoftSkillDTO> softSkills) {
		this.softSkills = softSkills;
	}

}

