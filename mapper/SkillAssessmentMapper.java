package com.report.ro.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.report.ro.dto.SkillAssessmentDTO;
import com.report.ro.dto.SoftSkillDTO;
import com.report.ro.dto.TechnicalSkillDTO;
import com.report.ro.model.SkillAssessment;
import com.report.ro.model.SoftSkill;
import com.report.ro.model.TechnicalSkill;

import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

@Component
public class SkillAssessmentMapper {
    
    private final ModelMapper modelMapper;

    @Autowired
    public SkillAssessmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SkillAssessmentDTO toDTO(SkillAssessment assessment) {
        if (assessment == null) return null;
        
        SkillAssessmentDTO dto = modelMapper.map(assessment, SkillAssessmentDTO.class);
        dto.setId(assessment.getEmployee().getId());
        dto.setEmployeeName(assessment.getEmployee().getName());
        
        // Map technical skills
        dto.setTechnicalSkills(assessment.getTechnicalSkills().stream()
            .map(this::toTechnicalSkillDTO)
            .collect(Collectors.toList()));
            
        // Map soft skills
        dto.setSoftSkills(assessment.getSoftSkills().stream()
            .map(this::toSoftSkillDTO)
            .collect(Collectors.toList()));
            
        return dto;
    }

    public TechnicalSkillDTO toTechnicalSkillDTO(TechnicalSkill skill) {
        return modelMapper.map(skill, TechnicalSkillDTO.class);
    }

    public SoftSkillDTO toSoftSkillDTO(SoftSkill skill) {
        return modelMapper.map(skill, SoftSkillDTO.class);
    }

    public TechnicalSkill toTechnicalSkillEntity(TechnicalSkillDTO dto) {
        return modelMapper.map(dto, TechnicalSkill.class);
    }

    public SoftSkill toSoftSkillEntity(SoftSkillDTO dto) {
        return modelMapper.map(dto, SoftSkill.class);
    }
}