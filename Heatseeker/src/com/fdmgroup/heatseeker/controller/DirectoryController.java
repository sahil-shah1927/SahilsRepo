package com.fdmgroup.heatseeker.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.model.Department;

/**
 * Spring Web Controller Object that handles redirecting users to the
 * appropriate page
 * 
 * @author Ming.Kong
 *
 */
@Controller
public class DirectoryController {
	
	private Logger logger = LoggerFactory.getLogger("Heatseeker");
	
	@RequestMapping(value="/")
	public String goWelcome() {
		logger.trace("redirecting to: welcome.jsp");
		return "welcome";
	}

	/**
	 * Brings users to the index page if they are at the root of the website
	 * 
	 * @return
	 */
	// @RequestMapping(value={"/", "/login"})
	@RequestMapping(value ="/login")
	public String goHome(HttpServletRequest request) {
		DepartmentDAO deptDao = ApplicationContextProvider.getApplicationContext().getBean("departmentDAO",
				DepartmentDAO.class);
		List<Department> deps = deptDao.readAll();
		request.setAttribute("departments", deps);
		logger.trace("redirecting to: login");
		return "index";
		// return "pushMessage";
	}

	@RequestMapping("/issues")
	public String goIssues() {
		logger.trace("redirecting to: issues");
		return "issues";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String redirectIndex(HttpServletRequest request) {
		DepartmentDAO deptDao = ApplicationContextProvider.getApplicationContext().getBean("departmentDAO",
				DepartmentDAO.class);
		List<Department> deps = deptDao.readAll();
		request.setAttribute("departments", deps);
		logger.trace("redirecting to: index.jsp");
		return "index";
	}

	@RequestMapping(value = "/issues", method = RequestMethod.GET)
	public String redirectIssues() {
		logger.trace("redirecting to: issues");
		return "issues";
	}

	@RequestMapping(value = "/deptIssues", method = RequestMethod.GET)
	public String goToDepartmentIssues() throws DepartmentDoesNotExistException {
		logger.trace("redirecting to: department issues");
		return "deptIssues";
	}

	@RequestMapping(value = "/genIssues", method = RequestMethod.GET)
	public String goToGenIssues() throws DepartmentDoesNotExistException {
		logger.trace("redirecting to: general issues");
		return "genIssues";

	}

	@RequestMapping(value = "/invalidCredentials", method = RequestMethod.GET)
	public String redirectInvalidCreds(HttpServletRequest request) {
		DepartmentDAO deptDao = ApplicationContextProvider.getApplicationContext().getBean("departmentDAO",
				DepartmentDAO.class);
		List<Department> deps = deptDao.readAll();
		request.setAttribute("departments", deps);
		request.setAttribute("loginError", "Invalid Credentials");
		logger.trace("redirecting to: login with error message");
		return "index";
	}
	
	@RequestMapping(value = "/errorPage", method = RequestMethod.GET)
	public String goToErrorPage() throws DepartmentDoesNotExistException {
		logger.trace("redirecting to: error page");
		return "errorPage";

	}

}
