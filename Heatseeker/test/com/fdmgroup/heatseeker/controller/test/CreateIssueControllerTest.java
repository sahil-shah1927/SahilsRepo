package com.fdmgroup.heatseeker.controller.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.springframework.ui.Model;
import com.fdmgroup.heatseeker.controller.CreateIssueController;
import com.fdmgroup.heatseeker.model.Issue;

public class CreateIssueControllerTest {

	public CreateIssueController controller = new CreateIssueController();
	
	@Test
	public void Test_makeIssue_returnsAnIssueBean() {		
		Object objectReturnedByMakeIssue = controller.makeIssue();
		assertEquals(Issue.class, objectReturnedByMakeIssue.getClass());
	}
	
	@Test
	public void Test_createIssueWithModelArgument_returnsFileIssueString() {
		Model model = mock(Model.class);
		String stringReturnedByCreateIssue = controller.createIssue(model);
		assertEquals("fileIssue", stringReturnedByCreateIssue);
	}

}
