package com.fdmgroup.tests.ModelTests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.IssueUpdates;

public class IssueUpdatesModelTest {

	@Test
	public void POJOTest() 
	{
		IssueUpdates update = new IssueUpdates();
		
		update.getUpdateDate();
		update.setUpdateText("hello");
		update.setSubmittedBy(new BasicUser());
		update.setUpdateDate(new Date());
		update.setUpdateId(1);
		assertEquals(1, update.getUpdateId());
		assertEquals(BasicUser.class, update.getSubmittedBy().getClass());
		assertEquals("hello", update.getUpdateText());
	}
	

}
