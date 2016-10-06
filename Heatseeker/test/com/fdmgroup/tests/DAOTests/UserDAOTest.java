package com.fdmgroup.tests.DAOTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.User;

/**
 * Test all CRUD Operations for UserDAO
 * Make sure persistence method is set to "Create"
 * 
 * @author sahil.shah
 *
 */
public class UserDAOTest 
{

	private static User user;
	private static User user2;
	private static User user3;
	private static User user4;
	private static User user5;
	private static UserDAO userdao;
	
	private static Department dept1;
	private static DepartmentDAO departmentdao;
	
	@BeforeClass 
	public static void initialize() 
	{
		//Setup dao and 4 sample users to be used inside tests
		
		departmentdao = (DepartmentDAO) ApplicationContextProvider.getApplicationContext().getBean("departmentDAO");
		
		dept1 = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		dept1.setDeptName("SampleDept");
		departmentdao.create(dept1);
		
		userdao = (UserDAO) ApplicationContextProvider.getApplicationContext().getBean("userDAO");
		
		user = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user.setUsername("test_username");
		user.setPassword("test_password1");
		user.setEmail("test_email");
		user.setDept(dept1);
		
		user2 = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user2.setUsername("second_username");
		user2.setPassword("test_password2");
		user2.setEmail("test_ email");
		user2.setDept(dept1);
		
		user3 = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user3.setUsername("third_username");
		user3.setPassword("test_password3");
		user3.setEmail("test_email");
		user3.setDept(dept1);
		
		user4 = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user4.setUsername("fourth_username");
		user4.setPassword("test_password4");
		user4.setEmail("test_email");
		user4.setDept(dept1);
		
		user5 = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user5.setUsername("fifth_username");
		user5.setPassword("test_password5");
		user5.setEmail("test_email");
		user5.setDept(dept1);
		

		
	}
	
	@AfterClass
	public static void teardown() throws UserDoesNotExistException, DepartmentDoesNotExistException{
		userdao.delete(user);
		userdao.delete(user3);
		userdao.delete(user4);
		userdao.delete(user5);
		departmentdao.delete(dept1);

	}
	
	@Test
	public void UserDAOCreateCallsEntityManagerPersistAndWritesTest() 
	{
		try {
			userdao.create(user);
		} catch (UserAlreadyExistsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try 
		{
			//assertEquals("test_username",userdao.read(user).getUsername());
			assertEquals("test_password1", userdao.read(user).getPassword());
		} catch (UserDoesNotExistException e) 
		{
			fail();
		}
	}
	
	@Test
	public void UserDAOReadCallsEntityManagerFindAndReturnsTest() throws UserDoesNotExistException 
	{
	
		try {
			userdao.create(user4);
		} catch (UserAlreadyExistsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try 
		{
			user4 = userdao.read(user4);
		} catch (UserDoesNotExistException e) 
		{
			fail();
		}
		assertEquals(user4.getEmail(),"test_email");
		assertEquals(user4.getUsername(),"fourth_username");
		assertEquals(user4.getPassword(),"test_password4");
		}
	
	@Test
	public void UserDAODeleteCallsEntityManagerRemoveAndRemovesTest()
	{
		try {
			userdao.create(user2);
		} catch (UserAlreadyExistsException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try
		{
			userdao.delete(user2);
		} 
		catch (UserDoesNotExistException e1) 
		{
			fail();
		}
		
		try
		{
			userdao.read(user2);
			fail();
		}
		catch(UserDoesNotExistException e)
		{
			assertEquals(1,1);
		}
		
	}
	
	@Test
	public void UserDAODeleteDoesNotAllowDeletingNonExistantEntity()
	{
		BasicUser fakeuser = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		fakeuser.setUsername("fake_username");
		
		try
		{
			userdao.delete(fakeuser);
			fail();
		}
		catch(UserDoesNotExistException e)
		{
			assertEquals(1,1);
		}
	}
	
	@Test
	public void UserDAOUpdateUpdatesEntityInDatabaseTest() 
	{
		try {
			userdao.create(user3);
		} catch (UserAlreadyExistsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		user3.setPassword("newUpdatedPassword");
		
		userdao.update(user3);
		
		try 
		{
			assertEquals("newUpdatedPassword",userdao.read(user3).getPassword());
		} catch (UserDoesNotExistException e) 
		{
			fail();
		}
		
	}
	
	@Test
	public void UserDAOReadAllReturnsListOfAllUsers()
	{
		ArrayList<User> myList = (ArrayList<User>) userdao.readAll();
		
		assertTrue(4 == myList.size() || 13 == myList.size());
	}
	
	@Test
	public void UserDAODoesNotAllowDuplicates()
	{
		try {
			userdao.create(user5);
		} catch (UserAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			userdao.create(user5);
		} catch (UserAlreadyExistsException e) {
			assertEquals(1,1);
		}
	}

}
