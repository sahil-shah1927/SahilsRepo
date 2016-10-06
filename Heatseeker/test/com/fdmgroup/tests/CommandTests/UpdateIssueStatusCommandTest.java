package com.fdmgroup.tests.CommandTests;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.commands.UpdateIssueStatus;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Status;

public class UpdateIssueStatusCommandTest {

	private static IssueDAO issueDAO;
	private static UserDAO userDAO;
	private static DepartmentDAO departmentdao;
	private static Issue testIssue;
	private static BasicUser testUser;
	private static Department dept1;
	private static Department dept2;

	
	@BeforeClass
	public static void create() {
		departmentdao = (DepartmentDAO) ApplicationContextProvider.getApplicationContext()
				.getBean("departmentDAO");
		issueDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		userDAO = (UserDAO) ApplicationContextProvider.getApplicationContext().getBean("userDAO");

		testIssue = (Issue) ApplicationContextProvider.getApplicationContext().getBean("issue");
		dept1 = (Department) ApplicationContextProvider.getApplicationContext()
				.getBean("department");
		dept2 = (Department) ApplicationContextProvider.getApplicationContext()
				.getBean("department");
		testUser = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");

		dept1.setDeptName("dept1");
		dept2.setDeptName("dept2");

		testUser.setDept(dept2);
		testUser.setUsername("u");
		testIssue.setDepartment(dept1);
		testIssue.setSubmittedBy(testUser);

		departmentdao.create(dept1);
		departmentdao.create(dept2);
		try {
			userDAO.create(testUser);
		} catch (UserAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void teardown() throws UserDoesNotExistException, DepartmentDoesNotExistException{
		userDAO.delete(testUser);
		departmentdao.delete(dept1);
		departmentdao.delete(dept2);
	}

	@Test
	public void UpdateIssueCommandUpdatesAnIssueTest() throws IssueAlreadyExistsException, IssueDoesNotExistException {

		issueDAO.create(testIssue);

		UpdateIssueStatus update = new UpdateIssueStatus(testIssue, Status.CLOSED);
		update.execute();

		assertEquals(Status.CLOSED, issueDAO.read(testIssue).getStatus());
	}

}
