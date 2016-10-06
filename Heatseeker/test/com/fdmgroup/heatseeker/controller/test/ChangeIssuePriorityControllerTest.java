package com.fdmgroup.heatseeker.controller.test;

import static org.junit.Assert.*;
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
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.controller.ChangeIssuePriorityController;
import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Priority;
import com.fdmgroup.heatseeker.model.User;

public class ChangeIssuePriorityControllerTest {

	public ChangeIssuePriorityController controller = new ChangeIssuePriorityController();
	private static UserDAO userDAO;
	private static IssueDAO issueDAO;
	private static User user;
	private static HttpSession mockSession;
	private static HttpServletRequest mockRequest;
	private static HttpServletResponse mockResponse;
	
	
	@BeforeClass
	public static void setup() throws UserAlreadyExistsException{
		userDAO = ApplicationContextProvider.getApplicationContext().getBean("userDAO", UserDAO.class);
		issueDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		PasswordEncoder encoder = ApplicationContextProvider.getApplicationContext().getBean("passwordEncoder",
				BCryptPasswordEncoder.class);
		user = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user.setUsername("user");
		String hashPass = encoder.encode("pass");
		user.setPassword(hashPass);
		user.setDept(null); 
		userDAO.create(user);  
		
		mockSession = mock(HttpSession.class);
		mockRequest = mock(HttpServletRequest.class);
		mockResponse = mock(HttpServletResponse.class);
	}
	
	@AfterClass
	public static void teardown() throws UserDoesNotExistException{
		userDAO.delete(user);
	}
	public Issue changePriority(User loggedInUser, Priority priority) throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException{
		Issue testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
	
		testIssue.setSubmittedBy(user);
		issueDAO.create(testIssue);
		Issue issuereturn =issueDAO.read(testIssue);
		String issueid = Integer.toString(issuereturn.getIssueId());
		
		when(mockRequest.getParameter("issueId")).thenReturn(issueid);
		when(mockRequest.getParameter("priorityOptions")).thenReturn(priority.toString().toUpperCase());
		when(mockSession.getAttribute("user")).thenReturn(loggedInUser);
				
		String headers ="http://localhost:8088/Heatseeker/issues";   
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		String result=controller.changePriority(mockSession, mockRequest, mockResponse);
		assertEquals("redirect:/issues",result);	
		return testIssue;
	}
	@Test
	public void test_changePriority_toLOW_AsTheUserThatSubmittedIt() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException{
		Priority priority = Priority.LOW;
		Issue issuereturn = issueDAO.read(changePriority(user, priority));
		assertEquals(priority, issuereturn.getPriority());
	}	
	@Test
	public void test_changePriority_toMEDIUM_AsTheUserThatSubmittedIt() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException{
		Priority priority = Priority.MEDIUM;
		Issue issuereturn = issueDAO.read(changePriority(user, priority));
		assertEquals(priority, issuereturn.getPriority());
	}
	
	@Test
	public void test_changePriority_toHIGH_AsTheUserThatSubmittedIt() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException{
		Priority priority = Priority.HIGH;
		Issue issuereturn = issueDAO.read(changePriority(user, priority));
		assertEquals(priority, issuereturn.getPriority());
	}
	
	@Test
	public void test_changePriority_toLOW_AsNotTheUserThatSubmittedIt_IssuePriorityShouldNotChange() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException{
		Priority newPriority = Priority.LOW;		
		User userThatDidNotSubmitIssue = new BasicUser();
		
		Issue issuereturn = issueDAO.read(changePriority(userThatDidNotSubmitIssue, newPriority));
		assertNotEquals(newPriority, issuereturn.getPriority());
	}
	
	@Test
	public void test_changePriority_toMEDIUM_AsNotTheUserThatSubmittedIt_IssuePriorityShouldNotChange() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException{
		Priority newPriority = Priority.MEDIUM;		
		User userThatDidNotSubmitIssue = new BasicUser();
		
		Issue issuereturn = issueDAO.read(changePriority(userThatDidNotSubmitIssue, newPriority));
		assertNotEquals(newPriority, issuereturn.getPriority());
	}
	
	@Test
	public void test_changePriority_toHIGH_AsNotTheUserThatSubmittedIt_IssuePriorityShouldNotChange() throws IssueAlreadyExistsException, IssueDoesNotExistException, ServletException, IOException{
		Priority newPriority = Priority.HIGH;		
		User userThatDidNotSubmitIssue = new BasicUser();
		
		Issue issuereturn = issueDAO.read(changePriority(userThatDidNotSubmitIssue, newPriority));
		assertNotEquals(newPriority, issuereturn.getPriority());
	}
}
