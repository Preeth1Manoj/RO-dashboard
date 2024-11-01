package com.report.ro.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//EmployeeProject.java
@Entity
@Table(name = "employee_projects")
public class EmployeeProject {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @ManyToOne
 @JoinColumn(name = "employee_id")
 private Employee employee;

 @ManyToOne
 @JoinColumn(name = "project_id")
 private Project project;

 private LocalDate allocationDate;
 private LocalDate deallocationDate;
 private boolean active;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public Employee getEmployee() {
	return employee;
}
public void setEmployee(Employee employee) {
	this.employee = employee;
}
public Project getProject() {
	return project;
}
public void setProject(Project project) {
	this.project = project;
}
public LocalDate getAllocationDate() {
	return allocationDate;
}
public void setAllocationDate(LocalDate allocationDate) {
	this.allocationDate = allocationDate;
}
public LocalDate getDeallocationDate() {
	return deallocationDate;
}
public void setDeallocationDate(LocalDate deallocationDate) {
	this.deallocationDate = deallocationDate;
}
public boolean isActive() {
	return active;
}
public void setActive(boolean active) {
	this.active = active;
}

}