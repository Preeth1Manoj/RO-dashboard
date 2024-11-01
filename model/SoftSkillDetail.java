package com.report.ro.model;

import jakarta.persistence.*;
import lombok.Data;
import com.report.ro.model.enums.ProficiencyLevel;
import com.report.ro.model.enums.ProgressStatus;

@Entity
@Table(name = "soft_skill_details")
@Data
public class SoftSkillDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessment_id")
	private SkillAssessment assessment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skill_id")
	private SoftSkill skill;

	@Enumerated(EnumType.STRING)
	private ProgressStatus status;

	@Enumerated(EnumType.STRING)
	private ProficiencyLevel proficiencyLevel;

	@Column(columnDefinition = "TEXT")
	private String comments;
}
