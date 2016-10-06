package com.fdmgroup.heatseeker.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.commands.AssignIssueCommand;
import com.fdmgroup.heatseeker.commands.CreateIssueUpdateCommand;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.IssueUpdates;
import com.fdmgroup.heatseeker.model.Status;
import com.fdmgroup.heatseeker.model.User;

/**
 * Controller that handles changing the departments for issues, such as
 * rejecting issues, and general admin setting the department
 * 
 * @author shayna.froimowitz
 *
 */

@Controller
public class ChangeDeptController {

	private Logger logger = LoggerFactory.getLogger("Heatseeker");
	
	/**
	 * Called when a department admin rejects an issue not belonging to their
	 * dept
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	public String rejectIssue(HttpServletRequest request) {
		IssueDAO issueDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		DepartmentDAO deptDAO = (DepartmentDAO) ApplicationContextProvider.getApplicationContext()
				.getBean("departmentDAO");

		int issueId = Integer.parseInt(request.getParameter("issueId"));

		Issue issue = null;
		Department dept = null;
		try {
			issue = issueDAO.readById(issueId);
			dept = deptDAO.readById(1);
		} catch (IssueDoesNotExistException | DepartmentDoesNotExistException e) {
			logger.error("Issue or Department does not exist", e);
		}

		AssignIssueCommand assign = new AssignIssueCommand(issue, dept, Status.REJECTED);
		assign.execute();

		logger.info(issue.getTitle()+": department changed to 'General' because it was rejected by Department Admin");


		String[] headers = request.getHeader("referer").split("/");
		String sendTo = headers[headers.length - 1];
		logger.trace("redirecting to: "+sendTo);
		return "redirect:/" + sendTo;
	}

	/**
	 * Called when a general admin changes the department an issue is assigned
	 * to
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/changeDept", method = RequestMethod.POST)
	public String changeDept(HttpServletRequest request, HttpSession session) {
		IssueDAO issueDAO = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
		DepartmentDAO deptDAO = (DepartmentDAO) ApplicationContextProvider.getApplicationContext()
				.getBean("departmentDAO");

		int issueId = Integer.parseInt(request.getParameter("issueId"));
		int deptId = Integer.parseInt(request.getParameter("deptId"));

		User user = (User) session.getAttribute("user");

		Issue issue = null;
		Department dept = null;
		try {
			issue = issueDAO.readById(issueId);
			dept = deptDAO.readById(deptId);

			AssignIssueCommand assign = new AssignIssueCommand(issue, dept, Status.NEW);
			assign.execute();

			IssueUpdates issueUpdate = new IssueUpdates();

			issueUpdate.setSubmittedBy(user);
			issueUpdate.setUpdateDate(new Date());
			String updateText = "Change issue department to: " + dept.getDeptName();
			CreateIssueUpdateCommand updateIssue = new CreateIssueUpdateCommand(updateText, issue, user, session);
			updateIssue.execute();
			
			logger.info(issue.getTitle()+": department changed to: '" + dept.getDeptName() + "' by General Admin");

		} catch (IssueDoesNotExistException | DepartmentDoesNotExistException e) {
			logger.error("Issue or Department does not exist", e);
		}

		String[] headers = request.getHeader("referer").split("/");
		String sendTo = headers[headers.length - 1];
		logger.trace("redirecting to: " + sendTo);
		return "redirect:/" + sendTo;
	}
}
