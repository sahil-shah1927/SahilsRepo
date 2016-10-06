package com.fdmgroup.heatseeker.commands;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.exceptions.PriorityIsAlreadySetException;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Priority;

/**
 * A command that changes the priority of an issue
 * Implemented using the Command Design Pattern
 * 
 * @author Sahil Sahil
 *
 */
public class UpdateIssuePriority implements Command
{
	private Issue issueToBeUpdated;
	private Priority newPriority;
	
	public UpdateIssuePriority(Issue issue,Priority priority)
	{
		issueToBeUpdated = issue;
		newPriority = priority;
	}
	
	@Override
	public void execute() throws PriorityIsAlreadySetException 
	{
		if(issueToBeUpdated.getPriority() == newPriority)
		{
			throw new PriorityIsAlreadySetException();
		}
		else
		{
			IssueDAO issueDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
			issueToBeUpdated.setPriority(newPriority);
			issueDAO.update(issueToBeUpdated);
		}
		
	}

}
