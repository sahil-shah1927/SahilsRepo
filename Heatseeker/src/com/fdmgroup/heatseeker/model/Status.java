package com.fdmgroup.heatseeker.model;

/**
 * Status Enum defines status values that can be assigned to issues.
 * 
 * @author Lawrence Leo, Antony Kwok, Lei Lin
 *
 */
public enum Status 
{
	NEW, OPEN, CLOSED, REJECTED, RESOLVED;
	/*
	 * NEW – When a User creates an issue it holds the NEW status. It will change once someone from the department    sees the issue.
	 * OPEN – When someone from the department sees the issue for the first time, they should set it to the OPEN status. From open it can go to REJECTED or RESOLVED.	
	 * REJECTED – When the department admin decides that the issue is more directed towards a different department, they should set it as REJECTED. This will send the issue to General Admin who should decide what department to send it to. When they send the issue to the new department, the issue’s status will be NEW.
	 * RESOLVED – When the department feels that they have finished the job, they change the status to RESOLVED, where the user who submitted the issue can decide whether or not they are happy with the result. If they are they will confirm and CLOSE the issue, otherwise they will comment why not (add to the updates) and reOPEN the issue.	
	 * CLOSED – When the department finishes and user agrees that the job is finished, the status will be changed to CLOSED. An issue cannot be updated once it is CLOSED.
	*/
	
	@Override
	public String toString() 
	{
		String str = super.toString();
		return str.substring(0, 1) + str.substring(1).toLowerCase();
	}
	/** 	
	 * @param role role of user
	 * @param statusToUpdate status the user wants to change the issue to
	 * @param currentStatus the status of the issue
	 * @return
	 */
	public static boolean isUserChangeable(String role, String statusToUpdate, String currentStatus){
		boolean result=false;
		if(role.equalsIgnoreCase("BasicUser")) {
			if(statusToUpdate.equalsIgnoreCase("CLOSED")
					|| (statusToUpdate.equalsIgnoreCase("OPEN") && currentStatus.equalsIgnoreCase("RESOLVED"))) {
				result = true;
			}
		} else if(role.equalsIgnoreCase("DepartmentAdmin")) {
			if((statusToUpdate.equalsIgnoreCase("RESOLVED") && !currentStatus.equalsIgnoreCase("New"))
					|| (statusToUpdate.equalsIgnoreCase("REJECTED"))
					|| (statusToUpdate.equalsIgnoreCase("OPEN"))) {
				result = true;
			}
		}
		return result;
	}
}
