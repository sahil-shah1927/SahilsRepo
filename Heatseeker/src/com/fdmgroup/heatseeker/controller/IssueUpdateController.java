package com.fdmgroup.heatseeker.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.commands.CreateIssueUpdateCommand;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.User;

@Controller
public class IssueUpdateController {

	private Logger logger = LoggerFactory.getLogger("Heatseeker");
	
	@RequestMapping(value = "/addIssueUpdate", method = RequestMethod.POST)
	public String postNewIssueUpdate(HttpSession session, HttpServletRequest request) {
		IssueDAO iDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		
		int issueId = Integer.parseInt(request.getParameter("issueId"));
		String updateText = request.getParameter("updateText");
		

		updateText = Jsoup.clean(updateText, Whitelist.none()).trim();	
		if(updateText.length() == 0) {
			String[] headers = request.getHeader("referer").split("/");
			String sendTo = headers[headers.length - 1];
			logger.trace("redirecting to: " + sendTo);
			return "redirect:/"+sendTo;
		}
		
		
		
		
		User submittedBy = (User) session.getAttribute("user");
		
		Issue issue;
		try {
			issue = iDAO.readById(issueId);
			
			CreateIssueUpdateCommand ciuCommand = new CreateIssueUpdateCommand(updateText, issue, submittedBy, session);
			ciuCommand.execute();
			logger.info("Issue updated: " + issue.getTitle() + ": " + updateText);
			
		} catch (IssueDoesNotExistException e) {
			logger.error("Issue does not exist",e);
		}
		
		// this gets the page you are coming from
		String[] headers = request.getHeader("referer").split("/");
		String sendTo = headers[headers.length - 1];
		logger.trace("redirecting to: " + sendTo);
		return "redirect:/"+sendTo;

		
	}
}
