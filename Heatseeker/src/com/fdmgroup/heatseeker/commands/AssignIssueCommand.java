package com.fdmgroup.heatseeker.commands;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Status;

/**
 * A command that assigns a department to an issue Implemented using the Command
 * Design Pattern
 * 
 * @author shayna.froimowitz
 *
 */
public class AssignIssueCommand implements Command {
	private Department department;
	private Issue issue;
	private Status status;

	public AssignIssueCommand(Issue issue, Department department, Status status) {
		super();
		this.department = department;
		this.issue = issue;
		this.status = status;
	}

	/**
	 * Command method execute assigns the issue to a department by getting the
	 * issue object, setting a department then updating it to the database
	 */
	@Override
	public void execute() {
		IssueDAO issueDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");

		issue.setDepartment(department);
		issue.setStatus(status);

		issueDAO.update(issue);

	}
}
