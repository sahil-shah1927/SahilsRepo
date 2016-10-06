package com.fdmgroup.heatseeker.controller.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.fdmgroup.heatseeker.controller.ChangeIssueStatusController;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.DepartmentAdmin;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Status;

public class ChangeIssueStatusControllerTest {

	public ChangeIssueStatusController controller = new ChangeIssueStatusController();
	private static UserDAO dao;
	private static DepartmentDAO ddao;
	private static BasicUser basicUser;
	private static DepartmentAdmin deptAdmin;
	private static Department dept;
	private static Department dept2;
	private static HttpSession mockSession;
	private static HttpServletRequest mockRequest;
	private static HttpServletResponse mockResponse;

	@BeforeClass
	public static void setup() throws UserAlreadyExistsException {
		ddao = ApplicationContextProvider.getApplicationContext().getBean("departmentDAO", DepartmentDAO.class);
		dept = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		dept.setDeptName("department");
		ddao.create(dept);
		
		dao = ApplicationContextProvider.getApplicationContext().getBean("userDAO", UserDAO.class);
		PasswordEncoder encoder = ApplicationContextProvider.getApplicationContext().getBean("passwordEncoder",
				BCryptPasswordEncoder.class);
		basicUser = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		basicUser.setUsername("basicUser");
		String hashPass = encoder.encode("basic");
		basicUser.setPassword(hashPass);
		basicUser.setDept(null);
		dao.create(basicUser);

		deptAdmin = (DepartmentAdmin) ApplicationContextProvider.getApplicationContext().getBean("deptAdmin");
		deptAdmin.setUsername("deptAdmin");
		String deptPass = ("deptAdmin");
		deptAdmin.setPassword(deptPass);
		deptAdmin.setDept(dept);
		dao.create(deptAdmin);

		
		mockSession = mock(HttpSession.class);
		mockRequest = mock(HttpServletRequest.class);
		mockResponse = mock(HttpServletResponse.class);
	}

	@AfterClass
	public static void teardown() throws UserDoesNotExistException, DepartmentDoesNotExistException {
		dao.delete(basicUser);
		dao.delete(deptAdmin);
		ddao.delete(dept);	
	}
	
	@Test
	public void test_basicuser_changeissueinto_CLOSED() throws Exception {
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		Issue testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");

		testIssue.setSubmittedBy(basicUser);
		issueDao.create(testIssue);
		String issueid = Integer.toString(testIssue.getIssueId());

		when(mockRequest.getParameter("statusToUpdate")).thenReturn("CLOSED");
		when(mockRequest.getParameter("issueId")).thenReturn(issueid);
		when(mockSession.getAttribute("user")).thenReturn(basicUser);

		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		String result = controller.changeStatus(mockSession, mockRequest, mockResponse);
		testIssue = issueDao.read(testIssue);
		assertEquals("redirect:/issues", result);
		assertEquals(testIssue.getStatus(), Status.CLOSED);
	}

	@Test
	public void test_basicuser_changeissueinto_REJECTED_StatusStaysTheSame() throws Exception {
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		Issue testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
		testIssue.setStatus(Status.NEW);
		testIssue.setSubmittedBy(basicUser);
		issueDao.create(testIssue);
		String issueid = Integer.toString(testIssue.getIssueId());

		when(mockRequest.getParameter("statusToUpdate")).thenReturn("REJECTED");
		when(mockRequest.getParameter("issueId")).thenReturn(issueid);
		when(mockSession.getAttribute("user")).thenReturn(basicUser);

		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		String result = controller.changeStatus(mockSession, mockRequest, mockResponse);
		testIssue = issueDao.read(testIssue);
		assertEquals("redirect:/issues", result);
		assertEquals(testIssue.getStatus(), Status.NEW);

	}
	
	@Test
	public void test_basicUser_ChangeIssueStatusFromResolvedToOpen_StatusChanges() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException{
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		Issue testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
		testIssue.setStatus(Status.RESOLVED);
		testIssue.setSubmittedBy(basicUser);
		issueDao.create(testIssue);
		String issueid = Integer.toString(testIssue.getIssueId());

		when(mockRequest.getParameter("statusToUpdate")).thenReturn("OPEN");
		when(mockRequest.getParameter("issueId")).thenReturn(issueid);
		when(mockSession.getAttribute("user")).thenReturn(basicUser);

		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		controller.changeStatus(mockSession, mockRequest, mockResponse);
		testIssue = issueDao.read(testIssue);
		assertEquals(testIssue.getStatus(), Status.OPEN);		
		
	}
	
	@Test
	public void test_basicUser_ChangeIssueStatusFromResolvedToNEW_StatusStaysTheSame() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException {
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		Issue testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
		testIssue.setStatus(Status.RESOLVED);
		testIssue.setSubmittedBy(basicUser);
		issueDao.create(testIssue);
		String issueid = Integer.toString(testIssue.getIssueId());

		when(mockRequest.getParameter("statusToUpdate")).thenReturn("NEW");
		when(mockRequest.getParameter("issueId")).thenReturn(issueid);
		when(mockSession.getAttribute("user")).thenReturn(basicUser);

		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		controller.changeStatus(mockSession, mockRequest, mockResponse);
		testIssue = issueDao.read(testIssue);
		assertEquals(testIssue.getStatus(), Status.RESOLVED);		
	}
	
	@Test
	public void test_deptAdmin_ChangeNewToOpen_ChangesStatus() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException{
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		Issue testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
		testIssue.setStatus(Status.NEW);
		testIssue.setSubmittedBy(basicUser);
		testIssue.setDepartment(dept);
		issueDao.create(testIssue);
		String issueid = Integer.toString(testIssue.getIssueId());

		when(mockRequest.getParameter("statusToUpdate")).thenReturn("OPEN");
		when(mockRequest.getParameter("issueId")).thenReturn(issueid);
		when(mockSession.getAttribute("user")).thenReturn(deptAdmin);

		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		controller.changeStatus(mockSession, mockRequest, mockResponse);
		testIssue = issueDao.read(testIssue);
		assertEquals(testIssue.getStatus(), Status.OPEN);
	}
	
	@Test
	public void test_deptAdmin_ChangeNewToRejected_ChangesStatus() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException{
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		Issue testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
		testIssue.setStatus(Status.NEW);
		testIssue.setSubmittedBy(basicUser);
		testIssue.setDepartment(dept);
		issueDao.create(testIssue);
		String issueid = Integer.toString(testIssue.getIssueId());

		when(mockRequest.getParameter("statusToUpdate")).thenReturn("REJECTED");
		when(mockRequest.getParameter("issueId")).thenReturn(issueid);
		when(mockSession.getAttribute("user")).thenReturn(deptAdmin);

		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		controller.changeStatus(mockSession, mockRequest, mockResponse);
		testIssue = issueDao.read(testIssue);
		assertEquals(testIssue.getStatus(), Status.REJECTED);
	}
	
	@Test
	public void test_deptAdmin_ChangeOpenToRejected_ChangesStatus() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException{
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		Issue testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
		testIssue.setStatus(Status.OPEN);
		testIssue.setSubmittedBy(basicUser);
		testIssue.setDepartment(dept);
		issueDao.create(testIssue);
		String issueid = Integer.toString(testIssue.getIssueId());

		when(mockRequest.getParameter("statusToUpdate")).thenReturn("REJECTED");
		when(mockRequest.getParameter("issueId")).thenReturn(issueid);
		when(mockSession.getAttribute("user")).thenReturn(deptAdmin);

		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		controller.changeStatus(mockSession, mockRequest, mockResponse);
		testIssue = issueDao.read(testIssue);
		assertEquals(testIssue.getStatus(), Status.REJECTED);
	}
	
	@Test
	public void test_deptAdmin_ChangeNewToResolved_StatusStaysTheSame() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException{
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		Issue testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
		testIssue.setStatus(Status.NEW);
		testIssue.setSubmittedBy(basicUser);
		testIssue.setDepartment(dept);
		issueDao.create(testIssue);
		String issueid = Integer.toString(testIssue.getIssueId());

		when(mockRequest.getParameter("statusToUpdate")).thenReturn("RESOLVED");
		when(mockRequest.getParameter("issueId")).thenReturn(issueid);
		when(mockSession.getAttribute("user")).thenReturn(deptAdmin);

		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		controller.changeStatus(mockSession, mockRequest, mockResponse);
		testIssue = issueDao.read(testIssue);
		assertEquals(testIssue.getStatus(), Status.NEW);
	}
	
	@Test
	public void test_deptAdmin_ChangeOpenToResolved_StatusStaysTheSame() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException{
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		Issue testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
		testIssue.setStatus(Status.OPEN);
		testIssue.setSubmittedBy(basicUser);
		testIssue.setDepartment(dept);
		issueDao.create(testIssue);
		String issueid = Integer.toString(testIssue.getIssueId());

		when(mockRequest.getParameter("statusToUpdate")).thenReturn("RESOLVED");
		when(mockRequest.getParameter("issueId")).thenReturn(issueid);
		when(mockSession.getAttribute("user")).thenReturn(deptAdmin);

		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		controller.changeStatus(mockSession, mockRequest, mockResponse);
		testIssue = issueDao.read(testIssue);
		assertEquals(testIssue.getStatus(), Status.RESOLVED);
	}
	
	@Test
	public void test_deptAdmin_ChangeOwnIssueNewToClosed_StatusChanges() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException, DepartmentDoesNotExistException{

		dept2 = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		dept2.setDeptName("department2");
		ddao.create(dept2);
		
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		Issue testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
		testIssue.setStatus(Status.NEW);
		testIssue.setSubmittedBy(deptAdmin);
		testIssue.setDepartment(dept2);
		issueDao.create(testIssue);
		String issueid = Integer.toString(testIssue.getIssueId());

		when(mockRequest.getParameter("statusToUpdate")).thenReturn("CLOSED");
		when(mockRequest.getParameter("issueId")).thenReturn(issueid);
		when(mockSession.getAttribute("user")).thenReturn(deptAdmin);

		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		controller.changeStatus(mockSession, mockRequest, mockResponse);
		testIssue = issueDao.read(testIssue);
		assertEquals(testIssue.getStatus(), Status.CLOSED);
		
		issueDao.delete(testIssue);
		ddao.delete(dept2);
	}
	
	@Test
	public void test_deptAdmin_ChangeOwnIssueOpenToResolved_StatusStaysTheSame() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException, DepartmentDoesNotExistException{

		dept2 = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		dept2.setDeptName("department2");
		ddao.create(dept2);
		
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		Issue testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
		testIssue.setStatus(Status.OPEN);
		testIssue.setSubmittedBy(deptAdmin);
		testIssue.setDepartment(dept2);
		issueDao.create(testIssue);
		String issueid = Integer.toString(testIssue.getIssueId());

		when(mockRequest.getParameter("statusToUpdate")).thenReturn("RESOLVED");
		when(mockRequest.getParameter("issueId")).thenReturn(issueid);
		when(mockSession.getAttribute("user")).thenReturn(deptAdmin);

		String headers = "http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		controller.changeStatus(mockSession, mockRequest, mockResponse);
		testIssue = issueDao.read(testIssue);
		assertEquals(testIssue.getStatus(), Status.OPEN);
		
		issueDao.delete(testIssue);
		ddao.delete(dept2);
	}
	
	
}
