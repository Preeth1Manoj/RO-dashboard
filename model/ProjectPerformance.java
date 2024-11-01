
	package com.report.ro.model;

	import java.time.LocalDate;
	import jakarta.persistence.*;
	import lombok.Getter;
	import lombok.Setter;

	@Entity
	@Table(name = "project_performance")
	@Getter
	@Setter
	public class ProjectPerformance {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "project_id")
	    private Project project;

		 public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		@Column(name = "project_name")
		 private String projectName;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "employee_id")
	    private Employee employee;
	   
	    
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

	    public Project getProject() {
	        return project;
	    }

	    public void setProject(Project project) {
	        this.project = project;
	    }

	    public Employee getEmployee() {
	        return employee;
	    }

	    public void setEmployee(Employee employee) {
	        this.employee = employee;
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