package com.report.ro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.report.ro.repository.EmployeeRepository;
import com.report.ro.repository.ProjectRepository;

import jakarta.transaction.Transactional;

import com.report.ro.mapper.EmployeeMapper;
import com.report.ro.model.Employee;
import com.report.ro.model.Project;

import com.report.ro.dto.EmployeeDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeService(
            EmployeeRepository employeeRepository,
            ProjectRepository projectRepository,
            EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.employeeMapper = employeeMapper;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
            .stream()
            .map(employeeMapper::toDTO)
            .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findEmployeeWithProjectById(id)
            .map(employeeMapper::toDTO)
            .orElse(null);
    }

    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        // Validate email uniqueness
        if (employeeRepository.findByEmail(employeeDTO.getEmail()).isPresent()) {
            return null;
        }

        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDesignation(employeeDTO.getDesignation());
        employee.setJoinDate(employeeDTO.getJoinDate());

        // Handle project assignment
        if (employeeDTO.getProjectId() != null) {
            Optional<Project> projectOpt = projectRepository.findById(employeeDTO.getProjectId());
            projectOpt.ifPresent(employee::setProject);
        }

        employee = employeeRepository.save(employee);
        return employeeMapper.toDTO(employee);
    }

    @Transactional
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        Optional<Employee> existingEmployeeOpt = employeeRepository.findById(employeeDTO.getId());
        
        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();
            
            // Update basic fields
            existingEmployee.setName(employeeDTO.getName());
            existingEmployee.setDesignation(employeeDTO.getDesignation());
            existingEmployee.setJoinDate(employeeDTO.getJoinDate());
            
            // Handle email update
            if (!existingEmployee.getEmail().equals(employeeDTO.getEmail())) {
                Optional<Employee> emailCheck = employeeRepository.findByEmail(employeeDTO.getEmail());
                if (emailCheck.isPresent() && !emailCheck.get().getId().equals(employeeDTO.getId())) {
                    return null;
                }
                existingEmployee.setEmail(employeeDTO.getEmail());
            }

            // Handle project update
            if (employeeDTO.getProjectId() != null) {
                Optional<Project> projectOpt = projectRepository.findById(employeeDTO.getProjectId());
                projectOpt.ifPresent(existingEmployee::setProject);
            } else {
                existingEmployee.setProject(null);
            }

            existingEmployee = employeeRepository.save(existingEmployee);
            return employeeMapper.toDTO(existingEmployee);
        }
        return null;
    }

    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.findById(id).ifPresent(employee -> {
            employee.setProject(null);
            employeeRepository.delete(employee);
        });
    }

    @Transactional
    public EmployeeDTO assignProject(Long employeeId, Long projectId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        
        if (employeeOpt.isPresent() && projectOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            Project project = projectOpt.get();
            
            employee.setProject(project);
            employee = employeeRepository.save(employee);
            return employeeMapper.toDTO(employee);
        }
        return null;
    }

    @Transactional
    public EmployeeDTO removeProject(Long employeeId) {
        return employeeRepository.findById(employeeId)
            .map(employee -> {
                employee.setProject(null);
                Employee updated = employeeRepository.save(employee);
                return employeeMapper.toDTO(updated);
            })
            .orElse(null);
    }

    public List<EmployeeDTO> getEmployeesByProject(Long projectId) {
        return employeeRepository.findByProjectId(projectId)
            .stream()
            .map(employeeMapper::toDTO)
            .collect(Collectors.toList());
    }
}