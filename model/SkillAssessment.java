package com.report.ro.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "skill_assessments")
@Getter
@Setter
public class SkillAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate assessmentDate;

    private String comments;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechnicalSkill> technicalSkills = new ArrayList<>();

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SoftSkill> softSkills = new ArrayList<>();

    // Helper methods
    public void addTechnicalSkill(TechnicalSkill skill) {
        technicalSkills.add(skill);
        skill.setAssessment(this);
    }

    public void addSoftSkill(SoftSkill skill) {
        softSkills.add(skill);
        skill.setAssessment(this);
    }

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

	public LocalDate getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(LocalDate assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public List<TechnicalSkill> getTechnicalSkills() {
		return technicalSkills;
	}

	public void setTechnicalSkills(List<TechnicalSkill> technicalSkills) {
		this.technicalSkills = technicalSkills;
	}

	public List<SoftSkill> getSoftSkills() {
		return softSkills;
	}

	public void setSoftSkills(List<SoftSkill> softSkills) {
		this.softSkills = softSkills;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}