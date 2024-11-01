package com.report.ro.dto.request;

import java.util.List;

import com.report.ro.dto.SoftSkillDTO;
import com.report.ro.dto.TechnicalSkillDTO;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class SkillAssessmentUpdateRequest {
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
