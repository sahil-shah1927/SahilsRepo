package com.fdmgroup.heatseeker.listeners;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.commands.UpdateStatusforSystemCommand;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Status;
/**
 * A TimerTask that change issue status from resolved to close after it resolved 2 mins
 * 
 * 
 * @author Lei Lin
 *
 */
public class StatusTimerTask extends TimerTask{

	
	@Override
	public void run() {				
		IssueDAO iDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		List<Issue> issuelist=iDAO.readAll();
		for(Issue issue: issuelist){
			
			long currentTimeMillis=System.currentTimeMillis();
			Date afterminustwoMins=new Date(currentTimeMillis-60000*1);
			
			if((issue.getStatus().equals(Status.RESOLVED))&&(issue.getDateResolved().before(afterminustwoMins))){
				try {
					UpdateStatusforSystemCommand ussCommand = new UpdateStatusforSystemCommand(issue);
					ussCommand.execute();
				} catch (Exception e) {
					System.out.println("UserDoesNotExistException");
					e.printStackTrace();
					
				}
			}
		}
	}

	

	
	

}
