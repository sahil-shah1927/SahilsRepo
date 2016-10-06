package com.fdmgroup.heatseeker.commands;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Status;

/**
 * A command that changes the status of an issue
 * Implemented using the Command Design Pattern
 * 
 * @author Lei Lin
 *
 */
public class UpdateIssueStatus implements Command {
	private Issue issue;
	private Status status;
	
	public UpdateIssueStatus(Issue issue, Status status) {
		super();
		this.issue = issue;
		this.status = status;
	}

	@Override
	public void execute() {
		IssueDAO dao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		issue.setStatus(status);
		dao.update(issue);
	}

}
