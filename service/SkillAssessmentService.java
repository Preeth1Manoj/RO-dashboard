// SkillAssessmentService.java
package com.report.ro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.report.ro.dto.SkillAssessmentDTO;
import com.report.ro.dto.request.SkillAssessmentCreateRequest;
import com.report.ro.dto.request.SkillAssessmentUpdateRequest;
import com.report.ro.mapper.SkillAssessmentMapper;
import com.report.ro.model.Employee;
import com.report.ro.model.SkillAssessment;
import com.report.ro.model.SoftSkill;
import com.report.ro.model.TechnicalSkill;
import com.report.ro.repository.EmployeeRepository;
import com.report.ro.repository.SkillAssessmentRepository;
import com.report.ro.util.ValidationUtil;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SkillAssessmentService {
    private final SkillAssessmentRepository assessmentRepository;
    private final EmployeeRepository employeeRepository;
    private final SkillAssessmentMapper skillAssessmentMapper;
    private final ValidationUtil validationUtil;
    
    @Autowired
    public SkillAssessmentService(
        SkillAssessmentRepository assessmentRepository,
        EmployeeRepository employeeRepository,
        SkillAssessmentMapper skillAssessmentMapper,
        ValidationUtil validationUtil
    ) {
        this.assessmentRepository = assessmentRepository;
        this.employeeRepository = employeeRepository;
        this.skillAssessmentMapper = skillAssessmentMapper;
        this.validationUtil = validationUtil;
    }
    

    public SkillAssessmentDTO createAssessment(SkillAssessmentCreateRequest request) {
        Optional<Employee> employeeOpt = employeeRepository.findById(request.getEmployeeId());
        if (!employeeOpt.isPresent()) {
            return null;
        }

        SkillAssessment assessment = new SkillAssessment();
        assessment.setEmployee(employeeOpt.get());
        assessment.setAssessmentDate(request.getAssessmentDate());
        assessment.setComments(request.getComments());

        // Add technical skills if present
        if (request.getTechnicalSkills() != null) {
            request.getTechnicalSkills().forEach(skillDto -> {
                if (validationUtil.validateSkill(skillDto)) {
                    TechnicalSkill skill = skillAssessmentMapper.toTechnicalSkillEntity(skillDto);
                    assessment.addTechnicalSkill(skill);
                }
            });
        }

        // Add soft skills if present
        if (request.getSoftSkills() != null) {
            request.getSoftSkills().forEach(skillDto -> {
                if (validationUtil.validateSkill(skillDto)) {
                    SoftSkill skill = skillAssessmentMapper.toSoftSkillEntity(skillDto);
                    assessment.addSoftSkill(skill);
                }
            });
        }

        try {
            SkillAssessment savedAssessment = assessmentRepository.save(assessment);
            return skillAssessmentMapper.toDTO(savedAssessment);
        } catch (Exception e) {
            return null;
        }
    }

    public SkillAssessmentDTO getAssessment(Long id) {
        Optional<SkillAssessment> assessmentOpt = assessmentRepository.findById(id);
        return assessmentOpt.map(skillAssessmentMapper::toDTO).orElse(null);
    }

    public SkillAssessmentDTO updateAssessment(Long id, SkillAssessmentUpdateRequest request) {
        Optional<SkillAssessment> assessmentOpt = assessmentRepository.findById(id);
        if (!assessmentOpt.isPresent()) {
            return null;
        }

        SkillAssessment assessment = assessmentOpt.get();
        assessment.setComments(request.getComments());
     // For technical skills
        if (request.getTechnicalSkills() != null) {
            request.getTechnicalSkills().forEach(skillDto -> {
                if (validationUtil.validateSkill(skillDto)) {  // Check return value
                    TechnicalSkill skill = skillAssessmentMapper.toTechnicalSkillEntity(skillDto);
                    assessment.addTechnicalSkill(skill);
                }
            });
        }

        // For soft skills
        if (request.getSoftSkills() != null) {
            request.getSoftSkills().forEach(skillDto -> {
                if (validationUtil.validateSkill(skillDto)) {  // Check return value
                    SoftSkill skill = skillAssessmentMapper.toSoftSkillEntity(skillDto);
                    assessment.addSoftSkill(skill);
                }
            });
        }

        try {
            SkillAssessment updatedAssessment = assessmentRepository.save(assessment);
            return skillAssessmentMapper.toDTO(updatedAssessment);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteAssessment(Long id) {
        if (assessmentRepository.existsById(id)) {
            try {
                assessmentRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public List<SkillAssessmentDTO> getEmployeeAssessmentHistory(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            return new ArrayList<>();
        }
        
        List<SkillAssessment> assessments = assessmentRepository
            .findByEmployeeIdOrderByAssessmentDateDesc(employeeId);
        
        return assessments.stream()
            .map(skillAssessmentMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    public List<SkillAssessmentDTO> getAllAssessments() {
        return assessmentRepository.findAll().stream()
            .map(skillAssessmentMapper::toDTO)
            .collect(Collectors.toList());
    }
}