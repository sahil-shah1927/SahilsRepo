package com.fdmgroup.heatseeker.filters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.fdmgroup.heatseeker.model.User;

public class AUserisLoggedInFilterTest {

	private AUserIsLoggedInFilter filter = new AUserIsLoggedInFilter();
	private static HttpServletRequest mockRequest;
	private static ServletResponse mockResponse;
	private static FilterChain mockChain;
	private static HttpSession mockSession;
	private static User mockUser;
	private static RequestDispatcher mockRequestDispatcher;
	
	@Before
	public void setup(){
		mockRequest = mock(HttpServletRequest.class);
		mockResponse = mock(ServletResponse.class);
		mockChain = mock(FilterChain.class);
		mockSession = mock(HttpSession.class);
		mockUser = mock(User.class);
		mockRequestDispatcher = mock(RequestDispatcher.class);
	}
	
	@Test
	public void test_LoadIssuesRelatedToUserFilter_doFilterWithNoLoggedInUserAndNullRole_SetsAuthenticateFalse_ForwardsToLogin() throws IOException, ServletException {
		
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("user")).thenReturn(null);
		when(mockSession.getAttribute("role")).thenReturn(null);
		when(mockRequest.getRequestDispatcher("login")).thenReturn(mockRequestDispatcher);
		
		
		filter.doFilter(mockRequest, mockResponse, mockChain);
		Mockito.verify(mockRequest).setAttribute("authenticate", false);
		Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
		//Cannot mock cast from ServletRequest to HttpServletRequest
	}
	
	@Test
	public void test_LoadIssuesRelatedToUserFilter_doFilterWithLoggedInUserAndNullRole_SetsAuthenticateFalse_ForwardsToLogin() throws IOException, ServletException {
		
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("user")).thenReturn(mockUser);
		when(mockSession.getAttribute("role")).thenReturn(null);
		when(mockRequest.getRequestDispatcher("login")).thenReturn(mockRequestDispatcher);
		
		
		filter.doFilter(mockRequest, mockResponse, mockChain);
		Mockito.verify(mockRequest).setAttribute("authenticate", false);
		Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
		//Cannot mock cast from ServletRequest to HttpServletRequest
	}
	
	@Test
	public void test_LoadIssuesRelatedToUserFilter_doFilterWithNoLoggedInUserAndBasicUserRole_SetsAuthenticateFalse_ForwardsToLogin() throws IOException, ServletException {
		
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("user")).thenReturn(null);
		when(mockSession.getAttribute("role")).thenReturn("BasicUser");
		when(mockRequest.getRequestDispatcher("login")).thenReturn(mockRequestDispatcher);
		
		
		filter.doFilter(mockRequest, mockResponse, mockChain);
		Mockito.verify(mockRequest).setAttribute("authenticate", false);
		Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
		//Cannot mock cast from ServletRequest to HttpServletRequest
	}
	
	@Test
	public void test_LoadIssuesRelatedToUserFilter_doFilterWithLoggedInUserAndBasicUserRole_CallsNextInChain() throws IOException, ServletException {
		
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("user")).thenReturn(mockUser);
		when(mockSession.getAttribute("role")).thenReturn("BasicUser");
		
		
		filter.doFilter(mockRequest, mockResponse, mockChain);
		Mockito.verify(mockChain).doFilter(mockRequest, mockResponse);
		//Cannot mock cast from ServletRequest to HttpServletRequest
	}
	
	@Test
	public void test_LoadIssuesRelatedToUserFilter_doFilterWithNoLoggedInUserAndDeptAdminRole_SetsAuthenticateFalse_ForwardsToLogin() throws IOException, ServletException {
		
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("user")).thenReturn(null);
		when(mockSession.getAttribute("role")).thenReturn("DepartmentAdmin");
		when(mockRequest.getRequestDispatcher("login")).thenReturn(mockRequestDispatcher);
		
		
		filter.doFilter(mockRequest, mockResponse, mockChain);
		Mockito.verify(mockRequest).setAttribute("authenticate", false);
		Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
		//Cannot mock cast from ServletRequest to HttpServletRequest
	}
	
	@Test
	public void test_LoadIssuesRelatedToUserFilter_doFilterWithLoggedInUserAndDepartmentAdminRole_CallsNextInChain() throws IOException, ServletException {
		
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("user")).thenReturn(mockUser);
		when(mockSession.getAttribute("role")).thenReturn("DepartmentAdmin");
		
		
		filter.doFilter(mockRequest, mockResponse, mockChain);
		Mockito.verify(mockChain).doFilter(mockRequest, mockResponse);
		//Cannot mock cast from ServletRequest to HttpServletRequest
	}
	
	@Test
	public void test_LoadIssuesRelatedToUserFilter_doFilterWithNoLoggedInUserAndGenAdminRole_SetsAuthenticateFalse_ForwardsToLogin() throws IOException, ServletException {
		
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("user")).thenReturn(null);
		when(mockSession.getAttribute("role")).thenReturn("GeneralAdmin");
		when(mockRequest.getRequestDispatcher("login")).thenReturn(mockRequestDispatcher);
		
		
		filter.doFilter(mockRequest, mockResponse, mockChain);
		Mockito.verify(mockRequest).setAttribute("authenticate", false);
		Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
		//Cannot mock cast from ServletRequest to HttpServletRequest
	}
	
	@Test
	public void test_LoadIssuesRelatedToUserFilter_doFilterWithLoggedInUserAndGenAdminRole_CallsNextInChain() throws IOException, ServletException {
		
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("user")).thenReturn(mockUser);
		when(mockSession.getAttribute("role")).thenReturn("GeneralAdmin");
		
		
		filter.doFilter(mockRequest, mockResponse, mockChain);
		Mockito.verify(mockChain).doFilter(mockRequest, mockResponse);
		//Cannot mock cast from ServletRequest to HttpServletRequest
	}
	
}
