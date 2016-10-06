package com.fdmgroup.heatseeker.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;

/**
 * 
 * @author Kishan
 *
 */
@Controller
public class AjaxController {
	
	private Logger logger = LoggerFactory.getLogger("Redirect");
	
	@RequestMapping("/setIssueStatusMessage")
	public String setIssueStatusMessage(HttpServletRequest request,HttpSession session) throws IssueDoesNotExistException{
		IssueDAO iDAO = ApplicationContextProvider.getApplicationContext().getBean("issueDAO",IssueDAO.class);
		int issueId = Integer.parseInt((String)session.getAttribute("issue_id"));
		String status = iDAO.readById(issueId).getStatus().toString();
		
		session.setAttribute("message","Status of issue "+session.getAttribute("issue_id")+" has been changed to " + "\'"+status+"\'");
		
		logger.info("Stored " + request.getParameter("message") + "into 'message' attribute of session");
		String[] headers = request.getHeader("referer").split("/");
		String sendTo = headers[headers.length - 1];
		logger.trace("redirecting to " + sendTo);
		return "redirect:/"+sendTo;		
	}
	
	@RequestMapping("/createIssueMessage")
	public String createIssueMessage(HttpServletRequest request,HttpSession session) throws IssueDoesNotExistException{
		
		
		session.setAttribute("message",request.getParameter("message"));
		
		logger.info("Stored " + request.getParameter("message") + "into 'message' attribute of session");
		String[] headers = request.getHeader("referer").split("/");
		String sendTo = headers[headers.length - 1];
		logger.trace("redirecting to " + sendTo);
		return "redirect:/"+sendTo;		
	}
	
	@RequestMapping("/setIssuePriorityMessage")
	public String setIssueMessage(HttpServletRequest request,HttpSession session) throws IssueDoesNotExistException{
		
		
		IssueDAO iDAO = ApplicationContextProvider.getApplicationContext().getBean("issueDAO",IssueDAO.class);
		int issueId = Integer.parseInt((String)session.getAttribute("issue_id"));
		String priority = iDAO.readById(issueId).getPriority().toString();
		
		session.setAttribute("message","Priority of issue "+session.getAttribute("issue_id")+" has been changed to " + "\'"+priority+"\'");
		
		logger.info("Stored " + request.getParameter("message") + "into 'message' attribute of session");
		String[] headers = request.getHeader("referer").split("/");
		String sendTo = headers[headers.length - 1];
		logger.trace("redirecting to " + sendTo);
		return "redirect:/"+sendTo;		
	}
	
	@RequestMapping("/setDepartmentMessage")
	public String setDepartmentMessage(HttpServletRequest request,HttpSession session) throws IssueDoesNotExistException{
		
		
		IssueDAO iDAO = ApplicationContextProvider.getApplicationContext().getBean("issueDAO",IssueDAO.class);
		int issueId = Integer.parseInt((String)session.getAttribute("issue_id"));
		String department = iDAO.readById(issueId).getDepartment().getDeptName();
		
		session.setAttribute("message","Department of issue "+session.getAttribute("issue_id")+" has been changed to " + "\'"+department+"\'");
		
		logger.info("Stored " + request.getParameter("message") + "into 'message' attribute of session");
		String[] headers = request.getHeader("referer").split("/");
		String sendTo = headers[headers.length - 1];
		logger.trace("redirecting to " + sendTo);
		return "redirect:/"+sendTo;		
	}
	
	
	@RequestMapping("/removeIssueMessage")
	public String removeIssueMessage(HttpServletRequest request,HttpSession session){
		
		logger.info("Removed " + session.getAttribute("message") + " from session");		
		session.removeAttribute("message");
		String[] headers = request.getHeader("referer").split("/");
		String sendTo = headers[headers.length - 1];
		logger.trace("redirecting to " + sendTo);
		return "redirect:/"+sendTo;		
	}
	
	@RequestMapping("/getUpdates")
	public String getUpdatesForIssue(){
		logger.trace("redirecting to getUpdates");
		return "getUpdates";
	}
	
	@RequestMapping("/setIssueId")
	public String setIssueId(HttpServletRequest request,HttpSession session){
			
		session.setAttribute("issue_id",request.getParameter("issue_id"));
		logger.info("Stored " + request.getParameter("issue_id") + "into 'issue_id' attribute of session");
		
		String[] headers = request.getHeader("referer").split("/");
		String sendTo = headers[headers.length - 1];
		logger.trace("redirecting to " + sendTo);
		return "redirect:/"+sendTo;
	}
	
	@RequestMapping("/getNewIssuesNotifications")
	public String getNewIssuesNotifications(){
		logger.trace("redirecting to newIssuesNotification");
		return "newIssuesNotification";
	}
	/**
	 * 
	 * @return String getMessages - Url to which ajax request is directed to fetch messages in a chat room.
	 */
	@RequestMapping("/AllDepartmentIssues")
	public String getAllDepartmentIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "All");
		logger.info("Setting attribute 'statusOfTab' of request to 'All'");
		logger.trace("redirecting to deptIssuesTable");
		return "deptIssuesTable";
	}
	
	
	@RequestMapping("/NewDepartmentIssues")
	public String getNewDepartmentIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "New");
		return "deptIssuesTable";
	}
	
	@RequestMapping("/OpenDepartmentIssues")
	public String getOpenDepartmentIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "Open");

		return "deptIssuesTable";
	}
	
	@RequestMapping("/ResolvedDepartmentIssues")
	public String getResolvedDepartmentIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "Resolved");

		return "deptIssuesTable";
	}

	@RequestMapping("/ClosedDepartmentIssues")
	public String getClosedDepartmentIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "Closed");
		return "deptIssuesTable";
	}

	
	
	
	
	
	@RequestMapping("/AllBasicIssues")
	public String getAllBasicIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "All");
		return "issuesTable";
	}
	
	@RequestMapping("/NewBasicIssues")
	public String getNewBasicIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "New");
		return "issuesTable";
	}
	
	@RequestMapping("/OpenBasicIssues")
	public String getOpenBasicIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "Open");

		return "issuesTable";
	}
	
	@RequestMapping("/RejectedBasicIssues")
	public String getRejectedBasicDepartmentIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "Rejected");

		return "issuesTable";
	}
	
	@RequestMapping("/ResolvedBasicIssues")
	public String getResolvedBasicIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "Resolved");

		return "issuesTable";
	}
	
	@RequestMapping("/ClosedBasicIssues")
	public String getClosedBasicIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "Closed");
		return "issuesTable";
	}
	
	
	
	
	
	
	
	@RequestMapping("/AllGeneralIssues")
	public String getAllGeneralIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "All");

		return "genIssuesTable";
	}
	
	@RequestMapping("/OpenGeneralIssues")
	public String getAllOpenIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "Open");

		return "genIssuesTable";
	}
	
	
	@RequestMapping("/NewGeneralIssues")
	public String getNewGeneralIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "New");

		return "genIssuesTable";
	}	
	
	@RequestMapping("/RejectedGeneralIssues")
	public String getRejectedGeneralIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "Rejected");

		return "genIssuesTable";
	}	
	
	@RequestMapping("/ResolvedGeneralIssues")
	public String getResolvedGeneralIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "Resolved");

		return "genIssuesTable";
	}
	
	@RequestMapping("/ClosedGeneralIssues")
	public String getClosedGeneralIssues(HttpServletRequest request){
		request.setAttribute("statusOfTab", "Closed");

		return "genIssuesTable";
	}
	
}
