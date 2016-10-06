package com.fdmgroup.heatseeker.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Department is used inside Issue class. Also has a List of issues that it
 * has been assigned which are managed by the respective department admin.s
 * 
 * @author Lawrence Leo
 *
 */
@Entity
public class Department {
	
	@Id
	@Column(name="Dept_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int deptId;
	
	@Column(name="name", nullable = false, length=30)
	private String deptName;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="department")
	private List<Issue> issues;

	public Department() 
	{	
		
	}

	public int getDeptId() 
	{
		return deptId;
	}

	public void setDeptId(int deptId) 
	{
		this.deptId = deptId;
	}

	public String getDeptName() 
	{
		return deptName;
	}

	public void setDeptName(String deptName) 
	{
		this.deptName = deptName;
	}

	public List<Issue> getIssues() 
	{
		return issues;
	}

	public void setIssues(List<Issue> issues) 
	{
		this.issues = issues;
	}

}
