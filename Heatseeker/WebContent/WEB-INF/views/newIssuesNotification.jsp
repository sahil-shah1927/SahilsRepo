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








<%
	if (session.getAttribute("user") != null && (session.getAttribute("role").equals("GeneralAdmin")
			|| session.getAttribute("role").equals("DepartmentAdmin"))) {
		DepartmentDAO dDAO = ApplicationContextProvider.getApplicationContext().getBean("departmentDAO",
				DepartmentDAO.class);
		User admin = (User) session.getAttribute("user");


	
		
	int deptId = dDAO.read(admin.getDept()).getDeptId();
		if (admin.getDept() != null) {
		

			int count = 0;
			int countRejectedAndNew = 0;
			try {
				if (session.getAttribute("role").equals("DepartmentAdmin")) {
					for (Issue issue : dDAO.readAllIssuesByDept(deptId)) {
						if (issue.getStatus().toString().equals("New")) {
							count++;
						}
					}
				}

				else if (session.getAttribute("role").equals("GeneralAdmin")) {
					for (Issue issue : dDAO.readAllIssuesByDept(deptId)) {
						if (issue.getStatus().toString().equals("Rejected") || issue.getStatus().toString().equals("New") ) {
							countRejectedAndNew++;
						}
					}
				}

				session.setAttribute("count", count);
				session.setAttribute("countRejectedAndNew", countRejectedAndNew);
			} catch (NullPointerException npe) {

			}
		}
	}
%> 	   
<c:if test="${sessionScope.user != null }" >
<c:choose>
	<c:when test="${sessionScope.role == 'GeneralAdmin'}">
		<c:choose>
			<c:when test="${countRejectedAndNew==0 }">
				General Issues
			</c:when>
			<c:otherwise>
				General Issues <div style="position:relative;display:inline-block;text-align:center; border-radius: 100%; width:20px ; height:20px;background:#f44336;color:white;">
			
				<div style="position:relative;top:50%;left:50%;transform:translate(-50%,-50%);">${countRejectedAndNew}</div></div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${sessionScope.role == 'DepartmentAdmin'}">
		<c:choose>
			<c:when test="${count==0 }">
				Department Issues
			</c:when>
			<c:otherwise>
				Department Issues <div style="position:relative;display:inline-block;text-align:center; border-radius: 100%; width:20px ; height:20px;background:#f44336;color:white;">
				<div style="position:relative;top:50%;left:50%;transform:translate(-50%,-50%);">${count}</div></div>
			</c:otherwise>
		</c:choose>
	</c:when>
</c:choose>	  
</c:if>
	  
	  
	  
