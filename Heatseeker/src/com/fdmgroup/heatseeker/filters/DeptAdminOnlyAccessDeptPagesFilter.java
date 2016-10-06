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

import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.GeneralAdmin;
import com.fdmgroup.heatseeker.model.User;

/**
 * Servlet Filter implementation class UserLoggedInFilter
 * @author Michael Loconte
 * Ascertains that only a Department Admin can access department admin specific pages. 
 */
@WebFilter(value = {"/deptIssues", "/AllDepartmentIssues",
		"/NewDepartmentIssues", "/OpenDepartmentIssues", "/ResolvedDepartmentIssues", "/ClosedDepartmentIssues"})
public class DeptAdminOnlyAccessDeptPagesFilter implements Filter {

    /**
     * Default constructor. 
     */
    public DeptAdminOnlyAccessDeptPagesFilter() {
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
		User user = (User) session.getAttribute("user");
		if(user instanceof BasicUser || user instanceof GeneralAdmin ) {
			request.setAttribute("message", "Sorry, you do not have access to this page.");
			request.getRequestDispatcher("errorPage").forward(request, response);
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
