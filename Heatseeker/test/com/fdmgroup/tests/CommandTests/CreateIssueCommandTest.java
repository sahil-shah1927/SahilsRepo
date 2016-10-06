package com.fdmgroup.tests.CommandTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.commands.CreateIssue;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.Issue;

public class CreateIssueCommandTest {

	private static DepartmentDAO departmentDAO;
	private static UserDAO userDAO;
	private static IssueDAO issueDAO;
	private static Department dept1;
	private static Department dept2;
	private static BasicUser user;
	private static Issue fakeissue;

	@BeforeClass
	public static void createUserIssueAndDepartmentForTesting() {

		departmentDAO = (DepartmentDAO) ApplicationContextProvider.getApplicationContext().getBean("departmentDAO");
		userDAO = (UserDAO) ApplicationContextProvider.getApplicationContext().getBean("userDAO");
		issueDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");

		dept1 = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		dept1.setDeptName("MYDEPTARTMENT");

		dept2 = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		dept2.setDeptName("MYDEPTARTMENT2");

		user = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user.setDept(dept2);
		user.setUsername("user");
		
		fakeissue = (Issue) ApplicationContextProvider.getApplicationContext().getBean("issue");
		fakeissue.setDepartment(dept1);
		fakeissue.setSubmittedBy(user);
		fakeissue.setUserDescription("Whatever");

		departmentDAO.create(dept1);
		departmentDAO.create(dept2);
		try {
			userDAO.create(user);
		} catch (UserAlreadyExistsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	@AfterClass
	public static void teardown() throws UserDoesNotExistException, DepartmentDoesNotExistException{
		userDAO.delete(user);
		departmentDAO.delete(dept1);
		departmentDAO.delete(dept2);
	}

	@Test
	public void CreateIssueCommandCreatesANewIssue() {

		CreateIssue createIssue = new CreateIssue(fakeissue);

		createIssue.execute();

		try {
			assertEquals("MYDEPTARTMENT", issueDAO.read(fakeissue).getDepartment().getDeptName());
		} catch (IssueDoesNotExistException e) {
			fail("CreateIssueCommandTest: Issue Was Not Written!");
		}
	}

	@Test
	public void CreateIssueCommandDoesNotAllowDuplicateEntry() throws IssueDoesNotExistException {
		CreateIssue createIssue = new CreateIssue(fakeissue);

		createIssue.execute();
		Issue copyIssue = issueDAO.read(fakeissue);
		List<Issue> issues = issueDAO.readAll();
		CreateIssue createIssue2 = new CreateIssue(copyIssue);
		createIssue2.execute();
		List<Issue> issuesAfterAttemptedAdd = issueDAO.readAll();
		
		assertEquals(issues.size(), issuesAfterAttemptedAdd.size());
	}

	@Test
	public void CreateIssueCommandAcceptsASessionAttribute() {
		Issue fakeissue = (Issue) ApplicationContextProvider.getApplicationContext().getBean("issue");
		fakeissue.setUserDescription("Whatever");

		HttpSession mySession = null;
		CreateIssue createIssue = new CreateIssue(fakeissue, mySession);

		assertNotNull(createIssue);
	}

}
