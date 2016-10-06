<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page
	import="com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider"%>
<%@ page import="com.fdmgroup.heatseeker.DAOs.DepartmentDAO"%>
<%@ page import="com.fdmgroup.heatseeker.DAOs.IssueDAO"%>

<%@ page import="com.fdmgroup.heatseeker.model.Issue"%>
<%@ page import="com.fdmgroup.heatseeker.model.DepartmentAdmin"%>
<%@ page import="com.fdmgroup.heatseeker.model.User"%>
<%@ page import="com.fdmgroup.heatseeker.model.Status"%>



<%@ page
	import="com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider"%>


<%@ page import="org.jsoup.*"%>
<%@ page import="org.jsoup.safety.Whitelist"%>

<!DOCTYPE html >

<%
	DepartmentDAO dDAO = ApplicationContextProvider.getApplicationContext().getBean("departmentDAO",
			DepartmentDAO.class);
	User admin = (User) session.getAttribute("user");
	int deptId = dDAO.read(admin.getDept()).getDeptId();

	List<Issue> myIssues = new ArrayList<Issue>();

	try {

		myIssues = dDAO.readAllIssuesByDept(deptId);

	} catch (NullPointerException npe) {

	}
%>


<div class="notification-button"><div class="notification-button-text"><strong>!</strong></div></div>