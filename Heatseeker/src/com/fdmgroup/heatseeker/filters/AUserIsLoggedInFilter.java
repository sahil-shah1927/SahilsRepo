package com.fdmgroup.heatseeker.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class UserLoggedInFilter
 * @author - Antony Kwok
 * Checks to make sure a user is logged in before accessing restricted pages. 
 */
@WebFilter(value = {"/issues", "/deptIssues", "/genIssues", "/AllDepartmentIssues", 
		"/NewDepartmentIssues", "/OpenDepartmentIssues", "/ResolvedDepartmentIssues", "/ClosedDepartmentIssues", 
		"/AllBasicIssues", "/NewBasicIssues", "/OpenBasicIssues", "/RejectedBasicIssues", "/ResolvedBasicIssues", 
		"/ClosedBasicIssues", "/AllGeneralIssues", "/NewGeneralIssues", "/RejectedGeneralIssues", "/ClosedGeneralIssues", 
		"/reject", "/changeDept", "/changeIssuePriority", "/changeIssueStatus", "/createissue", "/fileIssue", 
		"/addIssueUpdate"})
public class AUserIsLoggedInFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AUserIsLoggedInFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		if(session.getAttribute("user") == null || session.getAttribute("role") == null) {
			request.setAttribute("authenticate", false);
			request.getRequestDispatcher("login").forward(request, response);
		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
