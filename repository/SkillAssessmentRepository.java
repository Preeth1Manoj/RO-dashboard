// Repositories

package com.report.ro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.report.ro.model.SkillAssessment;

import java.util.List;

@Repository
public interface SkillAssessmentRepository extends JpaRepository<SkillAssessment, Long> {
    List<SkillAssessment> findByEmployeeId(Long employeeId);
    List<SkillAssessment> findByEmployeeIdOrderByAssessmentDateDesc(Long employeeId);
}







