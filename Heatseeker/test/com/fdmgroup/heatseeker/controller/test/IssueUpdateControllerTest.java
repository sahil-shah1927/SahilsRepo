package com.fdmgroup.heatseeker.controller.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import com.fdmgroup.heatseeker.controller.IssueUpdateController;

public class IssueUpdateControllerTest {
	private IssueUpdateController controller=new IssueUpdateController();

	@Test
	public void test_issueupdatecontroller_returnsredirectstring() throws Exception {
		HttpSession mockSession = mock(HttpSession.class);
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		
		when(mockRequest.getParameter("issueId")).thenReturn("1");
		when(mockRequest.getParameter("updateText")).thenReturn("dsagagagh");
		String headers ="http://localhost:8088/Heatseeker/issues";
		when(mockRequest.getHeader("referer")).thenReturn(headers);
		
		String result = controller.postNewIssueUpdate(mockSession,mockRequest);
		assertEquals("redirect:/issues",result);
	}

}
