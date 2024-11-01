package com.report.ro.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectDTO {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String pm;
    private String accManager;
    private String projectClass;  
    private Long parentProjectId;
    private List<ProjectPerformanceDTO> performances = new ArrayList<>();

  
    
	public String getProjectClass() {
		return projectClass;
	}

	public void setProjectClass(String projectClass) {
		this.projectClass = projectClass;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getAccManager() {
		return accManager;
	}

	public void setAccManager(String accManager) {
		this.accManager = accManager;
	}

	public Long getParentProjectId() {
		return parentProjectId;
	}

	public void setParentProjectId(Long parentProjectId) {
		this.parentProjectId = parentProjectId;
	}

	public List<ProjectPerformanceDTO> getPerformances() {
		return performances;
	}

	public void setPerformances(List<ProjectPerformanceDTO> performances) {
		this.performances = performances;
	}

    // Other existing getters and setters
}