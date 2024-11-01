package com.report.ro.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.report.ro.dto.ProjectPerformanceDTO;
import com.report.ro.model.ProjectPerformance;

@Configuration
public class AppConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
        modelMapper.createTypeMap(ProjectPerformance.class, ProjectPerformanceDTO.class)
        .addMappings(mapper -> {
            mapper.map(src -> src.getProject().getId(), ProjectPerformanceDTO::setProjectId);
            mapper.map(src -> src.getProject().getName(), ProjectPerformanceDTO::setProjectName);
            mapper.map(src -> src.getEmployee().getId(), ProjectPerformanceDTO::setEmployeeId);
            mapper.map(src -> src.getEmployee().getName(), ProjectPerformanceDTO::setEmployeeName);
        });
        return modelMapper;
    }
}