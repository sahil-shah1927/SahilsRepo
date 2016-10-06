package com.fdmgroup.heatseeker.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.commands.CreateIssueUpdateCommand;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Priority;
import com.fdmgroup.heatseeker.model.User;

@Controller
public class ChangeIssuePriorityController {
	private Logger logger = LoggerFactory.getLogger("Heatseeker");

	/**
	 * Called by change issue form on the issue page, it change the issue
	 * priority and insert a update record in the issueupdates table.
	 * 
	 * @param session:
	 *            use to get user role and user object
	 * @param issueId:
	 *            use to get issue from database
	 * @return String to the issue.jsp page
	 * @throws IssueDoesNotExistException
	 * @throws UserDoesNotExistException
	 * @throws IOException
	 * @throws ServletException
	 *
	 */
	@RequestMapping(value = "/changeIssuePriority", method = RequestMethod.POST)
	public String changePriority(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IssueDoesNotExistException, ServletException, IOException {
		int issueId = Integer.parseInt(request.getParameter("issueId"));
		Priority priorityToUpdate = Priority.valueOf(request.getParameter("priorityOptions"));
		IssueDAO iDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");

		Issue issue;

		issue = iDAO.readById(issueId);
		User user = (User) session.getAttribute("user");

		if (issue.getSubmittedBy().getUserId() == user.getUserId() && issue.getPriority() != priorityToUpdate) {

			issue.setPriority(priorityToUpdate);

			String updateText = "Change issue priority to: " + priorityToUpdate;

			CreateIssueUpdateCommand ciuCommand = new CreateIssueUpdateCommand(updateText, issue, user, session);

			try {
				ciuCommand.execute();
				logger.info(
						"Issue: " + issue.getTitle() + "priority changed to: '" + priorityToUpdate.toString() + "'");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String[] headers = request.getHeader("referer").split("/");
		String sendTo = headers[headers.length - 1];
		logger.trace("redirecting to: " + sendTo);
		return "redirect:/" + sendTo;
	}

}
