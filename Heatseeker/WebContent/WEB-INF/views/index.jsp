<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>

<link rel="shortcut icon" type="image/png" href="images/favicon.png"/>
<link rel="stylesheet" type="text/css" href="styles/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="styles/main.css" />
</head>

<body>
	<%@ include file="nav.jsp"%>


	<!--  @author lawrence  -->
	<div class="login-container center-block">
		<div class="form-label">

			<label class="color-white">Log In</label>
		</div>
		<c:if test="${requestScope.authenticate == false}">
			<p class="errorMessage">Sorry, you must be logged in to access that
				page.</p>
		</c:if>
		<form action="Login" method="POST" class="login-form">
			<div class="form-group">
				<label>Username</label> <input type="text" name="username"
					class="form-input form-control center-block" placeholder="Username"
					autofocus="autofocus" required="required" maxlength="30">
			</div>
			<div class="form-group">
				<label>Password</label> <input type="password" name="password"
					class="form-input form-control center-block" placeholder="Password"
					required="required" maxlength="30">
			</div>
			<p class="errorMessage">${loginError}</p>
			<div class="form-group">
				<button type="submit" class="btn btn-purple color-white"
					name="login">Sign in</button>
			</div>
		</form>
	</div>


</body>
</html>