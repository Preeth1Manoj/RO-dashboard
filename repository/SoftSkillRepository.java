package com.report.ro.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.report.ro.model.SoftSkill;

@Repository
public interface SoftSkillRepository extends JpaRepository<SoftSkill, Long> {
    List<SoftSkill> findByAssessmentId(Long assessmentId);
    void deleteByAssessmentId(Long assessmentId);
}
