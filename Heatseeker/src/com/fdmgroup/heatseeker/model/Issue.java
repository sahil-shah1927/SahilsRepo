package com.fdmgroup.heatseeker.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;

import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;

/**
 * 
 * Issue class holds all fields relevant to an issue. Contains a list of updates.
 * Used in User class and Department class.
 * Indexed in database by Department and by Status to improve lookups.
 * 
 * @author Lawrence Leo
 *
 */
@Entity
@Table(indexes=
	{
		@Index(name="DEPARTMENT_INDEX" ,columnList = "Assigned_To"),
		@Index(name="STATUS_INDEX" ,columnList = "Status")
	})
public class Issue 
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="Issue_Id")
	private int issueId;
	
	@Column(nullable=false, length=30)
	private String title;
	
	@Column(nullable=false, name="User_Description", length=300)
	private String userDescription;
	
	@Column(name="Admin_Comment", length=300)
	private String adminComment;
	
	@ManyToOne
	@JoinColumn(name="Assigned_To")
	private Department department;
	
	@ManyToOne
	@JoinColumn(nullable=false, name="Submitted_By")
	private User submittedBy;
	
	@Enumerated(EnumType.STRING)
	@Column(name="Status")
	private Status status;
	
	@Enumerated(EnumType.STRING)
	@Column(name="Priority")
	private Priority priority;
	
	
	@Column(name="Date_Submitted",nullable=false)
	private Date dateSubmitted;
	
	@Column(name="Date_Resolved")
	private Date dateResolved;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn
	private List<IssueUpdates> updates;
	
	public Issue() {

		updates = new ArrayList<IssueUpdates>();
	}
	
	public Issue(String title, String userDescription, Department dept, User user, Priority priority, Date date) {
		this.title = title;
		this.userDescription = userDescription;
		department = dept;
		submittedBy = user;
		this.priority = priority;
		dateSubmitted = date;
		status = Status.NEW;
	}
	
	public Issue(int departmentID ) throws DepartmentDoesNotExistException
	{
		Department dept = new Department();
		dept.setDeptId(departmentID);
		DepartmentDAO dDao = ApplicationContextProvider.getApplicationContext().getBean("departmentDAO", DepartmentDAO.class);
		this.department = dDao.read(dept);

		updates = new ArrayList<IssueUpdates>();
	}

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public String getAdminComment() {
		return adminComment;
	}

	public void setAdminComment(String adminComment) {
		this.adminComment = adminComment;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department assignedTo) {
		this.department = assignedTo;
	}

	public void setDepartment(int deptId) throws DepartmentDoesNotExistException {
		Department dept = new Department();
		dept.setDeptId(deptId);
		DepartmentDAO dDao = ApplicationContextProvider.getApplicationContext().getBean("departmentDAO", DepartmentDAO.class);
		this.department = dDao.read(dept);
		}
	
	public User getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(User submittedBy) {
		this.submittedBy = submittedBy;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted() {
		dateSubmitted = new java.util.Date();
	}

	public Date getDateResolved() {
		return dateResolved;
	}

	public void setDateResolved(Date dateResolved) {
		this.dateResolved = dateResolved;
	}

	public List<IssueUpdates> getUpdates() {
		return updates;
	}

	public void setUpdates(List<IssueUpdates> updates) {
		this.updates = updates;
	}
	
	public void addUpdate(IssueUpdates update){
		this.getUpdates().add(update);
	}
	
	@PostConstruct
	public void init(){
		setDateSubmitted();
	}
	
	public String getDateWithoutTime(Date date){
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormatter.format(date);
	}
	
	
	
}
