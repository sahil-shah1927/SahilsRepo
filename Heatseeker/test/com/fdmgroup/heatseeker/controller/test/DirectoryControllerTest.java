package com.fdmgroup.heatseeker.controller.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.heatseeker.controller.DirectoryController;

public class DirectoryControllerTest {

	public DirectoryController 	controller = new DirectoryController();
	private static HttpServletRequest mockRequest;
	
	@BeforeClass
	public static void setup(){
		mockRequest = mock(HttpServletRequest.class);
	}
	@Test
	public void Test_goHome_returnsIndexString() {
		String valueReturnedByController = controller.goHome(mockRequest);
		assertEquals("index", valueReturnedByController);
	}
	
	@Test
	public void Test_goIssues_returnsIssuesString() {
		String valueReturnedByController = controller.goIssues();
		assertEquals("issues", valueReturnedByController);
	}
	
	@Test
	public void Test_redirectIndex_returnsIndexString() {

		String valueReturnedByController = controller.redirectIndex(mockRequest);
		assertEquals("index", valueReturnedByController);
	}
	@Test
	public void Test_redirectIssues_returnsIssuesString() {

		String valueReturnedByController = controller.redirectIssues();
		assertEquals("issues", valueReturnedByController);
	}
	
	@Test
	public void Test_redirectInvalidCredentials_returnsIndexString() {

		String valueReturnedByController = controller.redirectInvalidCreds(mockRequest);
		assertEquals("index", valueReturnedByController);
	}
}
