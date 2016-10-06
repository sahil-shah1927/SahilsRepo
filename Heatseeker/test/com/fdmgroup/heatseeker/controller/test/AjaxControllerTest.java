package com.fdmgroup.heatseeker.controller.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.controller.AjaxController;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Priority;
import com.fdmgroup.heatseeker.model.Status;
import com.fdmgroup.heatseeker.model.User;

public class AjaxControllerTest {
	private AjaxController controller = new AjaxController();
	private static HttpServletRequest mockRequest;
	private static HttpSession mockSession;
	private static String issueid;
	private static UserDAO userDAO;
	private static User user;
	private static DepartmentDAO deptDAO;
	private static Department dept;
	@BeforeClass
	public static void setup() throws UserAlreadyExistsException, IssueAlreadyExistsException, IssueDoesNotExistException {
		mockRequest = mock(HttpServletRequest.class);
		mockSession = mock(HttpSession.class);

		userDAO = ApplicationContextProvider.getApplicationContext().getBean("userDAO", UserDAO.class);
		IssueDAO issueDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		PasswordEncoder encoder = ApplicationContextProvider.getApplicationContext().getBean("passwordEncoder",
				BCryptPasswordEncoder.class);
		user = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user.setUsername("user_AjaxControllerTest");
		String hashPass = encoder.encode("pass");
		user.setPassword(hashPass);
		user.setDept(null);
		userDAO.create(user);
		deptDAO = (DepartmentDAO) ApplicationContextProvider.getApplicationContext().getBean("departmentDAO");
		dept = new Department();
		dept.setDeptName("newDept_AjaxControllerTest");
		deptDAO.create(dept);
		Issue testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
		testIssue.setStatus(Status.OPEN);
	
		testIssue.setSubmittedBy(user);
		testIssue.setDepartment(dept);
		testIssue.setPriority(Priority.LOW);
		issueDAO.create(testIssue);
		Issue issuereturn =issueDAO.read(testIssue);
		issueid = Integer.toString(issuereturn.getIssueId());

	

	}
	
	@AfterClass
	public static void teardown() throws UserDoesNotExistException, DepartmentDoesNotExistException{
		userDAO.delete(user);
		deptDAO.delete(dept);
	}

	@Test
	public void test_getAllDepartmentIssues() {
		String result = controller.getAllDepartmentIssues(mockRequest);
		assertEquals("deptIssuesTable", result);

	}

	@Test
	public void test_getNewDepartmentIssues() {
		String result = controller.getNewDepartmentIssues(mockRequest);
		assertEquals("deptIssuesTable", result);

	}

	@Test
	public void test_getOpenDepartmentIssues() {
		String result = controller.getOpenDepartmentIssues(mockRequest);
		assertEquals("deptIssuesTable", result);

	}

	@Test
	public void test_getResolvedDepartmentIssues() {
		String result = controller.getResolvedDepartmentIssues(mockRequest);
		assertEquals("deptIssuesTable", result);

	}

	@Test
	public void test_getClosedDepartmentIssues() {
		String result = controller.getClosedDepartmentIssues(mockRequest);
		assertEquals("deptIssuesTable", result);

	}

	@Test
	public void test_getAllBasicIssues() {
		String result = controller.getAllBasicIssues(mockRequest);
		assertEquals("issuesTable", result);

	}

	@Test
	public void test_getNewBasicIssues() {
		String result = controller.getNewBasicIssues(mockRequest);
		assertEquals("issuesTable", result);

	}

	@Test
	public void test_getOpenBasicIssues() {
		String result = controller.getOpenBasicIssues(mockRequest);
		assertEquals("issuesTable", result);

	}

	@Test
	public void test_getRejectedBasicDepartmentIssues() {
		String result = controller.getRejectedBasicDepartmentIssues(mockRequest);
		assertEquals("issuesTable", result);

	}

	@Test
	public void test_getResolvedBasicIssues() {
		String result = controller.getResolvedBasicIssues(mockRequest);
		assertEquals("issuesTable", result);

	}

	@Test
	public void test_getClosedBasicIssues() {
		String result = controller.getClosedBasicIssues(mockRequest);
		assertEquals("issuesTable", result);

	}

	@Test
	public void test_getAllGeneralIssues() {
		String result = controller.getAllGeneralIssues(mockRequest);
		assertEquals("genIssuesTable", result);

	}

	@Test
	public void test_getNewGeneralIssues() {
		String result = controller.getNewGeneralIssues(mockRequest);
		assertEquals("genIssuesTable", result);

	}

	@Test
	public void test_getRejectedGeneralIssues() {
		String result = controller.getRejectedGeneralIssues(mockRequest);
		assertEquals("genIssuesTable", result);

	}

	@Test
	public void test_getClosedGeneralIssues() {
		String result = controller.getClosedGeneralIssues(mockRequest);
		assertEquals("genIssuesTable", result);

	}
	
	@Test
	public void test_setIssueStatusMessage() throws IssueDoesNotExistException {
		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		when(mockSession.getAttribute("issue_id")).thenReturn(issueid);
		String result = controller.setIssueStatusMessage(mockRequest, mockSession);
		assertEquals("redirect:/issues", result);
	}

	@Test
	public void test_setIssueMessage() throws IssueDoesNotExistException {
		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		when(mockSession.getAttribute("issue_id")).thenReturn(issueid);
		String result = controller.setIssueMessage(mockRequest, mockSession);
		assertEquals("redirect:/issues", result);
	}
	@Test
	public void test_createIssueMessage() throws IssueDoesNotExistException {
		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		when(mockRequest.getAttribute("message")).thenReturn("message to logger..");
		String result = controller.createIssueMessage(mockRequest, mockSession);
		assertEquals("redirect:/issues", result);
	}
	@Test
	public void test_setDepartmentMessage() throws IssueDoesNotExistException {
		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		when(mockSession.getAttribute("issue_id")).thenReturn(issueid);
		String result = controller.setDepartmentMessage(mockRequest, mockSession);
		assertEquals("redirect:/issues", result);
	}


	@Test
	public void test_removeAttribute() {
		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		String result = controller.removeIssueMessage(mockRequest, mockSession);
		assertEquals("redirect:/issues", result);
	}

	@Test
	public void test_getUpdatesForIssue() {
		String result = controller.getUpdatesForIssue();
		assertEquals("getUpdates", result);

	}

	@Test
	public void test_setIssueId() {
		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		String result = controller.setIssueId(mockRequest, mockSession);
		assertEquals("redirect:/issues", result);
	}

	@Test
	public void test_getNewIssuesNotifications() {
		String result = controller.getNewIssuesNotifications();
		assertEquals("newIssuesNotification", result);

	}

	@Test
	public void test_getAllOpenIssues() {
		String result = controller.getAllOpenIssues(mockRequest);
		assertEquals("genIssuesTable", result);

	}

	@Test
	public void test_getResolvedGeneralIssues() {
		String result = controller.getResolvedGeneralIssues(mockRequest);
		assertEquals("genIssuesTable", result);

	}

}
