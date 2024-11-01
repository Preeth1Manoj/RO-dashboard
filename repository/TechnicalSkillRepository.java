// TechnicalSkillRepository.java
package com.report.ro.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.report.ro.model.TechnicalSkill;

@Repository
public interface TechnicalSkillRepository extends JpaRepository<TechnicalSkill, Long> {
    List<TechnicalSkill> findByAssessmentId(Long assessmentId);
    void deleteByAssessmentId(Long assessmentId);
}