package com.report.ro.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectCreateDTO {
	@NotBlank(message = "Project name is required")
	private String name;

	@NotNull(message = "Start date is required")
	private LocalDate startDate;

	private LocalDate endDate;

	@NotBlank(message = "Project Manager is required")
	private String pm;

	@NotBlank(message = "Account Manager is required")
	private String accManager;

	private String projectclass;
	private Long parentProjectId;

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getProjectclass() {
		return projectclass;
	}

	public void setProjectclass(String projectclass) {
		this.projectclass = projectclass;
	}

	public Long getParentProjectId() {
		return parentProjectId;
	}

	public void setParentProjectId(Long parentProjectId) {
		this.parentProjectId = parentProjectId;
	}
}
