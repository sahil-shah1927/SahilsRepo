package com.fdmgroup.tests.ModelTests;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.Issue;

public class IssueModelTest {

	
	
	@Test
	public void POJOTest() throws DepartmentDoesNotExistException 
	{
		Department department = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		DepartmentDAO departmentdao = (DepartmentDAO) ApplicationContextProvider.getApplicationContext().getBean("departmentDAO");
		department.setDeptName("DEPARTMENT");
		departmentdao.create(department);
		department = departmentdao.read(department);
		
		Issue issue = (Issue) ApplicationContextProvider.getApplicationContext().getBean("issue");
		issue.setDepartment(department.getDeptId());
		issue.setIssueId(100);
		issue.getPriority();
		issue.getDateResolved();
		issue.setDateResolved(new Date());
		issue.getDateSubmitted();
		issue.getDateWithoutTime(new Date());
		issue.getAdminComment();
		issue.getUserDescription();
		
		departmentdao.delete(department);
	}

	@Test
	public void POJOTest_createwithDepartment() throws DepartmentDoesNotExistException 
	{
		Department department = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		DepartmentDAO departmentdao = (DepartmentDAO) ApplicationContextProvider.getApplicationContext().getBean("departmentDAO");
		department.setDeptName("DEPARTMENT");
		departmentdao.create(department);
		int departmentid= departmentdao.read(department).getDeptId();
		Issue issue=new Issue(departmentid);
		assertEquals("DEPARTMENT",issue.getDepartment().getDeptName());
		
		departmentdao.delete(department);
		
		
	}
}
