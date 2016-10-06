package com.fdmgroup.tests.DAOTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.Issue;

public class IssueDAOTest {
 
	static DepartmentDAO departmentDAO;
	static IssueDAO issueDAO;
	static UserDAO userDAO;
	
	static Department dept1;
	static Department dept2;
	static BasicUser user;
	static Issue fakeissue;
	
	@BeforeClass 
	public static void setup() 
	{
		departmentDAO = (DepartmentDAO) ApplicationContextProvider.getApplicationContext().getBean("departmentDAO");
		issueDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		userDAO = (UserDAO) ApplicationContextProvider.getApplicationContext().getBean("userDAO");

		
		dept1 = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		dept1.setDeptName("Test_Department1");
		
		dept2 = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		dept2.setDeptName("Test_Department2");
		
		departmentDAO.create(dept1);
		departmentDAO.create(dept2);
		
		user = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user.setUsername("user");
		user.setDept(dept2);
		
		try {
			userDAO.create(user);
		} catch (UserAlreadyExistsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Before
	public void beforetests(){
		fakeissue = (Issue) ApplicationContextProvider.getApplicationContext().getBean("issue");
		fakeissue.setDepartment(dept1);
		fakeissue.setSubmittedBy(user);

	}
	
	@AfterClass
	public static void teardown() throws UserDoesNotExistException, DepartmentDoesNotExistException{
		userDAO.delete(user);
		departmentDAO.delete(dept1);
		departmentDAO.delete(dept2);
	}
	
	@Test
	public void IssueDAOCreateCreatesANewIssueInDatabase() throws IssueAlreadyExistsException, IssueDoesNotExistException
	{					
		issueDAO.create(fakeissue);
		
		try 
		{
			assertEquals("Test_Department1",issueDAO.read(fakeissue).getDepartment().getDeptName());
		} catch (IssueDoesNotExistException e) 
		{
			fail();
		}
		issueDAO.delete(fakeissue);
	}
	
	@Test
	public void IssueDAOReadThrowsExceptionForNonExistantEntry()
	{
		Issue nonExistantIssue = new Issue();
		
		try
		{
			issueDAO.read(nonExistantIssue);
			fail();
		}
		catch(IssueDoesNotExistException e)
		{
			assertEquals(1,1);
		}
	}
	
	@Test
	public void IssueDAOReadByIDThrowsExceptionForNonExistantID()
	{
		try
		{
			issueDAO.readById(9999);
			fail();
		}
		catch(IssueDoesNotExistException e)
		{
			assertEquals(1,1);
		}
	}

	@Test
	public void IssueDAOReadAllGetsANonEmptyList() throws IssueAlreadyExistsException, IssueDoesNotExistException
	{
		issueDAO.create(fakeissue);
		
		ArrayList<Issue> resultList = (ArrayList<Issue>) issueDAO.readAll();
		assertTrue(!resultList.isEmpty());
		issueDAO.delete(fakeissue);

	}
	
	@Test
	public void IssueDAODeleteDeletesAnEntityFromDB() throws IssueAlreadyExistsException, IssueDoesNotExistException
	{
		
		issueDAO.create(fakeissue);
		issueDAO.delete(fakeissue);

		try
		{
			issueDAO.read(fakeissue);
			fail();
		}
		catch(IssueDoesNotExistException e)
		{
			assertEquals(1,1);
		}
		
	}
}
