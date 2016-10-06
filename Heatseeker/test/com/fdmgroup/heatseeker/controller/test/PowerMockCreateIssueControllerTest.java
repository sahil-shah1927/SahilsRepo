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
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.controller.CreateIssueController;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Priority;
import com.fdmgroup.heatseeker.model.User;

/**
 * Because PowerMock prevents EclEmma coverage measurement, all testing under Powermock for the CreateIssueController has been seperated.
 * @author john.haines
 *
 */
//@RunWith(PowerMockRunner.class)  Can't use powermock to see coverage because it happens at runtime but coverage is for compile time.
//@PrepareForTest(CreateIssueController.class)
public class PowerMockCreateIssueControllerTest {

	CreateIssueController controller = new CreateIssueController();
	private static UserDAO udao;
	private static DepartmentDAO ddao;
	private static User user;
	private static Department dept;
	private static Issue testIssue;
	private static HttpSession mockSession;
	private static HttpServletRequest mockRequest;
	
	
	@BeforeClass
	public static void setup() throws UserAlreadyExistsException{
		udao = ApplicationContextProvider.getApplicationContext().getBean("userDAO", UserDAO.class);
		PasswordEncoder encoder = ApplicationContextProvider.getApplicationContext().getBean("passwordEncoder",
				BCryptPasswordEncoder.class);
		user = (User) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user.setUsername("user");
		String hashPass = encoder.encode("pass");
		user.setPassword(hashPass);
		user.setDept(null);
		udao.create(user);
		
		ddao = ApplicationContextProvider.getApplicationContext().getBean("departmentDAO", DepartmentDAO.class);
		dept = ApplicationContextProvider.getApplicationContext().getBean("department", Department.class);
		dept.setDeptName("department");
		ddao.create(dept);
		
		testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
		
		mockSession = mock(HttpSession.class);
		mockRequest = mock(HttpServletRequest.class);
		
	}
	
	@AfterClass
	public static void teardown() throws UserDoesNotExistException, DepartmentDoesNotExistException{
		udao.delete(user);
		ddao.delete(dept);
	}
	
	@Test
	public void Test_createIssue_returnsIssuesString() throws Exception {
				
		String deptid = String.valueOf(dept.getDeptId());
		when(mockRequest.getParameter("deptID")).thenReturn(deptid);
		when(mockSession.getAttribute("user")).thenReturn(user);
		String priority = Priority.LOW.toString().toUpperCase();

		when(mockRequest.getParameter("priorityOptions")).thenReturn(priority);
		
		String stringReturnedByCreateIssue = controller.createIssue(testIssue, mockSession, mockRequest);
		assertEquals("redirect:/issues", stringReturnedByCreateIssue);
	}
}
