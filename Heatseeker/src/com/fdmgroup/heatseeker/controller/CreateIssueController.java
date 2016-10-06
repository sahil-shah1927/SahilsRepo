package com.fdmgroup.heatseeker.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.commands.CreateIssue;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Priority;
import com.fdmgroup.heatseeker.model.User;

/**
 * 
 * @author john.haines
 *
 */
@Controller
public class CreateIssueController {

	private Logger logger = LoggerFactory.getLogger("Heatseeker");

	@ModelAttribute("issue")
	public Issue makeIssue() {
		Issue issue = ApplicationContextProvider.getApplicationContext().getBean("issue", Issue.class);
		return issue;
	}

	/**
	 * Called by the create issue form on the create issue page, it adds the new
	 * issue to the Database and redirects the user to the view issue page. Due
	 * to JaCoCo's method of determining class identity, this class will not
	 * appear in EclEmma's coverage report when run prepared by PowerMockito. It
	 * is tested.
	 * 
	 * @param request
	 *            the HttpServletRequest for the form from which the parameters
	 *            are retrieved.
	 * @return the String representing the issue page.
	 * @throws IssueAlreadyExistsException
	 */
	@RequestMapping(value = "/createissue", method = RequestMethod.POST)
	public String createIssue(Issue issue, HttpSession session, HttpServletRequest request)
			throws IssueAlreadyExistsException {
		
		String title = Jsoup.clean(issue.getTitle(), Whitelist.none()).trim();
		String userDescription = Jsoup.clean(issue.getUserDescription(), Whitelist.none()).trim();
		
		if (title.length() == 0 || userDescription.length() == 0) {
			session.setAttribute("fileIssueError", "Title or Description cannot be empty");
			logger.trace("redirecting to: fileIssue.jsp");
			return "redirect:/fileIssue";
		}
		session.setAttribute("fileIssueError", null);
				
		issue.setTitle(title);
		issue.setUserDescription(userDescription);
		
		DepartmentDAO deptDao = (DepartmentDAO) ApplicationContextProvider.getApplicationContext()
				.getBean("departmentDAO");

		Department dept = new Department();
		dept.setDeptId(Integer.valueOf(request.getParameter("deptID")));
		try {
			dept = deptDao.read(dept);
		} catch (DepartmentDoesNotExistException e) {
			logger.error("Department does not exist", e);
		}

		issue.setDepartment(dept);
		Priority priority = Priority.valueOf(request.getParameter("priorityOptions"));
		issue.setPriority(priority);
		
		User user = (User) session.getAttribute("user");
		issue.setSubmittedBy(user);

		CreateIssue createIssue = new CreateIssue(issue, session);
		createIssue.execute();
		logger.info("Issue created: " + issue.getTitle());
		logger.trace("redirecting to: issues");
		return "redirect:/issues";
	}

	/**
	 * 
	 * @param model
	 *            - a model to
	 * @return
	 */
	@RequestMapping(value = "/fileIssue", method = RequestMethod.GET)
	public String createIssue(Model model) {
		DepartmentDAO deptDao = ApplicationContextProvider.getApplicationContext().getBean("departmentDAO",
				DepartmentDAO.class);
		List<Department> deps = deptDao.readAll();

		model.addAttribute("departments", deps);
		model.addAttribute(new Issue());
		logger.trace("redirecting to fileIssue.jsp");
		return "fileIssue";
	}
	
}
