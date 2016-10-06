<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page
	import="com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider"%>
<%@ page import="com.fdmgroup.heatseeker.DAOs.UserDAO"%>
<%@ page import="com.fdmgroup.heatseeker.DAOs.IssueDAO"%>

<%@ page import="com.fdmgroup.heatseeker.model.Issue"%>
<%@ page import="com.fdmgroup.heatseeker.model.DepartmentAdmin"%>
<%@ page import="com.fdmgroup.heatseeker.model.User"%>
<%@ page import="com.fdmgroup.heatseeker.model.Status"%>
<%@ page import="com.fdmgroup.heatseeker.model.IssueUpdates"%>

<%@ page import="java.util.Collections"%>



<%@ page
	import="com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider"%>


<%@ page import="org.jsoup.*"%>
<%@ page import="org.jsoup.safety.Whitelist"%>

<%
List<IssueUpdates> updates = null;
	if (session.getAttribute("issue_id") != null) {

		IssueDAO iDAO = ApplicationContextProvider.getApplicationContext().getBean("issueDAO", IssueDAO.class);
		Issue currentIssue = new Issue();
		updates = new ArrayList<IssueUpdates>();

		try {

			updates = iDAO.readById(Integer.parseInt((String) session.getAttribute("issue_id"))).getUpdates();
			Collections.sort(updates);
		} catch (NullPointerException npe) {

		}

	}
%>



<c:forEach items="<%=updates%>" var="update">
	<li><span class="message-date">[${update.getFormattedDate()}]</span>
		<strong>${update.submittedBy.username}</strong>: ${update.updateText}</li>
</c:forEach>



