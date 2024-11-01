// DTO Classes

package com.report.ro.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SkillAssessmentDTO {
    private Long id;
    
    @NotNull(message = "Assessment date is required")
    private LocalDate assessmentDate;
    
    @NotNull(message = "Employee ID is required")
    private Long employeeId;
    
    private String employeeName;
    private String comments;
    private List<TechnicalSkillDTO> technicalSkills = new ArrayList<>();
    private List<SoftSkillDTO> softSkills = new ArrayList<>();
	public List<TechnicalSkillDTO> getTechnicalSkills() {
		return technicalSkills;
	}
	public void setTechnicalSkills(List<TechnicalSkillDTO> technicalSkills) {
		this.technicalSkills = technicalSkills;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<SoftSkillDTO> getSoftSkills() {
		return softSkills;
	}
	public void setSoftSkills(List<SoftSkillDTO> softSkills) {
		this.softSkills = softSkills;
	}
}






