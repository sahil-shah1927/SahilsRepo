package com.fdmgroup.heatseeker.commands;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.IssueUpdates;
import com.fdmgroup.heatseeker.model.User;

/**
 * A command that creates issue updates
 * Implemented using the Command Design Pattern
 * 
 * @author Michael Loconte
 *
 */
public class CreateIssueUpdateCommand implements Command{
	private String updateText;
	private Issue issue;
	private User submittedBy;
	private HttpSession session;
	
	public CreateIssueUpdateCommand(String updateText, Issue issue, User submittedBy, HttpSession session){
		this.updateText = updateText;
		this.issue = issue;
		this.submittedBy = submittedBy;
		this.session = session;
	}
	
	
	@Override
	public void execute() {
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		UserDAO userDao = (UserDAO) ApplicationContextProvider.getApplicationContext().getBean("userDAO");
		try {
			IssueUpdates newUpdate = (IssueUpdates) ApplicationContextProvider.getApplicationContext().getBean("issueUpdate");
			newUpdate.setSubmittedBy(submittedBy);
			newUpdate.setUpdateDate(new Date());
			newUpdate.setUpdateText(updateText);
			issue.addUpdate(newUpdate);
			issueDao.update(issue);
			session.setAttribute("user", userDao.read(submittedBy));
		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	
	
}
