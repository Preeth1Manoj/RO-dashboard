package com.report.ro.mapper;

import org.springframework.stereotype.Component;
import com.report.ro.dto.ProjectDTO;
import com.report.ro.model.Project;

@Component
public class ProjectMapper {

	public ProjectDTO toDto(Project project) {
		if (project == null) {
			return null;
		}

		ProjectDTO dto = new ProjectDTO();
		dto.setId(project.getId());
		dto.setName(project.getName());
		dto.setStartDate(project.getStartDate());
		dto.setEndDate(project.getEndDate());
		dto.setPm(project.getPm());
		dto.setAccManager(project.getAccManager());

		if (project.getParentProject() != null) {
			dto.setParentProjectId(project.getParentProject().getId());
		}

		return dto;
	}

	public Project toEntity(ProjectDTO dto) {
		if (dto == null) {
			return null;
		}

		Project project = new Project();
		project.setId(dto.getId());
		project.setName(dto.getName());
		project.setStartDate(dto.getStartDate());
		project.setEndDate(dto.getEndDate());
		project.setPm(dto.getPm());
		project.setAccManager(dto.getAccManager());

		return project;
	}
}