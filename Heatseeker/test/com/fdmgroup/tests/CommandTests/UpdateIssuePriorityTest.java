package com.fdmgroup.tests.CommandTests;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.commands.UpdateIssuePriority;
import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.PriorityIsAlreadySetException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Priority;
import com.fdmgroup.heatseeker.model.User;

public class UpdateIssuePriorityTest {

	private static IssueDAO issueDao;
	private static Issue testIssue;
	private static User user;
	private static UserDAO udao;
	
	@BeforeClass
	public static void createissueanduserfortesting() throws UserAlreadyExistsException {
		issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
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
	}
	
	@AfterClass
	public static void teardown() throws UserDoesNotExistException{
		udao.delete(user);
	}

	@Test
	public void test_updateIssuePriority_withdifferentpriority()
			throws UserAlreadyExistsException, IssueAlreadyExistsException {

		Priority newPriority = Priority.HIGH;
		UpdateIssuePriority updateissueupdate = new UpdateIssuePriority(testIssue, newPriority);
		updateissueupdate.execute();
		assertEquals(testIssue.getPriority(), newPriority);

	}

	@Test(expected = PriorityIsAlreadySetException.class)
	public void test_updateIssuePriority_withsamepriority()
			throws UserAlreadyExistsException, IssueAlreadyExistsException {

		Priority oldPriority = Priority.HIGH;
		testIssue.setPriority(oldPriority);
		issueDao.create(testIssue);
		Priority newPriority = Priority.HIGH;
		UpdateIssuePriority updateissueupdate = new UpdateIssuePriority(testIssue, newPriority);
		updateissueupdate.execute();

	}
}
