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
import com.fdmgroup.heatseeker.controller.ChangeDeptController;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.User;

public class ChangeDeptControllerTest {

	ChangeDeptController controller = new ChangeDeptController();
	static IssueDAO idao;
	static UserDAO udao;
	static DepartmentDAO deptDAO;
	static Issue testIssue;
	static User user;
	static Department dept;
	static HttpServletRequest mockRequest;
	static HttpSession mockSession;
	
	
	@BeforeClass
	public static void setUpIssueAndUser() throws UserAlreadyExistsException, IssueAlreadyExistsException, IssueDoesNotExistException{
		idao = ApplicationContextProvider.getApplicationContext().getBean("issueDAO", IssueDAO.class);
		testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
	
		udao = ApplicationContextProvider.getApplicationContext().getBean("userDAO", UserDAO.class);
		PasswordEncoder encoder = ApplicationContextProvider.getApplicationContext().getBean("passwordEncoder",
				BCryptPasswordEncoder.class);
		user = (User) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user.setUsername("user");
		String hashPass = encoder.encode("pass");
		user.setPassword(hashPass);
		user.setDept(null);
		udao.create(user);
		
		testIssue.setSubmittedBy(user);
		idao.create(testIssue);
		testIssue = idao.read(testIssue);
		
		deptDAO = (DepartmentDAO) ApplicationContextProvider.getApplicationContext().getBean("departmentDAO");
		dept = new Department();
		dept.setDeptName("newDept");
		deptDAO.create(dept);
		
		mockRequest = mock(HttpServletRequest.class);
		mockSession = mock(HttpSession.class);
		
	}
	
	@AfterClass
	public static void teardown() throws UserDoesNotExistException, DepartmentDoesNotExistException{
		udao.delete(user);
		deptDAO.delete(dept);
	}
	
	@Test
	public void testRejectIssueReturnsDeptIssues() throws Exception {
		
		testIssue = idao.read(testIssue);
		String id = String.valueOf(testIssue.getIssueId());
		
		when(mockRequest.getParameter("issueId")).thenReturn(id);		 
		when(mockRequest.getHeader("referer")).thenReturn("/deptIssues");
		String stringReturnedByRejectIssue = controller.rejectIssue(mockRequest);
		assertEquals("redirect:/deptIssues", stringReturnedByRejectIssue);
	}
	
	@Test
	public void testChangeDeptReturnsDeptIssues() throws Exception {

		when(mockRequest.getParameter("issueId")).thenReturn("1");
		when(mockRequest.getParameter("deptId")).thenReturn("2");
		when(mockRequest.getHeader("referer")).thenReturn("/deptIssues");
		String stringReturnedByChangeDept = controller.changeDept(mockRequest, mockSession);
		assertEquals("redirect:/deptIssues", stringReturnedByChangeDept);
	}
	
	@Test
	public void testChangeDeptWithIssueAndDept() throws IssueDoesNotExistException, DepartmentDoesNotExistException {

		testIssue = idao.read(testIssue);
		String issueID = String.valueOf(testIssue.getIssueId());
		Department testDept = deptDAO.read(dept);
		String deptID = String.valueOf(testDept.getDeptId());
		when(mockRequest.getParameter("issueId")).thenReturn(issueID);
		when(mockRequest.getParameter("deptId")).thenReturn(deptID);
		when(mockSession.getAttribute("user")).thenReturn(user);
		when(mockRequest.getHeader("referer")).thenReturn("/deptIssues");
		String stringReturnedByChangeDept = controller.changeDept(mockRequest, mockSession);
		assertEquals("redirect:/deptIssues", stringReturnedByChangeDept);

	}

}
