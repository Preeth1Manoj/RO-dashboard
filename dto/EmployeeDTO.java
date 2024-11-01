package com.report.ro.dto;

import java.time.LocalDate;
import java.util.List;



//EmployeeDTO.java
public class EmployeeDTO {
 private Long id;
 private String name;
 private String email;
 private String designation;
 private LocalDate joinDate;
 private Long reportingOfficerId;
 private List<String> allocatedProjects; 
 private Long projectId;
 private String projectName;
  

 public Long getProjectId() {
	return projectId;
}

public void setProjectId(Long projectId) {
	this.projectId = projectId;
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

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getDesignation() {
	return designation;
}

public void setDesignation(String designation) {
	this.designation = designation;
}

public LocalDate getJoinDate() {
	return joinDate;
}

public void setJoinDate(LocalDate joinDate) {
	this.joinDate = joinDate;
}

// Add getters and setters for new fields
 public Long getReportingOfficerId() {
     return reportingOfficerId;
 }

 public void setReportingOfficerId(Long reportingOfficerId) {
     this.reportingOfficerId = reportingOfficerId;
 }

 public List<String> getAllocatedProjects() {
     return allocatedProjects;
 }

 public void setAllocatedProjects(List<String> allocatedProjects) {
     this.allocatedProjects = allocatedProjects;
 }

public String getProjectName() {
	return projectName;
}

public void setProjectName(String projectName) {
	this.projectName = projectName;
}
}