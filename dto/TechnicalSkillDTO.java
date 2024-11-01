package com.report.ro.dto;

import com.report.ro.model.enums.ProficiencyLevel;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TechnicalSkillDTO {
    private Long id;
    
    @NotNull(message = "Skill name is required")
    private String name;
    
    @NotNull(message = "Proficiency level is required")
    private ProficiencyLevel proficiencyLevel;
    
    private String comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProficiencyLevel getProficiencyLevel() {
		return proficiencyLevel;
	}

	public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
		this.proficiencyLevel = proficiencyLevel;
	}

	
}
