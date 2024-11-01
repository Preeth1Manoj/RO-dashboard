package com.report.ro.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.report.ro.dto.EmployeeDTO;
import com.report.ro.model.Employee;


import org.modelmapper.ModelMapper;

@Component
public class EmployeeMapper {
    @Autowired
    private ModelMapper modelMapper;

    public EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setDesignation(employee.getDesignation());
        dto.setJoinDate(employee.getJoinDate());
        
    
        
        if (employee.getProject() != null) {
            dto.setProjectId(employee.getProject().getId());
            dto.setProjectName(employee.getProject().getName());
        }
        
        return dto;
    }

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
}