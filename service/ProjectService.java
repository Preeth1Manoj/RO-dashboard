package com.report.ro.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.ro.dto.ProjectDTO;
import com.report.ro.dto.ProjectPerformanceDTO;
import com.report.ro.model.Employee;
import com.report.ro.model.Project;
import com.report.ro.model.ProjectPerformance;
import com.report.ro.repository.ProjectRepository;
import com.report.ro.repository.EmployeeRepository;
import com.report.ro.repository.ProjectPerformanceRepository;

@Service
@Transactional
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private ProjectPerformanceRepository performanceRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    // Project CRUD Operations
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            return null;
        }

        Project project = modelMapper.map(projectDTO, Project.class);
        
        if (projectDTO.getParentProjectId() != null) {
            Optional<Project> parentProjectOpt = projectRepository.findById(projectDTO.getParentProjectId());
            if (parentProjectOpt.isPresent()) {
                project.setParentProject(parentProjectOpt.get());
            }
        }
        
        project = projectRepository.save(project);
        return modelMapper.map(project, ProjectDTO.class);
    }

    public ProjectDTO getProject(Long id) {
        if (id == null) {
            return null;
        }
        
        Optional<Project> projectOpt = projectRepository.findById(id);
        return projectOpt.map(project -> modelMapper.map(project, ProjectDTO.class))
                        .orElse(null);
    }

    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        if (id == null || projectDTO == null) {
            return null;
        }

        Optional<Project> existingProjectOpt = projectRepository.findById(id);
        if (!existingProjectOpt.isPresent()) {
            return null;
        }

        Project existingProject = existingProjectOpt.get();
        
        existingProject.setName(projectDTO.getName());
        existingProject.setStartDate(projectDTO.getStartDate());
        existingProject.setEndDate(projectDTO.getEndDate());
        existingProject.setPm(projectDTO.getPm());
        existingProject.setAccManager(projectDTO.getAccManager());
        existingProject.setProjectClass(projectDTO.getProjectClass());
        
        if (existingProject.getStartDate() != null && 
            existingProject.getEndDate() != null && 
            existingProject.getStartDate().isAfter(existingProject.getEndDate())) {
            return null;
        }

        Project updatedProject = projectRepository.save(existingProject);
        return modelMapper.map(updatedProject, ProjectDTO.class);
    }

    public boolean deleteProject(Long id) {
        if (id == null) {
            return false;
        }

        Optional<Project> projectOpt = projectRepository.findById(id);
        if (!projectOpt.isPresent()) {
            return false;
        }
        
        Project project = projectOpt.get();
        if (!project.getPerformances().isEmpty()) {
            return false;
        }

        projectRepository.delete(project);
        return true;
    }
    
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
            .map(project -> modelMapper.map(project, ProjectDTO.class))
            .collect(Collectors.toList());
    }
    
    public List<ProjectDTO> searchProjects(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return projectRepository.searchProjects(keyword).stream()
            .map(project -> modelMapper.map(project, ProjectDTO.class))
            .collect(Collectors.toList());
    }

    // Performance Management Operations
    public ProjectPerformanceDTO addPerformanceRecord(Long projectId, ProjectPerformanceDTO performanceDTO) {
        if (projectId == null || performanceDTO == null || !isScoreValid(performanceDTO)) {
            return null;
        }

        Optional<Project> projectOpt = projectRepository.findById(projectId);
        Optional<Employee> employeeOpt = employeeRepository.findById(performanceDTO.getEmployeeId());
        
        if (!projectOpt.isPresent() || !employeeOpt.isPresent()) {
            return null;
        }

        ProjectPerformance performance = new ProjectPerformance();
        performance.setProject(projectOpt.get());
        performance.setEmployee(employeeOpt.get());
        
        updatePerformanceDetails(performance, performanceDTO);
        
        try {
            performance = performanceRepository.save(performance);
            return mapToPerformanceDTO(performance, projectOpt.get());
        } catch (Exception e) {
            return null;
        }
    }

    public ProjectPerformanceDTO updatePerformanceRecord(Long projectId, ProjectPerformanceDTO performanceDTO) {
        if (projectId == null || performanceDTO == null || performanceDTO.getId() == null || !isScoreValid(performanceDTO)) {
            return null;
        }

        Optional<Project> projectOpt = projectRepository.findById(projectId);
        Optional<ProjectPerformance> performanceOpt = performanceRepository.findById(performanceDTO.getId());
        
        if (!projectOpt.isPresent() || !performanceOpt.isPresent()) {
            return null;
        }

        ProjectPerformance performance = performanceOpt.get();
        if (!performance.getProject().getId().equals(projectId)) {
            return null;
        }

        updatePerformanceDetails(performance, performanceDTO);
        performance = performanceRepository.save(performance);
        
        return mapToPerformanceDTO(performance, projectOpt.get());
    }

    public boolean deletePerformanceRecord(Long projectId, Long performanceId) {
        if (projectId == null || performanceId == null) {
            return false;
        }

        Optional<ProjectPerformance> performanceOpt = performanceRepository.findById(performanceId);
        if (!performanceOpt.isPresent() || !performanceOpt.get().getProject().getId().equals(projectId)) {
            return false;
        }

        performanceRepository.delete(performanceOpt.get());
        return true;
    }

//    public List<ProjectPerformanceDTO> getProjectPerformances(Long projectId) {
//        if (projectId == null) {
//            return Collections.emptyList();
//        }
//        return performanceRepository.findAllByProjectId(projectId).stream()
//            .map(performance -> mapToPerformanceDTO(performance, performance.getProject()))
//            .collect(Collectors.toList());
//    }

    public List<ProjectPerformanceDTO> getEmployeePerformances(Long employeeId) {
        if (employeeId == null) {
            return Collections.emptyList();
        }
        return performanceRepository.findAllByEmployeeId(employeeId).stream()
            .map(performance -> mapToPerformanceDTO(performance, performance.getProject()))
            .collect(Collectors.toList());
    }

    public ProjectPerformanceDTO getLatestPerformance(Long projectId, Long employeeId) {
        if (projectId == null || employeeId == null) {
            return null;
        }
        
        List<ProjectPerformance> performances = performanceRepository.findByProjectIdAndEmployeeId(projectId, employeeId);
        return performances.stream()
            .max((p1, p2) -> p1.getEvaluationDate().compareTo(p2.getEvaluationDate()))
            .map(performance -> mapToPerformanceDTO(performance, performance.getProject()))
            .orElse(null);
    }

    
    public List<ProjectPerformanceDTO> getAllProjectPerformances() {
        try {
            List<ProjectPerformance> performances = performanceRepository.findAllWithProjectAndEmployee();
            
            return performances.stream()
                .map(performance -> {
                    ProjectPerformanceDTO dto = new ProjectPerformanceDTO();
                    dto.setId(performance.getId());
                    
                    // Set project details
                    if (performance.getProject() != null) {
                        dto.setProjectId(performance.getProject().getId());
                        dto.setProjectName(performance.getProject().getName());
                    }
                    
                    // Set employee details
                    if (performance.getEmployee() != null) {
                        dto.setEmployeeId(performance.getEmployee().getId());
                        dto.setEmployeeName(performance.getEmployee().getName());
                    }
                    
                    // Set performance details
                    dto.setEmployeeEngagementScore(performance.getEmployeeEngagementScore());
                    dto.setTimeManagementScore(performance.getTimeManagementScore());
                    dto.setProductivityScore(performance.getProductivityScore());
                    dto.setComments(performance.getComments());
                    dto.setDeliveryDate(performance.getDeliveryDate());
                    dto.setOrgActivity(performance.getOrgActivity());
                    dto.setDevelopmentPlan(performance.getDevelopmentPlan());
                    dto.setEvaluationDate(performance.getEvaluationDate());
                    
                    return dto;
                })
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ProjectPerformanceDTO> getProjectPerformances(Long projectId) {
        if (projectId == null) {
            return new ArrayList<>();
        }
        
        try {
            List<ProjectPerformance> performances = performanceRepository.findAllByProjectId(projectId);
            return performances.stream()
                .map(this::convertToPerformanceDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private ProjectPerformanceDTO convertToPerformanceDTO(ProjectPerformance performance) {
        ProjectPerformanceDTO dto = new ProjectPerformanceDTO();
        dto.setId(performance.getId());
        
        // Set project details
        if (performance.getProject() != null) {
            dto.setProjectId(performance.getProject().getId());
            dto.setProjectName(performance.getProject().getName());
        }
        
        // Set employee details
        if (performance.getEmployee() != null) {
            dto.setEmployeeId(performance.getEmployee().getId());
            dto.setEmployeeName(performance.getEmployee().getName());
        }
        
        // Set performance details
        dto.setEmployeeEngagementScore(performance.getEmployeeEngagementScore());
        dto.setTimeManagementScore(performance.getTimeManagementScore());
        dto.setProductivityScore(performance.getProductivityScore());
        dto.setComments(performance.getComments());
        dto.setDeliveryDate(performance.getDeliveryDate());
        dto.setOrgActivity(performance.getOrgActivity());
        dto.setDevelopmentPlan(performance.getDevelopmentPlan());
        dto.setEvaluationDate(performance.getEvaluationDate());
        
        return dto;
    }
    // Helper Methods
    private boolean isScoreValid(ProjectPerformanceDTO performanceDTO) {
        return isScoreInRange(performanceDTO.getEmployeeEngagementScore()) &&
               isScoreInRange(performanceDTO.getTimeManagementScore()) &&
               isScoreInRange(performanceDTO.getProductivityScore());
    }

    private boolean isScoreInRange(Integer score) {
        return score == null || (score >= 0 && score <= 100);
    }

    private void updatePerformanceDetails(ProjectPerformance performance, ProjectPerformanceDTO dto) {
        performance.setEmployeeEngagementScore(dto.getEmployeeEngagementScore());
        performance.setTimeManagementScore(dto.getTimeManagementScore());
        performance.setProductivityScore(dto.getProductivityScore());
        performance.setComments(dto.getComments());
        performance.setOrgActivity(dto.getOrgActivity());
        performance.setDevelopmentPlan(dto.getDevelopmentPlan());
        performance.setDeliveryDate(dto.getDeliveryDate());
        performance.setEvaluationDate(LocalDate.now());
    }

    private ProjectPerformanceDTO mapToPerformanceDTO(ProjectPerformance performance, Project project) {
        ProjectPerformanceDTO dto = new ProjectPerformanceDTO();
        dto.setId(performance.getId());
        dto.setProjectId(project.getId());
        dto.setProjectName(project.getName());
        
        if (performance.getEmployee() != null) {
            dto.setEmployeeId(performance.getEmployee().getId());
            dto.setEmployeeName(performance.getEmployee().getName());
        }
        
        dto.setEmployeeEngagementScore(performance.getEmployeeEngagementScore());
        dto.setTimeManagementScore(performance.getTimeManagementScore());
        dto.setProductivityScore(performance.getProductivityScore());
        dto.setComments(performance.getComments());
        dto.setOrgActivity(performance.getOrgActivity());
        dto.setDevelopmentPlan(performance.getDevelopmentPlan());
        dto.setDeliveryDate(performance.getDeliveryDate());
        dto.setEvaluationDate(performance.getEvaluationDate());
        
        return dto;
    }
}