package com.report.ro.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import com.report.ro.model.enums.ProficiencyLevel;

@Entity
@Table(name = "technical_skills")
@Getter
@Setter
public class TechnicalSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

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

	public ProficiencyLevel getProficiencyLevel() {
		return proficiencyLevel;
	}

	public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
		this.proficiencyLevel = proficiencyLevel;
	}

	public SkillAssessment getAssessment() {
		return assessment;
	}

	public void setAssessment(SkillAssessment assessment) {
		this.assessment = assessment;
	}

	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProficiencyLevel proficiencyLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id")
    private SkillAssessment assessment;

    private String comments;

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}