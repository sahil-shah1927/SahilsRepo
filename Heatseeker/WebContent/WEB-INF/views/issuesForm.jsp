<!--  @author lawrence  -->
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.fdmgroup.heatseeker.model.Priority"%>

<div class="issues-form-container center-block">
	<div class="form-label">

		<label class="color-white">Submit a Ticket</label>
	</div>

	<sf:form id="createIssueForm" action="createissue" method="POST"
		class="login-form" modelAttribute="issue">

		<div class="form-group">
			<label>Department</label> <select
				class="form-input form-control center-block" name="deptID"
				required="required">
				<c:forEach items="${departments}" var="currentDepartment">
					<option value="${currentDepartment.deptId }">${currentDepartment.deptName }</option>
				</c:forEach>
			</select>
		</div>

		<div class="form-group">
			<label>Title</label>
			<sf:input class="form-input form-control center-block" type="text"
				name="issueTitle" placeholder="Enter a title.."
				autofocus="autofocus" required="required" maxlength="30"
				path="title" />
		</div>

		<div class="form-group">
			<label>Description</label>
			<sf:textarea class="form-input form-control center-block"
				name="issueDescription" rows="5" placeholder="Enter a description.."
				path="userDescription" maxlength="300" />
		</div>
		<div class="form-group">
			<label>Priority</label><br>
			<c:forEach var="currentPriority" items="<%=Priority.values()%>">
				<label class="radio-inline"> <input type="radio"
					name="priorityOptions" value="${currentPriority}"
					<c:if test='${currentPriority.toString() == "Low"}'>
  						checked="checked"
 					</c:if>>
					${currentPriority.toString()}
				</label>
			</c:forEach>
		</div>
		<p class="errorMessage">${fileIssueError}</p>
		<div class="form-group">
			<button id="createIssueButton" type="submit"
				class="btn btn-purple color-white" name="submitIssue">Submit</button>
		</div>
	</sf:form>

</div>