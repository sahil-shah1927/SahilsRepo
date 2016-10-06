package com.fdmgroup.tests.CommandTests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Test;

import static org.mockito.Mockito.mock;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.commands.CreateIssueUpdateCommand;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.IssueUpdates;

public class CreateIssueUpdateCommandTest {

	@Test
	public void CreateIssueUpdateCommandUpdatesAnIssueTest() throws IssueAlreadyExistsException, IssueDoesNotExistException, DepartmentDoesNotExistException, UserDoesNotExistException 
	{
		DepartmentDAO departmentDAO = (DepartmentDAO) ApplicationContextProvider.getApplicationContext().getBean("departmentDAO");
		IssueDAO issueDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		UserDAO userDAO = (UserDAO) ApplicationContextProvider.getApplicationContext().getBean("userDAO");
		
		Issue testIssue = (Issue) ApplicationContextProvider.getApplicationContext().getBean("issue");
		Department fakeDepartment = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		Department fakeDepartment2 = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		BasicUser testUser = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		
		fakeDepartment.setDeptName("Fake_Department");
		fakeDepartment.setDeptName("Second_Fake_Department");
		
		testUser.setDept(fakeDepartment2);
		testIssue.setDepartment(fakeDepartment);
		testIssue.setSubmittedBy(testUser);
		
		departmentDAO.create(fakeDepartment);
		departmentDAO.create(fakeDepartment2);
		try {
			userDAO.create(testUser);
		} catch (UserAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		issueDAO.create(testIssue);
		
		HttpSession fakeSession = mock(HttpSession.class);
		
		CreateIssueUpdateCommand ciuCommand = new CreateIssueUpdateCommand("HERE IS AN UPDATE", testIssue, testUser, fakeSession);
		ciuCommand.execute();
		
		List<IssueUpdates> resultList = issueDAO.read(testIssue).getUpdates();
		assertEquals("HERE IS AN UPDATE",resultList.get(0).getUpdateText());
		
		userDAO.delete(testUser);
		departmentDAO.delete(fakeDepartment);
		departmentDAO.delete(fakeDepartment2);
	}

}
