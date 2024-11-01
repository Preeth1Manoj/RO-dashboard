package com.report.ro.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "employees")
@Data
@EqualsAndHashCode(exclude = {"assessments", "performances"})
@ToString(exclude = {"assessments", "performances"})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

	@Column(unique = true, nullable = false)
    private String email;

	 @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporting_officer_id")
    private User reportingOfficer;

    @Column
    private String designation;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SkillAssessment> assessments;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectPerformance> performances;
    
    @OneToMany(mappedBy = "employee")
    private List<EmployeeProject> projectAllocations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;
	
	
    public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<EmployeeProject> getProjectAllocations() {
        return projectAllocations;
    }

    public void setProjectAllocations(List<EmployeeProject> projectAllocations) {
        this.projectAllocations = projectAllocations;
    }

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
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

		public User getReportingOfficer() {
			return reportingOfficer;
		}

		public void setReportingOfficer(User reportingOfficer) {
			this.reportingOfficer = reportingOfficer;
		}

		public LocalDate getJoinDate() {
			return joinDate;
		}

		public void setJoinDate(LocalDate joinDate) {
			this.joinDate = joinDate;
		}

		public List<SkillAssessment> getAssessments() {
			return assessments;
		}

		public void setAssessments(List<SkillAssessment> assessments) {
			this.assessments = assessments;
		}

		public List<ProjectPerformance> getPerformances() {
			return performances;
		}

		public void setPerformances(List<ProjectPerformance> performances) {
			this.performances = performances;
		}
}



