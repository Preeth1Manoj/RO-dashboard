package com.report.ro.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	private LocalDate startDate;
	private LocalDate endDate;
	private String pm;
	private String accManager;
	 private String projectClass; 

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_project_id")
	private Project parentProject;

	@OneToMany(mappedBy = "parentProject", cascade = CascadeType.ALL)
	private List<Project> subProjects = new ArrayList<>();

	@JsonManagedReference
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectPerformance> performances = new ArrayList<>();

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

	// Add helper method for performance
	public void addPerformance(ProjectPerformance performance) {
		performances.add(performance);
		performance.setProject(this);
	}

	public void setAccManager(String accManager) {
		this.accManager = accManager;
	}

	public Project getParentProject() {
		return parentProject;
	}

	public void setParentProject(Project parentProject) {
		this.parentProject = parentProject;
	}

	public List<Project> getSubProjects() {
		return subProjects;
	}

	public void setSubProjects(List<Project> subProjects) {
		this.subProjects = subProjects;
	}

	public List<ProjectPerformance> getPerformances() {
		return performances;
	}

	public void setPerformances(List<ProjectPerformance> performances) {
		this.performances = performances;
	}

	public String getProjectClass() {
		return projectClass;
	}

	public void setProjectClass(String projectClass) {
		this.projectClass = projectClass;
	}

	
}