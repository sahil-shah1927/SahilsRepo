<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" type="image/png" href="images/favicon.png"/>
<link rel="stylesheet" type="text/css" href="styles/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="styles/main.css" />
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Issues</title>
</head>
<body>
	<%@ include file="nav.jsp"%>
	<div class="container">
		<h2>Issues</h2>
		<ul id="tabs" class="nav nav-tabs">
		
			<li class="li-all"><a data-toggle="tab" href="#All">All</a></li>
			<li><a data-toggle="tab" href="#New">New</a></li>
			<li><a data-toggle="tab" href="#Open">Open</a></li>
			<li><a data-toggle="tab" href="#Resolved">Resolved</a></li>
			<li><a data-toggle="tab" href="#Closed">Closed</a></li>
		</ul>
		<c:if test="${message != null }">
	<div id="alerts"><div class='alert info'><span class='closebtn'>&times;</span><strong>Info!</strong> ${message}</div></div>
		</c:if>
		<div class="tab-content">
		<div id="All" class="tab-pane fade in active">
			<h3>All Issues</h3>
			<div> </div>
			</div>
			<div id="New" class="tab-pane fade">
			<h3>New Issues</h3>
			<div> </div>
			</div>
			<div id="Open" class="tab-pane fade">
			<h3>Open Issues</h3>
			<div> </div>
			</div>
			
			<div id="Resolved" class="tab-pane fade">
			<h3>Resolved Issues</h3>
			<div> </div>
			</div>
			<div id="Closed" class="tab-pane fade">
			<h3>Closed Issues</h3>
			<div> </div>
			</div>
		</div>
	</div>
	<script src="scripts/main.js"></script>
	<script src="scripts/dept-admin-loadIssues-ajax.js"></script>



	

</body>
</html>


