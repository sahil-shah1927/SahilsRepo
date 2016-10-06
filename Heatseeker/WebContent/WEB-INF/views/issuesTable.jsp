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
<%@ page import="com.fdmgroup.heatseeker.model.Priority"%>



<%@ page
	import="com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider"%>


<%@ page import="org.jsoup.*"%>
<%@ page import="org.jsoup.safety.Whitelist"%>

<!DOCTYPE html >





<%
	UserDAO uDAO = ApplicationContextProvider.getApplicationContext().getBean("userDAO", UserDAO.class);
	User basicUser = (User) session.getAttribute("user");

	List<Issue> myIssues = new ArrayList<Issue>();

	try {

		myIssues = uDAO.read(basicUser).getIssues();

	} catch (NullPointerException npe) {

	}
%>


<script type="text/javascript" src="scripts/jquery.tablesorter.js"></script>

<script type="text/javascript" src="scripts/tablesorterconfig.js"></script>


<table
	class="tablesorter table table-striped table-hover table-bordered issue-table">
	<thead>
		<tr>
			<th role="button" class="center-text hover-border width-1"></th>
			<th role="button" class="center-text hover-border width-4">ID <i
				class="fa fa-angle-down" aria-hidden="true"></i></th>
			<th role="button" class="center-text hover-border width-25">Title
				<i class="fa fa-angle-down" aria-hidden="true"></i>
			</th>
			<th role="button" class="center-text hover-border width-39">Description
				<i class="fa fa-angle-down" aria-hidden="true"></i>
			</th>
			<th role="button" class="center-text hover-border width-10">Department
				<i class="fa fa-angle-down" aria-hidden="true"></i>
			</th>
			<th role="button" class="center-text hover-border width-13">Date
				Submitted <i class="fa fa-angle-down" aria-hidden="true"></i>
			</th>
			<th role="button" class="center-text hover-border width-8">Status
				<i class="fa fa-angle-down" aria-hidden="true"></i>
			</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test='${statusOfTab == "All"}'>
				<c:forEach var="currentIssue" items="<%=myIssues%>">
					<tr class="issue-row" role="button">
						<c:choose>
							<c:when test="${currentIssue.priority.toString() == 'Low'}">
								<td class="center-text priority-low" data-container="body"
									data-toggle="tooltip" data-placement="right"
									title="Low Priority"><span hidden='hidden'>0</span></td>
							</c:when>
							<c:when test="${currentIssue.priority.toString() == 'Medium'}">
								<td class="center-text priority-medium" data-container="body"
									data-toggle="tooltip" data-placement="right"
									title="Medium Priority"><span hidden='hidden'>1</span></td>
							</c:when>
							<c:when test="${currentIssue.priority.toString() == 'High'}">
								<td class="center-text priority-high" data-container="body"
									data-toggle="tooltip" data-placement="right"
									title="High Priority"><span hidden='hidden'>2</span></td>
							</c:when>
						</c:choose>
						<td class="center-text">${currentIssue.issueId}</td>
						<td class="center-text truncate">${currentIssue.title}</td>
						<td class="truncate">${currentIssue.userDescription}</td>
						<td class="center-text">${currentIssue.department.deptName}</td>
						<td class="center-text">${currentIssue.getDateWithoutTime(currentIssue.dateSubmitted)}</td>
						<td class="center-text">${currentIssue.status.toString()}</td>

					</tr>
					<tr class="expand-child issue-details">
						<td colspan="7">
							<div class="issue-details-container center-block">
								<div class="issue-details-title center-text center-block">
									<h3>${currentIssue.title}</h3>
								</div>
								<div class="issue-details-info center-text">
									<div class="left-details-info inline-block">
										<p>Submitted by:</p>
										<p>Contact at:</p>
										<c:if test='${currentIssue.dateResolved != null}'>
											<p>Date Closed:</p>
										</c:if>
									</div>
									<div class="right-details-info inline-block">
										<p>${currentIssue.submittedBy.username}</p>
										<p>
											<a
												href="mailto:${currentIssue.submittedBy.email}?subject=RE: ${currentIssue.title}">${currentIssue.submittedBy.email}</a>
										</p>
										<c:if test='${currentIssue.dateResolved != null}'>
											<p>${currentIssue.getDateWithoutTime(currentIssue.dateResolved)}</p>
										</c:if>
									</div>
								</div>
								<div class="issue-description-container center-block">
									<div class="issue-description-label">
										<label>Details</label>
									</div>
									<div class="issue-description">
										${currentIssue.userDescription}</div>
								</div>
								<div class="issue-updates-container center-block">
									<div class="issue-updates-label">
										<label>Updates</label>
									</div>
									<div class="issue-details-log center-block">
										<ul class="allTabIssueUpdates"></ul>
									</div>
									<c:if test='${currentIssue.status.toString() != "Closed" }'>
										<div
											class="issue-details-update-container center-text center-block">
											<form class="addIssueUpdateForm" action="addIssueUpdate"
												method="POST" class="issue-update-form">
												<div class="form-group inline-block issue-update-input">
													<input type="hidden" name="issueId"
														value="${currentIssue.issueId}" /> <input type="text"
														class="form-control update-textarea width-100"
														placeholder="Add an update.." name="updateText" required>
												</div>
												<div class="form-group inline-block">
													<button class="addIssueUpdateButton center-block btn btn-hover-lightblue" type="submit"
														
														name="addUpdate">Submit</button>
												</div>
											</form>
										</div>
									</c:if>
								</div>
								<c:if test='${currentIssue.status.toString() != "Closed" }'>
									<div class="center-block center-text">
										<form class="changeIssueStatusForm" action="changeIssueStatus"
											method="POST" class="issue-details-update-status">
											<input type="hidden" name="issueId"
												value="${currentIssue.issueId}" /> <select
												class="issue-status-select form-control inline-block"
												required="required" name="statusToUpdate">
												<option value="" disabled selected>Mark As</option>
												<!-- Load options to change the issue's status based on the user's role -->
												<c:forEach var="currentStatus" items="<%=Status.values()%>">
													<c:if
														test="${Status.isUserChangeable('BasicUser', currentStatus, currentIssue.status.toString())}">
														<option value="${currentStatus}">${currentStatus.toString()}</option>
													</c:if>
												</c:forEach>
											</select>
											<button
												class="changeIssueStatusForm btn inline-block btn-hover-lightblue"
												type="submit">Change Status</button>
										</form>
									</div>
								</c:if>
								<c:if test='${currentIssue.status.toString() != "Closed" }'>
									<div
										class="center-block center-text issue-details-update-priority">
										<form class="changeIssuePriorityForm"
											action="changeIssuePriority" method="POST">
											<input type="hidden" name="issueId"
												value="${currentIssue.issueId}" />
											<div class="form-group">
												<c:forEach var="currentPriority"
													items="<%=Priority.values()%>">
													<label class="radio-inline"> <input type="radio"
														name="priorityOptions" value="${currentPriority}"
														<c:if test='${currentPriority == currentIssue.priority}'>
									  						checked="checked"
									 					</c:if>>
														${currentPriority.toString()}
													</label>
												</c:forEach>
												<button
													class="changeIssuePriorityForm btn inline-block priority-btn btn-hover-lightblue"
													type="submit">Change Priority</button>
											</div>


										</form>
									</div>
								</c:if>
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach var="currentIssue" items="<%=myIssues%>">
					<c:if test="${currentIssue.status.toString() == statusOfTab}">
						<tr class="issue-row" role="button">
							<c:choose>
								<c:when test="${currentIssue.priority.toString() == 'Low'}">
									<td class="center-text priority-low" data-container="body"
										data-toggle="tooltip" data-placement="right"
										title="Low Priority"><span hidden='hidden'>0</span></td>
								</c:when>
								<c:when test="${currentIssue.priority.toString() == 'Medium'}">
									<td class="center-text priority-medium" data-container="body"
										data-toggle="tooltip" data-placement="right"
										title="Medium Priority"><span hidden='hidden'>1</span></td>
								</c:when>
								<c:when test="${currentIssue.priority.toString() == 'High'}">
									<td class="center-text priority-high" data-container="body"
										data-toggle="tooltip" data-placement="right"
										title="High Priority"><span hidden='hidden'>2</span></td>
								</c:when>
							</c:choose>
							<td class="center-text issue-id">${currentIssue.issueId}</td>
							<td class="center-text truncate">${currentIssue.title}</td>
							<td class="truncate">${currentIssue.userDescription}</td>
							<td class="center-text">${currentIssue.department.deptName}</td>
							<td class="center-text">${currentIssue.getDateWithoutTime(currentIssue.dateSubmitted)}</td>
							<td class="center-text">${currentIssue.status.toString()}</td>
						</tr>
						<tr class="expand-child issue-details">
							<td colspan="7">
								<div class="issue-details-container center-block">
									<div class="issue-details-title center-text center-block">
										<h3>${currentIssue.title}</h3>
									</div>
									<div class="issue-details-info center-text">
										<div class="left-details-info inline-block">
											<p>Submitted by:</p>
											<p>Contact at:</p>
											<c:if test='${currentIssue.dateResolved != null}'>
												<p>Date Closed:</p>
											</c:if>
										</div>
										<div class="right-details-info inline-block">
											<p>${currentIssue.submittedBy.username}</p>
											<p>
												<a
													href="mailto:${currentIssue.submittedBy.email}?subject=RE: ${currentIssue.title}">${currentIssue.submittedBy.email}</a>
											</p>
											<c:if test='${currentIssue.dateResolved != null}'>
												<p>${currentIssue.getDateWithoutTime(currentIssue.dateResolved)}</p>
											</c:if>
										</div>
									</div>

									<div class="issue-description-container center-block">
										<div class="issue-description-label">
											<label>Details</label>
										</div>
										<div class="issue-description">
											${currentIssue.userDescription}</div>
									</div>
									<div class="issue-updates-container center-block">
										<div class="issue-updates-label">
											<label>Updates</label>
										</div>
										<div class="issue-details-log center-block">
											<ul class="allTabIssueUpdates">
											</ul>
										</div>
										<c:if test='${currentIssue.status.toString() != "Closed" }'>
											<div
												class="issue-details-update-container center-text center-block">
												<form class="addIssueUpdateForm" action="addIssueUpdate"
													method="POST" class="issue-update-form">
													<div class="form-group inline-block issue-update-input">
														<input type="hidden" name="issueId"
															value="${currentIssue.issueId}" /> <input type="text"
															class="form-control update-textarea width-100"
															placeholder="Add an update.." name="updateText" required>
													</div>
													<div class="form-group inline-block">
														<button class="addIssueUpdateButton center-block btn btn-hover-lightblue" type="submit"
														
															name="addUpdate">Submit</button>
													</div>
												</form>
											</div>
										</c:if>
									</div>
									<c:if test='${currentIssue.status.toString() != "Closed" }'>
										<div class="center-block center-text">
											<form class="changeIssueStatusForm"
												action="changeIssueStatus" method="POST"
												class="issue-details-update-status">
												<input type="hidden" name="issueId"
													value="${currentIssue.issueId}" /> <select
													class="issue-status-select form-control inline-block"
													required="required" name="statusToUpdate">
													<option value="" disabled selected>Mark As</option>
													<!-- Load options to change the issue's status based on the user's role -->
													<c:forEach var="currentStatus" items="<%=Status.values()%>">
														<c:if
															test="${Status.isUserChangeable('BasicUser', currentStatus, currentIssue.status.toString())}">
															<option value="${currentStatus}">${currentStatus.toString()}</option>
														</c:if>
													</c:forEach>
												</select>
												<button
													class="changeIssueStatusForm btn inline-block btn-hover-lightblue"
													type="submit">Change Status</button>
											</form>
										</div>
									</c:if>
									<c:if test='${currentIssue.status.toString() != "Closed" }'>
										<div
											class="center-block center-text issue-details-update-priority">
											<form class="changeIssuePriorityForm"
												action="changeIssuePriority" method="POST">
												<input type="hidden" name="issueId"
													value="${currentIssue.issueId}" />
												<div class="form-group">
													<c:forEach var="currentPriority"
														items="<%=Priority.values()%>">
														<label class="radio-inline"> <input type="radio"
															name="priorityOptions" value="${currentPriority}"
															<c:if test='${currentPriority == currentIssue.priority}'>
										  						checked="checked"
										 					</c:if>>
															${currentPriority.toString()}
														</label>
													</c:forEach>
													<button
														class="changeIssuePriorityForm btn inline-block priority-btn btn-hover-lightblue"
														type="submit">Change Priority</button>
												</div>


											</form>
										</div>
									</c:if>
								</div>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>

<script src="scripts/main.js"></script>