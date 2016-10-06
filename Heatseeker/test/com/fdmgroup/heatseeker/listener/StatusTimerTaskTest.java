package com.fdmgroup.heatseeker.listener;

import static org.junit.Assert.assertTrue;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.listeners.StatusTimerTask;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Status;
import com.fdmgroup.heatseeker.model.User;

public class StatusTimerTaskTest {
	
	private static UserDAO dao;
	private static BasicUser user;
	private static IssueDAO issueDao;
	private static Issue testIssue;

	@BeforeClass
	public static void setup() throws UserAlreadyExistsException, IssueAlreadyExistsException {
		dao = ApplicationContextProvider.getApplicationContext().getBean("userDAO", UserDAO.class);
		PasswordEncoder encoder = ApplicationContextProvider.getApplicationContext().getBean("passwordEncoder",
				BCryptPasswordEncoder.class);
		user = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		user.setUsername("user_StatusTimerTaskTest");
		String hashPass = encoder.encode("pass");
		user.setPassword(hashPass);
		user.setDept(null);
		dao.create(user);

		BasicUser sysuser = (BasicUser) ApplicationContextProvider.getApplicationContext().getBean("basicUser");
		sysuser.setUsername("SYSTEM");
		hashPass = encoder.encode("ADMIN123");
		sysuser.setPassword(hashPass);
		sysuser.setDept(null);
		dao.create(sysuser);

		issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		testIssue = new Issue();
		testIssue.setDateSubmitted();
		testIssue.setTitle("hi");
		testIssue.setUserDescription("hu");
		testIssue.setSubmittedBy(user);
		issueDao.create(testIssue);

	}

	@Test
	public void test_statusTimertast_with_RESOLVEDissue() throws IssueDoesNotExistException {
		Issue issueReturn = issueDao.read(testIssue);
		issueReturn.setStatus(Status.RESOLVED);
		long currentTimeMillis = System.currentTimeMillis();
		Date afterminustwoMins = new Date(currentTimeMillis - 60000 * 10);
		issueReturn.setDateResolved(afterminustwoMins);
		issueDao.update(issueReturn);
		StatusTimerTask task = new StatusTimerTask();
		task.run();
		Issue issueReturn2 = issueDao.read(issueReturn);
		assertTrue(issueReturn2.getStatus() == Status.CLOSED);

	}

	@Test
	public void test_statusTimertast_with_REJECTEDissue() throws IssueDoesNotExistException {
		Issue issueReturn = issueDao.read(testIssue);
		issueReturn.setStatus(Status.REJECTED);
		long currentTimeMillis = System.currentTimeMillis();
		Date afterminustwoMins = new Date(currentTimeMillis - 60000 * 10);
		issueReturn.setDateResolved(afterminustwoMins);
		issueDao.update(issueReturn);
		StatusTimerTask task = new StatusTimerTask();
		try {
			task.run();
		} catch (Exception e) {

		}
		Issue issueReturn2 = issueDao.read(issueReturn);
		assertTrue(issueReturn2.getStatus() == Status.REJECTED);

	}
	@AfterClass
	public static void deletesystemuser() throws UserDoesNotExistException, IssueDoesNotExistException{
		//UserDAO userDao = (UserDAO) ApplicationContextProvider.getApplicationContext().getBean("userDAO");
		User systemUser = new BasicUser("SYSTEM", "ADMIN123", "a@fdmgroup.com");
		User userToBeDeleted=dao.read(systemUser);
		issueDao.delete(testIssue);
		dao.delete(userToBeDeleted);
		dao.delete(user);
	
	
	}

}
