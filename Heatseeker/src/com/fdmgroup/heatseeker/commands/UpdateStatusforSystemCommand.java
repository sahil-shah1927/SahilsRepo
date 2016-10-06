package com.fdmgroup.heatseeker.commands;

import java.util.Date;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.IssueUpdates;
import com.fdmgroup.heatseeker.model.Status;
import com.fdmgroup.heatseeker.model.User;
/**
 * A command that change issue status from resolved to close
 * Implemented using the Command Design Pattern
 * 
 * @author Lei Lin
 *
 */
public class UpdateStatusforSystemCommand implements Command {

	private Issue issue;
	private User submittedBy;

	public UpdateStatusforSystemCommand(Issue issue) throws UserDoesNotExistException {
		this.submittedBy = getsystemuser();
		this.issue = issue;
	}

	@Override
	public void execute() throws Exception {
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		IssueUpdates newUpdate = (IssueUpdates) ApplicationContextProvider.getApplicationContext()
				.getBean("issueUpdate");
		newUpdate.setSubmittedBy(submittedBy);
		newUpdate.setUpdateDate(new Date());
		newUpdate.setUpdateText("System close this issue.");
		issue.setStatus(Status.CLOSED);
		issue.addUpdate(newUpdate);
		issueDao.update(issue);

	}

	public BasicUser getsystemuser() throws UserDoesNotExistException {		
		UserDAO dao = ApplicationContextProvider.getApplicationContext().getBean("userDAO", UserDAO.class);		
		BasicUser user = new BasicUser();
		user.setUsername("SYSTEM");
		user.setPassword("ADMIN123");
		User syetem = dao.read(user);
		return (BasicUser) syetem;
	}
}
