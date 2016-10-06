package com.fdmgroup.heatseeker.commands;

import javax.servlet.http.HttpSession;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.User;

/**
 * A command that creates issues 
 * Implemented using the Command Design Pattern
 * 
 * @author shayna.froimowitz
 *
 */
public class CreateIssue implements Command {
	private Issue issue;
	private HttpSession session;
	
	public CreateIssue(Issue issue) {
		super();
		this.issue = issue;
		this.session= null;
	}
	
	public CreateIssue(Issue issue, HttpSession session) {
		super();
		this.issue = issue;
		this.session=session;
	}
	
	@Override
	public void execute(){
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		
		User user = issue.getSubmittedBy();
		user.addIssue(this.issue);
		 
		
		try {
			issueDao.create(issue);
		} catch (IssueAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (session != null)
		{
			session.setAttribute("user", user);
		}
		
	}

}
