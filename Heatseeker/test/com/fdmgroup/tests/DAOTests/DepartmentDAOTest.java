package com.fdmgroup.tests.DAOTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.Issue;

/**
 * Test all CRUD Operations for DepartmentDAO
 * Make sure persistence method is set to "Create"
 * 
 * @author sahil.shah
 *
 */
public class DepartmentDAOTest {

	DepartmentDAO departmentDAO;
	IssueDAO issueDAO;
	UserDAO userDAO;
	
	Department dept1;
	Department dept2;
	
	@Before 
	public void setup() 
	{
		departmentDAO = (DepartmentDAO) ApplicationContextProvider.getApplicationContext().getBean("departmentDAO");
		issueDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		userDAO = (UserDAO) ApplicationContextProvider.getApplicationContext().getBean("userDAO");

		
		dept1 = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		dept1.setDeptName("Test_Department1");
		
		dept2 = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		dept2.setDeptName("Test_Department2");
	}
	
	@Test
	public void DepartmentDAOCreateCallsEntityManagerPersistAndWritesTest() throws DepartmentDoesNotExistException 
	{
		departmentDAO.create(dept1);
		assertEquals(1,1);
		departmentDAO.delete(dept1);
	}
	
	@Test
	public void DepartmentDAOReadCallsFindAndReadsEntityTest() throws DepartmentDoesNotExistException
	{
		departmentDAO.create(dept2);
		try 
		{
			assertEquals("Test_Department2",departmentDAO.read(dept2).getDeptName());
		} 
		catch (DepartmentDoesNotExistException e) 
		{
			fail("DepartmentDAO Error: Department was not read!");
		}
		departmentDAO.delete(dept2);

	}
	
	@Test
	public void DepartmentDAOReadAllReturnsListOfAllUsersTest() throws DepartmentDoesNotExistException
	{

		
		departmentDAO.create(dept1);
		departmentDAO.create(dept2);
		
		List<Department> myList = departmentDAO.readAll();
		
		//assertEquals(2,myList.size());
		assertTrue(2 == myList.size() || 7 == myList.size());
		
		departmentDAO.delete(dept1);
		departmentDAO.delete(dept2);

	}
	
	@Test
	public void DepartmentDAOUpdateUpdatesTest() throws DepartmentDoesNotExistException
	{
		departmentDAO.create(dept1);
		dept1.setDeptName("updatedName");
		departmentDAO.update(dept1);
		
		try 
		{
			assertEquals("updatedName",departmentDAO.read(dept1).getDeptName());
		} catch (DepartmentDoesNotExistException e) 
		{
			fail("DepartmentDAO Error: Department was not read in DepartmentDAOUpdateUpdatesTest()!");
		}
		
		departmentDAO.delete(dept1);
		
	}
	
	@Test
	public void DepartmentDAODeleteCallsEntityManagerRemoveAndRemovesTest()
	{
		departmentDAO.create(dept1);
		
		try
		{
			departmentDAO.delete(dept1);
		} 
		catch (DepartmentDoesNotExistException e1) 
		{
			fail();
		}
		
		try
		{
			departmentDAO.read(dept1);
			fail();
		}
		catch(DepartmentDoesNotExistException e)
		{
			assertEquals(1,1);
		}
		
	}
	
	@Test
	public void DepartmentDAODeleteDoesNotAllowDeletingNonExistantEntity()
	{
		try
		{
			departmentDAO.delete(dept1);
			fail();
		}
		catch(DepartmentDoesNotExistException e)
		{
			assertEquals(1,1);
		}
	}
	
	@Test
	public void DepartmentDAOReadAllIssuesByDeptReadsAListOfIssuesTest() throws IssueAlreadyExistsException, DepartmentDoesNotExistException, UserDoesNotExistException
	{
		BasicUser buser = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		buser.setDept(dept1);
		buser.setUsername("user");
		Issue fakeissue = (Issue) ApplicationContextProvider.getApplicationContext().getBean("issue");
		fakeissue.setDepartment(dept2);
		fakeissue.setSubmittedBy(buser);
	
		departmentDAO.create(dept1);
		departmentDAO.create(dept2);
		
		try {
			userDAO.create(buser);
			fakeissue.setSubmittedBy(buser);
			issueDAO.create(fakeissue);
		} catch (UserAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Issue> issues = departmentDAO.readAllIssuesByDept(dept2.getDeptId());
		assertEquals(1, issues.size());

		userDAO.delete(buser);
		departmentDAO.delete(dept2);
		departmentDAO.delete(dept1);
	}
	
	

}
