package com.fdmgroup.heatseeker.controller;

import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.User;

/**
 * Handles user login. Gets the DAO and password encoder from the
 * ApplicationContext. Passwords are encrypted using Spring Security.
 * 
 * @author ming.kong
 *
 */
@Controller
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger("Heatseeker");
	
	/**
	 * Creates a user using the login parameters and attempts to read the
	 * corresponding user from the database and compare passwords. Catches
	 * NoResultException is there is no corresponding user in the database and
	 * sets the appropriate error message. Sets the appropriate error message if
	 * password is invalid and returns back to index.
	 * 
	 * @param request
	 *            Used to get login parameters and pass in login error messages
	 * @param session
	 *            Used to set a user attribute object if the login is successful
	 * @return String to correct .jsp page
	 */
	@RequestMapping(value = "/Login", method = RequestMethod.POST, params = "login")
	public String login(Model model, HttpServletRequest request, HttpSession session) {
		UserDAO dao = ApplicationContextProvider.getApplicationContext().getBean("userDAO", UserDAO.class);
		PasswordEncoder encoder = ApplicationContextProvider.getApplicationContext().getBean("passwordEncoder",
				BCryptPasswordEncoder.class);

		User user = new BasicUser();

		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));

		try {
			User testUser = dao.read(user);
			if (encoder.matches(user.getPassword(), testUser.getPassword())) {
				session.setAttribute("user", testUser);
				session.setAttribute("role", testUser.getClass().getSimpleName());
				logger.info(user.getUsername()+" logged in");
				logger.trace("redirecting to issues");
				return "redirect:/issues";
			} else {
				logger.trace("redirecting to login with error message");
				return "redirect:/invalidCredentials";
			}
		} catch (NoResultException | UserDoesNotExistException e) {
			logger.trace("redirecting to login with error message");
			return "redirect:/invalidCredentials";
		}
	}

	/**
	 * Test function used to add basic users to the database with encrypted password.
	 * Registration function will no be available at release.
	 * 
	 * @param request
	 *            Used to create user to register to database
	 * @return to logged-in index page
	 */
	@RequestMapping(value = "/Login", method = RequestMethod.POST, params = "registerBasicUser")
	public String register(HttpServletRequest request) {
		UserDAO dao = ApplicationContextProvider.getApplicationContext().getBean("userDAO", UserDAO.class);
		PasswordEncoder encoder = ApplicationContextProvider.getApplicationContext().getBean("passwordEncoder",
				BCryptPasswordEncoder.class);

		User user = (User) ApplicationContextProvider.getApplicationContext().getBean("basicUser");

		user.setUsername(request.getParameter("username"));

		String hashPass = encoder.encode(request.getParameter("password"));

		user.setPassword(hashPass);
		
		user.setDept(null);

		try {
			dao.create(user);
			logger.info("User: " + user.getUsername() + " created");
		} catch (UserAlreadyExistsException e) {
			logger.error("User already exists", e);
		}
		
		DepartmentDAO deptDao = ApplicationContextProvider.getApplicationContext().getBean("departmentDAO", DepartmentDAO.class);
		List<Department> deps = deptDao.readAll();
		request.setAttribute("departments", deps);
		logger.info(user.getUsername()+" registered");
		logger.trace("redirecting to: login page");
		return "redirect:/index";
	}
	
	/**
	 * Test function used to add departmentAdmin users to the database with encrypted password.
	 * Registration function will no be available at release.
	 * 
	 * @param request
	 *            Used to create user to register to database
	 * @return to logged-in index page
	 */
	@RequestMapping(value = "/Login", method = RequestMethod.POST, params = "registerDeptAdmin")
	public String registerDeptAdmin(HttpServletRequest request) throws DepartmentDoesNotExistException {
		
		UserDAO dao = ApplicationContextProvider.getApplicationContext().getBean("userDAO", UserDAO.class);
		PasswordEncoder encoder = ApplicationContextProvider.getApplicationContext().getBean("passwordEncoder",
				BCryptPasswordEncoder.class);

		User user = (User) ApplicationContextProvider.getApplicationContext().getBean("deptAdmin");

		user.setUsername(request.getParameter("username"));

		String hashPass = encoder.encode(request.getParameter("password"));

		Department departmentToBeRead = new Department();
		DepartmentDAO deptDao = ApplicationContextProvider.getApplicationContext().getBean("departmentDAO", DepartmentDAO.class);
		
		int deptId = Integer.valueOf(request.getParameter("deptID"));
		
		departmentToBeRead.setDeptId(deptId);
		Department dept = deptDao.read(departmentToBeRead);
		user.setDept(dept);
		user.setPassword(hashPass);
		
		try {
			dao.create(user);
			logger.info("User: " + user.getUsername() + " created");
		} catch (UserAlreadyExistsException e) {
			logger.error("User already exists" , e);
		}
		List<Department> deps = deptDao.readAll();

		request.setAttribute("departments", deps);
		logger.info("department admin, "+user.getUsername()+", registered");
		logger.trace("redirecting to: login");
		return "redirect:/index";
	}
	
	/**
	 * Test function used to add generalAdmin users to the database with encrypted password.
	 * Registration function will no be available at release.
	 * 
	 * @param request
	 *            Used to create user to register to database
	 * @return to logged-in index page
	 */
	@RequestMapping(value = "/Login", method = RequestMethod.POST, params = "registerGenAdmin")
	public String registerGenAdmin(HttpServletRequest request) throws DepartmentDoesNotExistException {
		UserDAO dao = ApplicationContextProvider.getApplicationContext().getBean("userDAO", UserDAO.class);
		PasswordEncoder encoder = ApplicationContextProvider.getApplicationContext().getBean("passwordEncoder",
				BCryptPasswordEncoder.class);

		User user = (User) ApplicationContextProvider.getApplicationContext().getBean("genAdmin");

		user.setUsername(request.getParameter("username"));

		String hashPass = encoder.encode(request.getParameter("password"));

		Department departmentToBeRead = new Department();
		DepartmentDAO deptDao = ApplicationContextProvider.getApplicationContext().getBean("departmentDAO", DepartmentDAO.class);
		
		departmentToBeRead.setDeptId(Integer.valueOf(request.getParameter("deptID")));
		Department dept = deptDao.read(departmentToBeRead);
		user.setDept(dept);
		user.setPassword(hashPass);
		
		try {
			dao.create(user);
			logger.info("User: " + user.getUsername() + " created");
		} catch (UserAlreadyExistsException e) {
			logger.error("User already exists", e);
		}
		List<Department> deps = deptDao.readAll();

		request.setAttribute("departments", deps);
		logger.info("general admin, " + user.getUsername()+", registered");
		logger.trace("redirecting to: login");
		return "redirect:/index";
	}
	
	/**
	 * Invalidates the session and redirects to the index page for login
	 * 
	 * @param session
	 * 			Used to invalidate the session
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		User user = (User) session.getAttribute("user");
		logger.info(user.getUsername()+" logged out");
		session.setAttribute("user", null);
		session.setAttribute("role", null);
		session.invalidate();
		logger.trace("redirecting to: login");
		return "redirect:/index";
	}
}