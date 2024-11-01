package com.report.ro.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.report.ro.dto.ProjectPerformanceDTO;
import com.report.ro.model.Employee;
import com.report.ro.model.Project;
import com.report.ro.model.ProjectPerformance;
import com.report.ro.repository.EmployeeRepository;
import com.report.ro.repository.ProjectPerformanceRepository;
import com.report.ro.repository.ProjectRepository;

import jakarta.transaction.Transactional;

@Service
	@Transactional
	public class ProjectPerformanceService {

	    private final ProjectPerformanceRepository performanceRepository;
	    private final EmployeeRepository employeeRepository;
	    private final ProjectRepository projectRepository;
	    private final ModelMapper modelMapper;

	    @Autowired
	    public ProjectPerformanceService(
	            ProjectPerformanceRepository performanceRepository,
	            EmployeeRepository employeeRepository,
	            ProjectRepository projectRepository,
	            ModelMapper modelMapper) {
	        this.performanceRepository = performanceRepository;
	        this.employeeRepository = employeeRepository;
	        this.projectRepository = projectRepository;
	        this.modelMapper = modelMapper;
	    }

	    public List<ProjectPerformanceDTO> getPerformancesByEmployeeId(Long employeeId) {
	        List<ProjectPerformance> performances = performanceRepository.findByEmployeeId(employeeId);
	        return performances.stream()
	            .map(perf -> {
	                ProjectPerformanceDTO dto = new ProjectPerformanceDTO();
	                dto.setId(perf.getId());
	                dto.setProjectId(perf.getProject().getId());
	                dto.setProjectName(perf.getProject().getName());
	                dto.setEmployeeId(perf.getEmployee().getId());
	                dto.setEmployeeName(perf.getEmployee().getName());
	                dto.setEmployeeEngagementScore(perf.getEmployeeEngagementScore());
	                dto.setTimeManagementScore(perf.getTimeManagementScore());
	                dto.setProductivityScore(perf.getProductivityScore());
	                dto.setComments(perf.getComments());
	                dto.setEvaluationDate(perf.getEvaluationDate());
	                return dto;
	            })
	            .collect(Collectors.toList());
	    }

	    public ProjectPerformanceDTO createPerformance(ProjectPerformanceDTO dto) {
	        // Validate employee exists
	        Employee employee = employeeRepository.findById(dto.getEmployeeId())
	                .orElseThrow(() -> new RuntimeException("Employee not found"));

	        // Validate project exists
	        Project project = projectRepository.findById(dto.getProjectId())
	                .orElseThrow(() -> new RuntimeException("Project not found"));

	        // Create performance
	        ProjectPerformance performance = modelMapper.map(dto, ProjectPerformance.class);
	        performance.setEmployee(employee);
	        performance.setProject(project);
	        performance.setEvaluationDate(LocalDate.now());

	        // Save and return
	        ProjectPerformance saved = performanceRepository.save(performance);
	        return modelMapper.map(saved, ProjectPerformanceDTO.class);
	    }
	}