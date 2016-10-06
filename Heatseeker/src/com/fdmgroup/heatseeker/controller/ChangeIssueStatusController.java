package com.fdmgroup.heatseeker.controller;

import java.io.IOException;
import java.util.Date;

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
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.commands.CreateIssueUpdateCommand;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.DepartmentAdmin;
import com.fdmgroup.heatseeker.model.GeneralAdmin;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Status;
import com.fdmgroup.heatseeker.model.User;

/**
 * 
 * @author Everyone
 *
 */
@Controller

public class ChangeIssueStatusController {

	private Logger logger = LoggerFactory.getLogger("Heatseeker");

	/**
	 * Called by change issue form on the issue page, it change the issue status
	 * and insert a update record in the issueupdates table.
	 * 
	 * @param session:
	 *            use to get user role and user object
	 * @param issueId:
	 *            use to get issue from database
	 * @param statusToUpdate:
	 *            use to set the status
	 * @return String to the issue.jsp page
	 * @throws IssueDoesNotExistException
	 * @throws UserDoesNotExistException
	 * @throws IOException
	 * @throws ServletException
	 *
	 */
	@RequestMapping(value = "/changeIssueStatus", method = RequestMethod.POST)
	public String changeStatus(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IssueDoesNotExistException, ServletException, IOException {
		int issueId = Integer.parseInt(request.getParameter("issueId"));
		Status statusToUpdate = Status.valueOf(request.getParameter("statusToUpdate"));
		IssueDAO iDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");

		Issue issue;

		issue = iDAO.readById(issueId);
		User user = (User) session.getAttribute("user");

		if (statusToUpdate == Status.REJECTED && (user.getClass().equals(DepartmentAdmin.class)
				&& user.getDept().getDeptName().equals(issue.getDepartment().getDeptName()))) {
			issue.setStatus(statusToUpdate);
			logger.info("Issue rejected: " + issue.getTitle());

			DepartmentDAO deptDAO = (DepartmentDAO) ApplicationContextProvider.getApplicationContext()
					.getBean("departmentDAO");
			Department dept = null;
			try {
				dept = deptDAO.readById(1);
			} catch (DepartmentDoesNotExistException e) {
				logger.error("Issue or Department does not exist", e);
			}
			issue.setDepartment(dept);

			logger.info(
					issue.getTitle() + ": department changed to 'General' because it was rejected by Department Admin");
			createIssueUpdate(statusToUpdate, issue, user, session);

		} else if (statusToUpdate == Status.CLOSED && user.getUserId() == issue.getSubmittedBy().getUserId()) {
			issue.setStatus(statusToUpdate);
			issue.setDateResolved(new Date());
			createIssueUpdate(statusToUpdate, issue, user, session);
			logger.info("Issue closed: " + issue.getTitle());
		} else if (issue.getStatus() == Status.NEW && statusToUpdate == Status.OPEN
				&& user.getClass().equals(DepartmentAdmin.class)
				&& user.getDept().getDeptName().equals(issue.getDepartment().getDeptName())) {
			issue.setStatus(statusToUpdate);
			createIssueUpdate(statusToUpdate, issue, user, session);
			logger.info("Updated issue: " + issue.getTitle() + " status to: '" + statusToUpdate +"'");
			
		} else if (issue.getStatus() == Status.OPEN && statusToUpdate == Status.RESOLVED
				&& user.getClass().equals(DepartmentAdmin.class)
				&& user.getDept().getDeptName().equals(issue.getDepartment().getDeptName())) {
			issue.setStatus(statusToUpdate);
			issue.setDateResolved(new Date());
			createIssueUpdate(statusToUpdate, issue, user, session);
			logger.info("Issue resolved: " + issue.getTitle() + " on " + issue.getDateResolved());
		} else if (issue.getStatus() == Status.REJECTED && user.getClass().equals(GeneralAdmin.class)) {
			issue.setStatus(statusToUpdate);
			logger.info("Updated issue: " + issue.getTitle() + " status to: '" + statusToUpdate + "' by General Admin");
			createIssueUpdate(statusToUpdate, issue, user, session);
			
		} else if (issue.getStatus() == Status.RESOLVED && user.getUserId() == issue.getSubmittedBy().getUserId()
				&& statusToUpdate == Status.OPEN) {
			issue.setStatus(statusToUpdate);
			issue.setDateResolved(null);
			logger.info("Issue reopened: " + issue.getTitle());
			createIssueUpdate(statusToUpdate, issue, user, session);
			
		}

		String[] headers = request.getHeader("referer").split("/");
		String sendTo = headers[headers.length - 1];
		logger.trace("redirecting to: " + sendTo);
		return "redirect:/" + sendTo;

	}

	public void createIssueUpdate(Status statusToUpdate, Issue issue, User user, HttpSession session) {
		String updateText = "Change issue status to: " + statusToUpdate;

		CreateIssueUpdateCommand ciuCommand = new CreateIssueUpdateCommand(updateText, issue, user, session);

		try {
			ciuCommand.execute();
		} catch (Exception e) {
			logger.error("Exception thrown", e);;
		}
	}

}
