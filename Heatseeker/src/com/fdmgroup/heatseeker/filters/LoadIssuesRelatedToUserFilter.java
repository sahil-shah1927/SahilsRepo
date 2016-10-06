package com.fdmgroup.heatseeker.filters;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.User;

/**
 * Servlet Filter implementation class LoadIssuesRelatedToUserFilter
 */
public class LoadIssuesRelatedToUserFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public LoadIssuesRelatedToUserFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @author Joel & Antony.Kwok
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		String role = (String) session.getAttribute("role");
		IssueDAO issueDAO = ApplicationContextProvider.getApplicationContext().getBean("issueDAO", IssueDAO.class);
		List<Issue> issueList = null;
		if (user != null) {
			if(role.equals("BasicUser")) {
				issueList = user.getIssues();
			} else if(role.equals("DepartmentAdmin")) {
				issueList = user.getDept().getIssues();
			} else if(role.equals("GeneralAdmin")){
				issueList = issueDAO.readAll();
			}
			session.setAttribute("issues", issueList);
			request.setAttribute("authenticate", true);
		} else {
			request.setAttribute("authenticate", false);
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
