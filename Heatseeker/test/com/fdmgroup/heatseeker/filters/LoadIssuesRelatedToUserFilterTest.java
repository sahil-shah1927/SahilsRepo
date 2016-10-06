package com.fdmgroup.heatseeker.filters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.User;

public class LoadIssuesRelatedToUserFilterTest {

	private LoadIssuesRelatedToUserFilter filter = new LoadIssuesRelatedToUserFilter();
	private static HttpServletRequest mockRequest;
	private static ServletResponse mockResponse;
	private static FilterChain mockChain;
	private static HttpSession mockSession;
	private static User mockUser;
	
	@BeforeClass
	public static void setup(){
		mockRequest = mock(HttpServletRequest.class);
		mockResponse = mock(ServletResponse.class);
		mockChain = mock(FilterChain.class);
		mockSession = mock(HttpSession.class);
		mockUser = mock(User.class);
	}
	
	@Test
	public void test_LoadIssuesRelatedToUserFilter_doFilterWithBasicUser() throws IOException, ServletException {
		
		when(mockRequest.getSession(false)).thenReturn(mockSession);
		when(mockSession.getAttribute("user")).thenReturn(mockUser);
		when(mockSession.getAttribute("role")).thenReturn("BasicUser");
		
		filter.doFilter(mockRequest, mockResponse, mockChain);
		//Cannot mock cast from ServletRequest to HttpServletRequest
	}
	
	@Test
	public void test_LoadIssuesRelatedToUserFilter_doFilterWithDepartmentAdmin() throws IOException, ServletException {
		Department mockDepartment = mock(Department.class);
		
		when(mockRequest.getSession(false)).thenReturn(mockSession);
		when(mockSession.getAttribute("user")).thenReturn(mockUser);
		when(mockSession.getAttribute("role")).thenReturn("DepartmentAdmin");
		when(mockUser.getDept()).thenReturn(mockDepartment);
		filter.doFilter(mockRequest, mockResponse, mockChain);
		//Cannot mock cast from ServletRequest to HttpServletRequest
	}

	@Test
	public void test_LoadIssuesRelatedToUserFilter_doFilterWithGeneralAdmin() throws IOException, ServletException {
		
		when(mockRequest.getSession(false)).thenReturn(mockSession);
		when(mockSession.getAttribute("user")).thenReturn(mockUser);
		when(mockSession.getAttribute("role")).thenReturn("GeneralAdmin");
		
		filter.doFilter(mockRequest, mockResponse, mockChain);
		//Cannot mock cast from ServletRequest to HttpServletRequest
	}
	
	
	@Test
	public void test_LoadIssuesRelatedToUserFilter_doFilterWithInvalidRole() throws IOException, ServletException {
		
		when(mockRequest.getSession(false)).thenReturn(mockSession);
		when(mockSession.getAttribute("user")).thenReturn(mockUser);
		when(mockSession.getAttribute("role")).thenReturn("Invalid");
		
		filter.doFilter(mockRequest, mockResponse, mockChain);
		//Cannot mock cast from ServletRequest to HttpServletRequest
	}
	
	@Test
	public void test_LoadIssuesRelatedToUserFilter_doFilterWithNullUser() throws IOException, ServletException {
		
		when(mockRequest.getSession(false)).thenReturn(mockSession);
		when(mockSession.getAttribute("user")).thenReturn(null);
		when(mockSession.getAttribute("role")).thenReturn(null);
		
		filter.doFilter(mockRequest, mockResponse, mockChain);
	}
}
