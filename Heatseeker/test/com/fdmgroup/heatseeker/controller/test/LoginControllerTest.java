package com.fdmgroup.heatseeker.controller.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.controller.LoginController;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.DepartmentAdmin;
import com.fdmgroup.heatseeker.model.GeneralAdmin;
import com.fdmgroup.heatseeker.model.User;

public class LoginControllerTest {

	private LoginController controller = new LoginController();
	private static UserDAO udao;
	private static DepartmentDAO departmentdao;
	private static BasicUser user;
	private static Department department; 
	private static String departmentid;
	private static HttpServletRequest mockRequest;
	private static HttpSession mockSession;
	private static Model mockModel;
	
	
	@BeforeClass
	public static void createUser() throws Exception{
		udao = ApplicationContextProvider.getApplicationContext().getBean("userDAO", UserDAO.class);
		PasswordEncoder encoder = ApplicationContextProvider.getApplicationContext().getBean("passwordEncoder",
				BCryptPasswordEncoder.class);
		user = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user.setUsername("user");
		String hashPass = encoder.encode("pass");
		user.setPassword(hashPass);
		user.setDept(null);
		udao.create(user);
		
		department = (Department) ApplicationContextProvider.getApplicationContext().getBean("department");
		departmentdao = (DepartmentDAO) ApplicationContextProvider.getApplicationContext().getBean("departmentDAO");
		department.setDeptName("dept");
		departmentdao.create(department);
		department = departmentdao.read(department);
		departmentid = Integer.toString(department.getDeptId());
		
		mockRequest = mock(HttpServletRequest.class);
		mockSession = mock(HttpSession.class);
		mockModel = mock(Model.class);

	}
	
	@AfterClass
	public static void teardown() throws UserDoesNotExistException, DepartmentDoesNotExistException{
		udao.delete(user);
		departmentdao.delete(department);
	}
	
	@Test
	public void Test_Logout_ReturnsIndexString() {
		when(mockSession.getAttribute("user")).thenReturn(user);
		String stringReturnedByLogout = controller.logout(mockSession);
		assertEquals("redirect:/index", stringReturnedByLogout);
	}
	
	@Test
	public void Test_Logout_CallsSessionSetAttribute() {
		when(mockSession.getAttribute("user")).thenReturn(user);
		controller.logout(mockSession);
		verify(mockSession, times(1)).invalidate();
	}

	@Test
	public void Test_RegisterDeptAdmin_ReturnsIndexString() throws DepartmentDoesNotExistException, UserDoesNotExistException{
		
		when(mockRequest.getParameter("username")).thenReturn("username");
		when(mockRequest.getParameter("password")).thenReturn("password");
		when(mockRequest.getParameter("deptID")).thenReturn(departmentid);
		
		String stringReturnedByRegisterDeptAdmin = controller.registerDeptAdmin(mockRequest);
		assertEquals("redirect:/index", stringReturnedByRegisterDeptAdmin);
		
		User user2 = (DepartmentAdmin) ApplicationContextProvider.getApplicationContext().getBean("deptAdmin");
		user2.setUsername("username");
		udao.delete(user2);
	}
	@Test
	public void Test_registerGenAdmin_ReturnsIndexString() throws DepartmentDoesNotExistException, UserDoesNotExistException{
		when(mockRequest.getParameter("username")).thenReturn("username");
		when(mockRequest.getParameter("password")).thenReturn("password");
		when(mockRequest.getParameter("deptID")).thenReturn(departmentid);
		
		String stringReturnedByRegisterDeptAdmin = controller.registerGenAdmin(mockRequest);
		assertEquals("redirect:/index", stringReturnedByRegisterDeptAdmin);
		
		User user2 = (GeneralAdmin) ApplicationContextProvider.getApplicationContext().getBean("genAdmin");
		user2.setUsername("username");
		udao.delete(user2);
	}
	
	@Test
	public void Test_Register_ReturnsIndexString() throws UserDoesNotExistException {
		when(mockRequest.getParameter("username")).thenReturn("username");
		when(mockRequest.getParameter("password")).thenReturn("password");
		
		String stringReturnedByRegister = controller.register(mockRequest);
		assertEquals("redirect:/index", stringReturnedByRegister);
	
		User user2 = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user2.setUsername("username");
		udao.delete(user2);
	}
	
	@Test
	public void Test_LoginWithWrongUserName_ReturnsInvalidCredentials() {
		when(mockRequest.getParameter("username")).thenReturn("username1");
		when(mockRequest.getParameter("password")).thenReturn("password");
		
		String stringReturnedByLogin = controller.login(mockModel, mockRequest, mockSession);
		assertEquals("redirect:/invalidCredentials", stringReturnedByLogin);
	}
	
	@Test
	public void Test_LoginWithWrongPassword_ReturnsInvalidCredentials() {
		when(mockRequest.getParameter("username")).thenReturn("user");
		when(mockRequest.getParameter("password")).thenReturn("password");
		
		String stringReturnedByLogin = controller.login(mockModel, mockRequest, mockSession);
		assertEquals("redirect:/invalidCredentials", stringReturnedByLogin);
	}
	
	@Test
	public void Test_Login_ValidCredentials_ReturnsIndexString(){
		when(mockRequest.getParameter("username")).thenReturn("user");
		when(mockRequest.getParameter("password")).thenReturn("pass");
		
		String stringReturnedByLogin = controller.login(mockModel, mockRequest, mockSession);
		assertEquals("redirect:/issues", stringReturnedByLogin);
	}
}
