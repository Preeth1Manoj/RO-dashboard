package com.report.ro.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ProjectPerformanceDTO {
    private Long id;
    private Long projectId;
    private String projectName;
    private Long employeeId;
    private String employeeName;
    private LocalDate deliveryDate;
    private String orgActivity;
    private Integer employeeEngagementScore;
    private String developmentPlan;
    private Integer timeManagementScore;
    private Integer productivityScore;
    private String comments;
    private LocalDate evaluationDate;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrgActivity() {
        return orgActivity;
    }

    public void setOrgActivity(String orgActivity) {
        this.orgActivity = orgActivity;
    }

    public Integer getEmployeeEngagementScore() {
        return employeeEngagementScore;
    }

    public void setEmployeeEngagementScore(Integer employeeEngagementScore) {
        this.employeeEngagementScore = employeeEngagementScore;
    }

    public String getDevelopmentPlan() {
        return developmentPlan;
    }

    public void setDevelopmentPlan(String developmentPlan) {
        this.developmentPlan = developmentPlan;
    }

    public Integer getTimeManagementScore() {
        return timeManagementScore;
    }

    public void setTimeManagementScore(Integer timeManagementScore) {
        this.timeManagementScore = timeManagementScore;
    }

    public Integer getProductivityScore() {
        return productivityScore;
    }

    public void setProductivityScore(Integer productivityScore) {
        this.productivityScore = productivityScore;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
}